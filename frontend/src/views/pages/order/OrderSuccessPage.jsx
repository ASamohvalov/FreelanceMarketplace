import { useLocation, useNavigate } from "react-router-dom";
import FooterComponent from "../../components/FooterComponent";
import "./css/order_success_page.css"
import { useEffect } from "react";
import { useState } from "react";

export default function OrderSuccessPage() {
  const navigate = useNavigate();
  const { state } = useLocation();

  const [currentDate] = useState(() => new Date().toLocaleDateString("ru"));

  useEffect(() => {
    if (!state) {
      navigate("/error");
    }
  }, [navigate, state]);

  return (
    <>
      <main style={{ height: "90vh" }}>
        <div className="container mt-5 mb-5">
          <div className="order-success-page_card mx-auto">
            <div className="text-center mb-4">
              <div className="order-success-page_icon mb-3">
                <i className="bi bi-check-circle-fill"></i>
              </div>
              <h3 className="fw-semibold">Вы успешно оформили заказ</h3>
              <p className="text-muted">
                Исполнитель получил уведомление и скоро свяжется с вами.
              </p>
            </div>

            <div className="order-success-page_info mb-4">
              <div className="row">
                <div className="col-md-6 mb-3">
                  <div className="text-muted small">Дата оформления</div>
                  <div className="fw-semibold">{currentDate}</div>
                </div>

                <div className="col-md-6 mb-3">
                  <div className="text-muted small">Услуга</div>
                  <div className="fw-semibold">{state.serviceName}</div>
                </div>

                <div className="col-md-6 mb-3">
                  <div className="text-muted small">Исполнитель</div>
                  <div className="fw-semibold">{state.executor}</div>
                </div>

                <div className="col-md-6">
                  <div className="text-muted small">Стоимость</div>
                  <div className="fw-semibold">{state.price} ₽</div>
                </div>
              </div>
            </div>

            <div className="d-flex gap-3 justify-content-center flex-wrap">
              <button className="btn btn-main px-4">
                <i className="bi bi-chat-dots me-2"></i>
                Перейти в чат
              </button>

              <button className="btn btn-outline-secondary px-4">
                <i className="bi bi-receipt me-2"></i>
                Мои заказы
              </button>
            </div>
          </div>
        </div>
      </main>
      <FooterComponent />
    </>
  );
}
