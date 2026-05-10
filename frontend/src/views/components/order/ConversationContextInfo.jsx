import { useNavigate, useOutletContext, useParams } from "react-router-dom";
import "./css/conversation_context_info.css";
import { getConversationContextInfo } from "../../../logic/requests/message/messageRequest";
import { useEffect } from "react";
import { useState } from "react";
import { getUserData } from "../../../logic/jwt";
import { fromIsoDate, fromIsoDateToDate } from "../../../logic/time";
import OrderStatusComponent from "./OrderStatusComponent";

export default function ConversationContextInfo({ size, uShown }) {
  const navigate = useNavigate();
  const { conversationId } = useParams();
  const [info, setInfo] = useState();
  const [shown, setShown] = uShown;

  const userData = getUserData();

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
                  ? "Информация о последнем заказе"
                  : "Информация об услуге"}
              </h6>

              <div className="service-preview"></div>

              <div className="mb-3">
                <div className="info-label">Услуга</div>
                <div className="info-value">{info?.service?.title}</div>
              </div>

              <div className="mb-3">
                <div className="info-label">Стоимость</div>
                <div className="info-value">{info?.service?.price} ₽</div>
              </div>

              {info?.type === "ORDER" && (
                <>
                  <div className="mb-3">
                    <div className="info-label">День дедлайна</div>
                    <div className="info-value">{ fromIsoDateToDate(info?.order?.deadlineDate) }</div>
                  </div>

                  <div className="mb-3">
                    <div className="info-label">Статус</div>
                    <OrderStatusComponent status={info?.order?.status} />
                  </div>
                </>
              )}

              <button
                className="btn btn-outline-primary w-100 mb-2"
                onClick={() => navigate(`/service/${info?.service?.id}`)}
              >
                <i className="bi bi-box-arrow-up-right me-1"></i>
                Открыть услугу
              </button>

              {info?.service.freelancer.userId === userData.id && (
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
              )}
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
              ? "Информация о последнем заказе"
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
            <div className="info-label">Стоимость</div>
            <div className="info-value">{info?.service?.price} ₽</div>
          </div>

          {info?.type === "ORDER" && (
            <>
              <div className="mb-3">
                <div className="info-label">День дедлайна</div>
                <div className="info-value">{ fromIsoDateToDate(info?.order?.deadlineDate) }</div>
              </div>

              <div className="mb-3">
                <div className="info-label">Статус</div>
                <OrderStatusComponent status={info?.order?.status} />
              </div>
            </>
          )}

          <button
            className="btn btn-outline-primary w-100 mb-2"
            onClick={() => navigate(`/service/${info?.service?.id}`)}
          >
            <i className="bi bi-box-arrow-up-right me-1"></i>
            Открыть услугу
          </button>

          {info?.service.freelancer.userId === userData.id && info?.order?.status === "IN_PROGRESS" && (
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
          )}
        </div>
      </div>
    );
  }
}
