import HeaderComponent from "../../components/HeaderComponent";
import FooterComponent from "../../components/FooterComponent";
import { useState } from "react";
import { useEffect } from "react";
import {
  getAllPersonalNotificationsRequest,
  getAllPersonalNotificationsWithHiddenRequest,
} from "../../../logic/requests/message/notificationRequest";
import "./css/notification_page.css";
import { useNavigate } from "react-router-dom";
import NotificationCardComponent from "../../components/notification/NotificationCardComponent";

export default function NotificationsPage() {
  const navigate = useNavigate();
  const [notifications, setNotifications] = useState([]);
  const [allNotifications, setAllNotifications] = useState([]);
  const [showHidden, setShowHidden] = useState(false);

  useEffect(() => {
    (async () => {
      const response = await getAllPersonalNotificationsRequest();
      if (response.status !== 200) {
        navigate("/error");
        return;
      }
      setNotifications(response.data);
    })();
  }, [navigate]);

  function deleteCardById(idx) {
    setNotifications(notifications.filter((_, i) => i !== idx));
  }

  return (
    <>
      <main>
        <div className="container mt-5 mb-5 notification-page-div">
          <div className="mb-3">
            <h3 className="fw-bold">Уведомления</h3>
            <div className="form-check form-switch m-0">
              <input
                className="form-check-input"
                type="checkbox"
                id="showHidden"
                onClick={async () => {
                  if (!showHidden && allNotifications.length === 0) {
                    const response =
                      await getAllPersonalNotificationsWithHiddenRequest();
                    if (response.status !== 200) {
                      navigate("/error");
                      return;
                    }
                    setAllNotifications(response.data);
                  }
                  setShowHidden(!showHidden);
                }}
              />
              <label
                className="form-check-label small text-muted"
                htmlFor="showHidden"
              >
                Показать скрытые
              </label>
            </div>
          </div>
          <div>
            {(() => {
              const notifi = showHidden ? allNotifications : notifications;
              return notifi.map((n, idx) => (
                <NotificationCardComponent
                  notification={n}
                  key={idx}
                  onHide={deleteCardById}
                  hidden={showHidden}
                  idx={idx}
                />
              ));
            })()}
          </div>
        </div>
      </main>
    </>
  );
}
