import HeaderComponent from "../../components/HeaderComponent";
import FooterComponent from "../../components/FooterComponent";
import { useState } from "react";
import { useEffect } from "react";
import { getAllPersonalNotificationsRequest } from "../../../logic/requests/message/notificationRequest";
import "./css/notification_page.css"
import { useNavigate } from "react-router-dom";
import NotificationCardComponent from "../../components/notification/NotificationCardComponent";

export default function NotificationsPage() {
  const navigate = useNavigate();
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    (async () => {
      const response = await getAllPersonalNotificationsRequest();
      if (response.status !== 200) {
        navigate("/error");
        return;
      }
      setNotifications(response.data);
    })();
  }, [navigate])

  return (
    <>
      <HeaderComponent />

      <main>
        <div className="container mt-5 mb-5 notification-page-div">
          <h3 className="fw-bold mb-4">Уведомления</h3>
          <div>
            {
              notifications.map((n) => <NotificationCardComponent notification={n} key={n.id} />)
            }
          </div>
        </div>
      </main>

      <FooterComponent />
    </>
  );
}
