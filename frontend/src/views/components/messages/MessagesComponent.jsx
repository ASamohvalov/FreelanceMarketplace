import { useEffect, useState } from "react";
import "./css/messages_component.css";
import {
  sendMessageRequest,
  sendMessageUpdateRequest,
  sendReadMessageRequest,
} from "../../../logic/requests/message/messageRequest";
import MessageCardComponent from "./MessageCardComponent";
import { getUserData } from "../../../logic/jwt";
import { useRef } from "react";
import { Link, NavLink, useNavigate, useParams } from "react-router-dom";
import { pool } from "../../../logic/message";
import { useCallback } from "react";

export default function MessagesComponent({
  conversation = null,
  uShown,
  size,
}) {
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState([]);
  const navigate = useNavigate();
  const conversationId = useParams();
  const [shown, setShown] = uShown;
  const [isOpen, setIsOpen] = useState(null);
  const errorHandle = useCallback(() => {
    navigate("/error");
  }, [navigate]);

  const messageChatRef = useRef();
  const isActiveRef = useRef();

  useEffect(() => {
    isActiveRef.current = true;

    pool(isActiveRef, setMessages, conversationId.conversationId, errorHandle);

    return () => (isActiveRef.current = false);
  }, [errorHandle, conversationId]);

  useEffect(() => {
    (async () => {
      const messageIds = messages
        .filter((m) => m.authorId !== getUserData().id && !m.read)
        .map((m) => m.id);
      if (messageIds.length > 0) {
        const response = await sendReadMessageRequest(messageIds);
        if (response.status !== 200) {
          console.log("failed to send read request");
          return;
        }
      }
    })();

    messageChatRef.current.scrollTop = messageChatRef.current.scrollHeight;
  }, [messages]);
  console.log(messages);
  
  return (
    <div
      className={`${!size ? "col-6" : "col-12"} h-100 d-flex flex-column border-start border-end`}
    >
      <div className="chat-header d-flex gap-4">
        {size && (
          <>
            <NavLink
              className="btn btn-primary rounded-2 text-decoration-none"
              to={"/messages"}
            >
              Назад
            </NavLink>
            <button
              className="btn btn-secondary rounded-2"
              onClick={() => setShown((prev) => !prev)}
            >
              Сведения
            </button>
          </>
        )}
        Чат
      </div>

      <div className="chat-messages flex-grow-1" ref={messageChatRef}>
        {messages?.map((m, idx) => (
          <MessageCardComponent
            message={m}
            key={idx}
            isAuthor={m.authorId === getUserData().id}
            Opened={[isOpen, setIsOpen]}
            setMessage={setMessage}
          />
        ))}
      </div>

      <div className="chat-input d-flex gap-2">
        <input
          type="text"
          className="form-control"
          placeholder="Введите сообщение..."
          value={message}
          onChange={(e) => setMessage(e.target.value)}
        />
        <button
          className="btn btn-primary"
          onClick={async () => {
            if (message.length > 0) {
              let response;
              if (isOpen) {
                response = await sendMessageUpdateRequest(
                  isOpen.id,
                  message,
                );
                setIsOpen(null);
              } else {
                response = await sendMessageRequest(
                  conversationId.conversationId,
                  message,
                );
              }
              if (response.status !== 200) {
                errorHandle();
                return;
              }
              setMessage("");
            }
          }}
        >
          <i className="bi bi-send-fill"></i>
        </button>
      </div>
    </div>
  );
}
