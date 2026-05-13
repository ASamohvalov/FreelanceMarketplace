import { useEffect, useState } from "react";
import { sendProposalReplyRequest } from "../../../logic/requests/message/proposalRequest";
import { hideNotificationRequest } from "../../../logic/requests/message/notificationRequest";
import { sendAtDateToRUString } from "../../../logic/time";
import "./css/notification_component.css";
import { Link, useNavigate } from "react-router-dom";
import { acceptExtendDeadline } from "../../../logic/requests/order/orderRequest";

export default function NotificationCardComponent({
  notification,
  idx,
  onHide,
  hidden,
  entityId,
}) {
  const navigate = useNavigate();
  const [time, setTime] = useState("");

  const getLinkRoute = () => {
    switch (notification.type) {
      case "NEW_ORDER":
      case "ORDER_COMPLETED":
      case "ORDER_CANCELLED":
      case "ORDER_ACCEPTED":
        return `/order/info/${entityId}`;
      case "NEW_ORDER_REPORT":
        return "/order/reports";
      case "MONEY_TRANSFERRED":
        return "/personal-account";
      default:
        return "#";
    }
  };

  const linkRoute = getLinkRoute();
  const isDisabled = linkRoute === "#" || !linkRoute;

  useEffect(() => {
    const sendAtDate = Date.parse(notification.sendAt);
    const updateInterval = sendAtDateToRUString(sendAtDate, setTime);
    if (updateInterval !== -1) {
      const intervalId = setInterval(
        sendAtDateToRUString,
        updateInterval,
        sendAtDate,
        setTime,
      );

      return () => clearInterval(intervalId);
    }
  }, [setTime, notification]);

  async function hideNotificaiton() {
    await hideNotificationRequest(notification.id).then(() => onHide(idx));
  }

  return (
    <div
      style={{
        textDecoration: "none",
        // pointerEvents: isDisabled ? "none" : "auto",
        cursor: isDisabled ? "default" : "pointer",
      }}
      className="text-dark"
      onClick={() => {
        if (!isDisabled) {
          navigate(linkRoute);
        }
      }}
    >
      <div className="notification-component-card mb-3 d-flex gap-3 align-items-start border">
        <div className="notification-component-icon flex-shrink-0">
          {notification.type === "NEW_PROPOSAL" && (
            <i className="bi bi-envelope-fill" />
          )}
          {notification.type === "NEW_ORDER" && (
            <i className="bi bi-cart-fill" />
          )}
          {notification.type === "NEW_ORDER_REPORT" && (
            <i className="bi bi-file-earmark-post" />
          )}
          {notification.type === "ORDER_COMPLETED" && (
            <i className="bi bi-calendar-check" />
          )}
          {notification.type === "MONEY_TRANSFERRED" && (
            <i className="bi bi-credit-card" />
          )}
          {notification.type === "ORDER_CANCELLED" && (
            <i className="bi bi-ban" />
          )}
          {notification.type === "ORDER_ACCEPTED" && (
            <i className="bi bi-check2-circle" />
          )}
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
            {(notification.type === "NEW_PROPOSAL" || notification.type === "EXTEND_ORDER_DEADLINE_REQUEST") && (
              <div
                className="bi bi-check-square notification-component-icon_right notification-component-icon_right_green"
                onClick={(e) => {
                  e.stopPropagation();

                  if (notification.type === "NEW_PROPOSAL") {
                    const conf = window.confirm("Открыть чат?");

                    if (conf == true) {
                      sendProposalReplyRequest(notification.entityId).then(
                        hideNotificaiton(),
                      );
                    }
                  } else {
                    const conf = window.confirm("Продлить дедлайн?");

                    if (conf == true) {
                      acceptExtendDeadline(notification.entityId).then(hideNotificaiton());
                    }
                  }
                }}
              />
            )}
            <div
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
    </div>
  );
}
