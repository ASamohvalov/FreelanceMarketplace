import { useNavigate, useOutletContext, useParams } from "react-router-dom";
import "./css/conversation_context_info.css";
import { getConversationContextInfo } from "../../../logic/requests/message/messageRequest";
import { useEffect } from "react";
import { useState } from "react";

export default function ConversationContextInfo({ size, uShown }) {
  const navigate = useNavigate();
  const { conversationId } = useParams();
  const [info, setInfo] = useState();
  const [shown, setShown] = uShown;

  useEffect(() => {
    getConversationContextInfo(conversationId).then((response) => {
      setInfo(response.data);
    });
  }, [conversationId, setInfo]);

  if (size) {
    return (
      <>
        {shown && (
          <div
            className="col-12 position-absolute service-panel p-3"
            style={{ top: "8%" }}
          >
            <div className="service-info-card">
              <h6 className="fw-semibold mb-3">
                {info?.type === "ORDER"
                  ? "Информация о заказе"
                  : "Информация об услуге"}
              </h6>

              <div className="service-preview"></div>

              <div className="mb-3">
                <div className="info-label">Услуга</div>
                <div className="info-value">{info?.service?.title}</div>
              </div>

              <div className="mb-3">
                <div className="info-label">Заказчик</div>
                <div className="info-value">
                  {info?.service?.freelancer?.firstName +
                    " " +
                    info?.service?.freelancer?.lastName}
                </div>
              </div>

              <div className="mb-3">
                <div className="info-label">Стоимость</div>
                <div className="info-value">{info?.service?.price} ₽</div>
              </div>

              <div className="mb-3">
                <div className="info-label">Срок выполнения</div>
                <div className="info-value">7 дней</div>
              </div>

              <div className="mb-3">
                <div className="info-label">Статус</div>
                <span className="badge bg-warning text-dark order-badge">
                  {info?.order.status}
                </span>
              </div>

              <button
                className="btn btn-outline-primary w-100 mb-2"
                onClick={() => navigate(`/service/${info?.service?.id}`)}
              >
                <i className="bi bi-box-arrow-up-right me-1"></i>
                Открыть услугу
              </button>

              <button
                className="btn btn-primary w-100"
                onClick={() =>
                  navigate("/order/report/send", {
                    state: { orderId: info.order.id },
                  })
                }
              >
                <i className="bi bi-check-circle me-1"></i>
                Сдать работу
              </button>
            </div>
          </div>
        )}
      </>
    );
  } else {
    return (
      <div className="col-3 service-panel p-3">
        <div className="service-info-card">
          <h6 className="fw-semibold mb-3">
            {info?.type === "ORDER"
              ? "Информация о заказе"
              : "Информация об услуге"}
          </h6>

          {info?.service.imageUrl ? (
            <img src={info.service.imageUrl}></img>
          ) : (
            <div className="service-preview"></div>
          )}

          <div className="mb-3">
            <div className="info-label">Услуга</div>
            <div className="info-value">{info?.service?.title}</div>
          </div>

          <div className="mb-3">
            <div className="info-label">Заказчик</div>
            <div className="info-value">
              {info?.service?.freelancer?.firstName +
                " " +
                info?.service?.freelancer?.lastName}
            </div>
          </div>

          <div className="mb-3">
            <div className="info-label">Стоимость</div>
            <div className="info-value">{info?.service?.price} ₽</div>
          </div>

          <div className="mb-3">
            <div className="info-label">Срок выполнения</div>
            <div className="info-value">7 дней</div>
          </div>

          <div className="mb-3">
            <div className="info-label">Статус</div>
            <span className="badge bg-warning text-dark order-badge">
              {info?.order?.status}
            </span>
          </div>

          <button
            className="btn btn-outline-primary w-100 mb-2"
            onClick={() => navigate(`/service/${info?.service?.id}`)}
          >
            <i className="bi bi-box-arrow-up-right me-1"></i>
            Открыть услугу
          </button>

          <button
            className="btn btn-primary w-100"
            onClick={() =>
              navigate("/order/report/send", {
                state: { orderId: info.order.id },
              })
            }
          >
            <i className="bi bi-check-circle me-1"></i>
            Сдать работу
          </button>
        </div>
      </div>
    );
  }
}
