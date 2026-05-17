import { Link, useLocation, useNavigate } from "react-router-dom";
import "./css/order_success_page.css";
import { useEffect } from "react";
import { useState } from "react";
import SuccessInfoComponent from "../../components/SuccessInfoComponent";

export default function OrderSuccessPage() {
  const navigate = useNavigate();
  const { state } = useLocation();

  const [currentDate] = useState(() => new Date().toLocaleDateString("ru"));

  useEffect(() => {
    if (!state) {
      navigate("/error?code=403");
    }
  }, [navigate, state]);

  return (
    <SuccessInfoComponent
      title={"Вы успешно оформили заказ"}
      description={"Исполнитель получил уведомление и скоро свяжется с вами."}
    >
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
            <div className="fw-semibold">{state.price / 100} ₽</div>
          </div>
        </div>
      </div>

      <div className="d-flex gap-3 justify-content-center flex-wrap">
        <button
          className="btn btn-main px-4"
          onClick={() => navigate("/messages")}
        >
          <i className="bi bi-chat-dots me-2"></i>
          Перейти в чат
        </button>

        <Link className="btn btn-outline-secondary px-4" to="/MyOrders">
          <i className="bi bi-receipt me-2"></i>
          Мои заказы
        </Link>
      </div>
    </SuccessInfoComponent>
  );
}
