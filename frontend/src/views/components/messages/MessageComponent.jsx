import { useEffect } from "react";
import { getUserData } from "../../../logic/jwt";
import { sendAtDateToRUString } from "../../../logic/time";
import { useState } from "react";

export default function MessageComponent({ message }) {
  const [time, setTime] = useState("");

  useEffect(() => {
    const sendAtDate = message.sendAt instanceof Date
        ? message.sendAt
        : Date.parse(message.sendAt);
    const updateInterval = sendAtDateToRUString(sendAtDate, setTime);
    if (updateInterval !== -1) {
      setInterval(sendAtDateToRUString, updateInterval, sendAtDate, setTime);
    }
  }, [message, setTime]);

  const id = getUserData().id;

  return (
    <div
      className={`message ${message.authorId === id ? "message-sent" : "message-received"}`}
    >
      <div>{message.text}</div>
      <div className="text-end">
        <small
          className={
            message.authorId === id ? "text-secondary-main" : "text-secondary"
          }
        >
          {time}
        </small>
      </div>
    </div>
  );
}
