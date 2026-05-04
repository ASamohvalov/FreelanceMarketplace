import { useEffect } from "react";
import { getUserData } from "../../../logic/jwt";
import { sendAtDateToRUString } from "../../../logic/time";
import { useState } from "react";
import { sendDeleteMessageRequest } from "../../../logic/requests/message/messageRequest";
import "./css/messages_component.css";

export default function MessageCardComponent({ message, isAuthor, Opened, setMessage }) {
  const [time, setTime] = useState("");
  const [show, setShow] = useState(false);
  const [isOpen, setIsOpen] = Opened;

  useEffect(() => {
    const sendAtDate =
      message.sendAt instanceof Number
        ? Date.parse(message.sendAt)
        : new Date(message.sendAt);
    const updateInterval = sendAtDateToRUString(sendAtDate, setTime);
    if (updateInterval !== -1) {
      setInterval(sendAtDateToRUString, updateInterval, sendAtDate, setTime);
    }
  }, [message, setTime]);

  const id = getUserData().id;

  function onClick(e) {
    e.preventDefault();
    setIsOpen(isOpen?.id === message.id ? null : message);
  }

  useEffect(() => {
    if (isOpen?.id === message.id) {
      setShow(true);
    } else if (isOpen === null) {
      setShow(false);
    }
  }, [isOpen, message]);

  return (
    <div className="position-relative">
      <div
        className={`message ${message.authorId === id ? "message-sent" : "message-received"}`}
        onContextMenu={(e) => {
          isAuthor && onClick(e);
        }}
      >
        <div>{message.text}</div>
        <div className="text-end">
          <small
            className={
              message.authorId === id ? "text-secondary-main" : "text-secondary"
            }
          >
            {message.authorId === id && (
              <i
                className={`bi ${message.read ? "bi-check2-all" : "bi-check2"}`}
              />
            )}
            <span className="m-1">{time}</span>
          </small>
        </div>
      </div>
      {isOpen?.id === message.id && show && (
        <div
          className={`position-absolute z-2 -bottom-10 bg-info gap-2 d-flex flex-column p-2 rounded-3 ${message.authorId === id ? "end-0" : "start-0"}`}
        >
          <button className="btn btn-primary" onClick={() => {setMessage(message.text); setShow(false);}}>
            Изменить
          </button>
          <button
            className="btn btn-danger"
            onClick={async () => {
              const response = await sendDeleteMessageRequest(message.id);
              if (response.status !== 200) {
                console.log("logic ERROR");
              }
              setIsOpen(null);
            }}
          >
            Удалить
          </button>
        </div>
      )}
    </div>
  );
}
