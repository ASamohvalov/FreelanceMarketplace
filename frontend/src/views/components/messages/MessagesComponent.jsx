import { useState } from "react";
import { getUserData } from "../../../logic/jwt";
import "./css/messages_component.css";
import { sendMessageRequest } from "../../../logic/requests/message/messageRequest";

export default function MessagesComponent({ messages, conversation, errorHandle }) {
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
        {messages.map((m) => (
          <div
            className={`message ${m.authorId === id ? "message-sent" : "message-received"}`}
            key={m.id}
          >
            {m.text}
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
        <button className="btn btn-primary" onClick={() => {
          if (message.length > 0) {
            const response = sendMessageRequest(conversation.id, message);
            if (response.status !== 200) {
              errorHandle();
              return;
            }
          }
        }}>
          <i className="bi bi-send-fill"></i>
        </button>
      </div>
    </div>
  );
}
