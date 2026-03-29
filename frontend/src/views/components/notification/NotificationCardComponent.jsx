import { useEffect, useState } from "react";
import { sendProposalReplyRequest } from "../../../logic/requests/message/proposalRequest";
import { hideNotificationRequest } from "../../../logic/requests/message/notificationRequest";
import { sendAtDateToRUString } from "../../../logic/time";
import "./css/notification_component.css";

export default function NotificationCardComponent({
  notification,
  idx,
  onHide,
  hidden,
}) {
  const [time, setTime] = useState("");

  useEffect(() => {
    const sendAtDate = Date.parse(notification.sendAt);
    const updateInterval = sendAtDateToRUString(sendAtDate, setTime);
    if (updateInterval !== -1) {
      setInterval(sendAtDateToRUString, updateInterval, sendAtDate, setTime);
    }
  }, [setTime, notification]);

  async function hideNotificaiton() {
    await hideNotificationRequest(notification.id).then(() => onHide(idx));
  }

  return (
    <div className="notification-component-card mb-3 d-flex gap-3 align-items-start border">
      <div className="notification-component-icon flex-shrink-0">
        {notification.type === "NEW_PROPOSAL" && (
          <i className="bi bi-envelope-fill" />
        )}
        {notification.type === "NEW_ORDER" && <i className="bi bi-cart-fill" />}
      </div>

      <div className="flex-grow-1">
        <h6 className="mb-1">{notification.title}</h6>
        <p className="mb-1">{notification.message}</p>

        {notification.type === "NEW_PROPOSAL" && (
          <p className="mb-1 text-secondary notification-component-message">
            {notification.data.message}
          </p>
        )}
        <div className="notification-component-time">{time}</div>
      </div>

      {!hidden && (
        <div className="d-flex gap-2 flex-shrink-0 align-items-center">
          {notification.type === "NEW_PROPOSAL" && (
            <i
              className="bi bi-check-square notification-component-icon_right notification-component-icon_right_green"
              onClick={async () => {
                if (window.confirm("Открыть чат?")) {
                  await sendProposalReplyRequest(notification.entityId).then(
                    hideNotificaiton(),
                  );
                }
              }}
            />
          )}
          <i
            className="bi bi-x-square notification-component-icon_right notification-component-icon_right_red"
            onClick={async () => {
              if (window.confirm("Скрыть уведомление?")) {
                hideNotificaiton();
              }
            }}
          />
        </div>
      )}
    </div>
  );
}
