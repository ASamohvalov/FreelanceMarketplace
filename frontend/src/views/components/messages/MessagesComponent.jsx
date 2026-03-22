import { useEffect, useState } from "react";
import "./css/messages_component.css";
import {
  sendMessageRequest,
  sendReadMessageRequest,
} from "../../../logic/requests/message/messageRequest";
import MessageCardComponent from "./MessageCardComponent";
import { getUserData } from "../../../logic/jwt";
import { useRef } from "react";

export default function MessagesComponent({
  messages,
  conversation,
  errorHandle,
  addNewMessageHandle,
}) {
  const [message, setMessage] = useState("");

  const messageChatRef = useRef();

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
  }, [messages, errorHandle]);

  return (
    <div className="col-6 d-flex flex-column border-start border-end">
      <div className="chat-header">
        {conversation
          ? conversation?.member.firstName + " " + conversation?.member.lastName
          : "Чат"}
      </div>

      <div className="chat-messages flex-grow-1"
        ref={messageChatRef}
      >
        {messages.map((m, idx) => (
          <MessageCardComponent message={m} key={idx} />
        ))}
      </div>

      <div className="chat-input d-flex gap-2">
        <input
          type="text"
          className="form-control"
          placeholder="Введите сообщение..."
          value={message}
          onChange={(e) => conversation !== null && setMessage(e.target.value)}
        />
        <button
          className="btn btn-primary"
          onClick={async () => {
            if (message.length > 0) {
              const response = await sendMessageRequest(
                conversation.id,
                message,
              );
              if (response.status !== 200) {
                errorHandle();
                return;
              }
              addNewMessageHandle(message, conversation.id);
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
