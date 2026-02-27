import HeaderComponent from "../../components/HeaderComponent";
import FooterComponent from "../../components/FooterComponent";
import "./css/notification_page.css"

export default function NotificationsPage() {
  return (
    <>
      <HeaderComponent />

      <main>
        <div className="container mt-5 mb-5 notification-page-div">
          <h3 className="fw-bold mb-4">Уведомления</h3>
          <div>
            <div className="notification-page-card mb-3 d-flex gap-3 align-items-start">
              <div className="notification-page-icon">🛒</div>
              <div>
                <h6 className="mb-1">Новый заказ</h6>
                <p className="mb-1">
                  Вы получили новый заказ на разработку сайта.
                </p>
                <div className="notification-page-time">2 минуты назад</div>
              </div>
            </div>

            <div className="notification-page-card mb-3 d-flex gap-3 align-items-start">
              <div className="notification-page-icon">💬</div>
              <div>
                <h6 className="mb-1">Открыт новый чат</h6>
                <p className="mb-1">
                  Клиент написал вам сообщение по услуге UI/UX.
                </p>
                <div className="notification-page-time">10 минут назад</div>
              </div>
            </div>

            <div className="notification-page-card mb-3 d-flex gap-3 align-items-start">
              <div className="notification-page-icon">⭐</div>
              <div>
                <h6 className="mb-1">Новый отзыв</h6>
                <p className="mb-1">Клиент оставил отзыв на вашу услугу.</p>
                <div className="notification-page-time">1 час назад</div>
              </div>
            </div>
          </div>
        </div>
      </main>

      <FooterComponent />
    </>
  );
}
