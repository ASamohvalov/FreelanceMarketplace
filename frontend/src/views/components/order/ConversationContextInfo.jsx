import { useNavigate, useOutletContext, useParams } from "react-router-dom";
import "./css/conversation_context_info.css";
import { getConversationContextInfo } from "../../../logic/requests/message/messageRequest";
import { useContext, useEffect } from "react";
import { useState } from "react";
import { getUserData } from "../../../logic/jwt";
import { fromIsoDate, fromIsoDateToDate } from "../../../logic/time";
import OrderStatusComponent from "./OrderStatusComponent";
import FeedbackTypeComponent from "../admin_panel/FeedbackTypeComponent";
import { userContext } from "../../../logic/store/userContext";

export default function ConversationContextInfo({ size, uShown }) {
  const navigate = useNavigate();
  const { conversationId } = useParams();
  const [info, setInfo] = useState();
  const [shown, setShown] = uShown;

  const [user, _] = useContext(userContext);
  const [feedbackShowMode, setFeedbackShowMode] = useState(false);

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
                {info?.type === "ORDER" && "Информация о последнем заказе"}
                {info?.type === "DISCUSSION" && "Информация о услуге"}
                {info?.type === "FEEDBACK" && "Информация об обращении"}
              </h6>

              {info?.service?.imageUrl ? (
                <img src={info.service.imageUrl}></img>
              ) : (
                <div className="service-preview"></div>
              )}

              {info?.type !== "FEEDBACK" && (
                <>
                  <div className="mb-3">
                    <div className="info-label">Услуга</div>
                    <div className="info-value">{info?.service?.title}</div>
                  </div>

                  <div className="mb-3">
                    <div className="info-label">Стоимость</div>
                    <div className="info-value">{info?.service?.price} ₽</div>
                  </div>
                </>
              )}

              {info?.type === "ORDER" && (
                <>
                  {info?.order?.status !== "PENDING" && (
                    <div className="mb-3">
                      <div className="info-label">День дедлайна</div>
                      <div className="info-value">{ fromIsoDateToDate(info?.order?.deadlineDate) }</div>
                    </div>
                  )}

                  <div className="mb-3">
                    <div className="info-label">Статус</div>
                    <OrderStatusComponent status={info?.order?.status} />
                  </div>
                </>
              )}

              {info?.type !== "FEEDBACK" && (
                <button
                  className="btn btn-outline-primary w-100 mb-2"
                  onClick={() => navigate(`/service/${info?.service?.id}`)}
                >
                  <i className="bi bi-box-arrow-up-right me-1"></i>
                  Открыть услугу
                </button>
              )}

              {info?.service?.freelancer?.userId === userData.id && info?.order?.status === "IN_PROGRESS" && (
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

              {info?.type === "FEEDBACK" && (
                <div>
                  <strong>{info.feedback.title}</strong>
                  <div>
                    <small className="text-secondary">Тип обращения: </small>
                    <FeedbackTypeComponent type={info.feedback.type} />
                  </div>
                  <p className="mt-2 fs-6 text-secondary" onClick={() => setFeedbackShowMode(!feedbackShowMode)}>
                    {
                      feedbackShowMode
                        ? info.feedback.text
                        : (info.feedback.text.length > 100
                          ? info.feedback.text.slice(0, 100) + "..."
                          : info.feedback.text)
                    }
                  </p>

                  {user.isAdmin && (
                    <button
                      className="btn btn-outline-primary w-100 mb-2"
                      onClick={() => navigate(`/admin/feedback?id=${info?.feedback?.id}`)}
                    >
                      <i className="bi bi-box-arrow-up-right me-1"></i>
                      Открыть обращение
                    </button>
                  )}
                </div>
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
            {info?.type === "ORDER" && "Информация о последнем заказе"}
            {info?.type === "DISCUSSION" && "Информация о услуге"}
            {info?.type === "FEEDBACK" && "Информация об обращении"}
          </h6>

          {info?.service?.imageUrl ? (
            <img src={info.service.imageUrl}></img>
          ) : (
            <div className="service-preview"></div>
          )}

          {info?.type !== "FEEDBACK" && (
            <>
              <div className="mb-3">
                <div className="info-label">Услуга</div>
                <div className="info-value">{info?.service?.title}</div>
              </div>

              <div className="mb-3">
                <div className="info-label">Стоимость</div>
                <div className="info-value">{info?.service?.price} ₽</div>
              </div>
            </>
          )}

          {info?.type === "ORDER" && (
            <>
              {info?.order?.status !== "PENDING" && (
                <div className="mb-3">
                  <div className="info-label">День дедлайна</div>
                  <div className="info-value">{ fromIsoDateToDate(info?.order?.deadlineDate) }</div>
                </div>
              )}

              <div className="mb-3">
                <div className="info-label">Статус</div>
                <OrderStatusComponent status={info?.order?.status} />
              </div>
            </>
          )}

          {info?.type !== "FEEDBACK" && (
            <button
              className="btn btn-outline-primary w-100 mb-2"
              onClick={() => navigate(`/service/${info?.service?.id}`)}
            >
              <i className="bi bi-box-arrow-up-right me-1"></i>
              Открыть услугу
            </button>
          )}

          {info?.service?.freelancer?.userId === userData.id && info?.order?.status === "IN_PROGRESS" && (
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

          {info?.type === "FEEDBACK" && (
            <div>
              <strong>{info.feedback.title}</strong>
              <div>
                <small className="text-secondary">Тип обращения: </small>
                <FeedbackTypeComponent type={info.feedback.type} />
              </div>
              <p className="mt-2 fs-6 text-secondary" onClick={() => setFeedbackShowMode(!feedbackShowMode)}>
                {
                  feedbackShowMode
                    ? info.feedback.text
                    : (info.feedback.text.length > 100
                      ? info.feedback.text.slice(0, 100) + "..."
                      : info.feedback.text)
                }
              </p>

              {user.isAdmin && (
                <button
                  className="btn btn-outline-primary w-100 mb-2"
                  onClick={() => navigate(`/admin/feedback?id=${info?.feedback?.id}`)}
                >
                  <i className="bi bi-box-arrow-up-right me-1"></i>
                  Открыть обращение
                </button>
              )}
            </div>
          )}
        </div>
      </div>
    );
  }
}
