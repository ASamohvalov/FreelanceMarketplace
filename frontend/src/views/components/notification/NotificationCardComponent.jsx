import { useEffect, useState } from "react";
import "./css/notification_component.css";

export default function NotificationCardComponent({ notification }) {
  const [time, setTime] = useState("");


  useEffect(() => {
    const sendAtDate = Date.parse(notification.sendAt);
    var interval = -1;

    function changeTime() {
      const SECOND = 1000;
      const MINUTE = 1000 * 60;
      const HOUR = 1000 * 60 * 60;
      const DAY = 1000 * 60 * 60 * 24;
      const WEEK = 1000 * 60 * 60 * 24 * 7;
      const MONTH = 1000 * 60 * 60 * 24 * 30;
      const YEAR = 1000 * 60 * 60 * 24 * 365;

      const currentTime = new Date().getTime();
      const distance = currentTime - sendAtDate;

      if (distance < SECOND * 30) {
        setTime("сейчас");
        interval = SECOND * 30;
      } else if (distance < MINUTE) {
        setTime("30 секунд назад");
        interval = SECOND * 30;
      } else if (distance < HOUR) {
        const minutes = Math.floor((distance % HOUR) / MINUTE);
        setTime(minutes + " минут назад");
        interval = MINUTE;
      } else if (distance < DAY) {
        const hours = Math.floor((distance % DAY) / HOUR);
        setTime(hours + " часов назад");
        interval = HOUR;
      } else if (distance < WEEK) {
        const days = Math.floor(distance / DAY);
        setTime(days + " дней назад");
      } else if (distance < MONTH) {
        const weeks = Math.floor(distance / WEEK);
        setTime(weeks + " недель назад");
      } else if (distance < YEAR) {
        const months = Math.floor(distance / MONTH);
        setTime(months + " месяцев назад");
      } else {
        const years = Math.floor(distance / YEAR);
        setTime(years + " лет назад");
      }
    }

    changeTime();
    if (interval !== -1) {
      setInterval(changeTime, interval)
    }
  })

  return (
    <div className="notification-component-card mb-3 d-flex gap-3 align-items-start">
      <div className="notification-component-icon">
        {
          (() => {
            if (notification.type === "NEW_PROPOSAL") {
              return (
                <i className="bi bi-envelope-fill"></i>
              );
            } else if (notification.type === "NEW_ORDER") {
              return (
                <i className="bi bi-cart-fill"></i>
              );
            }
          })()
        }
      </div>
      <div>
        <h6 className="mb-1">{notification.title}</h6>
        <p className="mb-1">
          {notification.message}
        </p>
        <div className="notification-component-time">{time}</div>
      </div>
    </div>
  )
}
