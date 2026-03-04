import { useState } from "react";
import { getUserData } from "../../../logic/jwt";
import "./css/messages_component.css";
import { sendMessageRequest } from "../../../logic/requests/message/messageRequest";
import { sendAtDateToRUString } from "../../../logic/time";

export default function MessagesComponent({
  messages,
  conversation,
  errorHandle,
  addNewMessageHandle,
}) {
  const [message, setMessage] = useState("");

  const id = getUserData().id;

  return (
    <div className="col-8 d-flex flex-column">
      <div className="p-3 border-bottom fw-semibold">
        {conversation
          ? conversation?.member.firstName + " " + conversation?.member.lastName
          : "Чат"}
      </div>

      <div className="chat-messages">
        {messages.map((m, idx) => (
          <div
            className={`message ${m.authorId === id ? "message-sent" : "message-received"}`}
            key={idx}
          >
            <div>{m.text}</div>
            <div className="text-end">
              <small
                className={
                  m.authorId === id ? "text-secondary-main" : "text-secondary"
                }
              >
                {sendAtDateToRUString(m.sendAt instanceof Date ? m.sendAt : new Date(m.sendAt))[0]}
              </small>
            </div>
          </div>
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
