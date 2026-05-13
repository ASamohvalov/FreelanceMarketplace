import { Link, useNavigate } from "react-router-dom";
import { fromIsoDate, fromIsoDateToDate } from "../../../logic/time";
import FeedbackTypeComponent from "./FeedbackTypeComponent";
import { useState } from "react";
import { acceptFeedbackRequest } from "../../../logic/requests/feedbackRequest";
import { openFeedbackConversationRequest } from "../../../logic/requests/message/messageRequest";

export default function AdminFeedbackInfoComponent({
  id, title, type, text, createdAt, sender, onClose, accepted
}) {
  const navigate = useNavigate();
  const [message, setMessage] = useState(null);

  const handleAcceptClick = async () => {
    const response = await acceptFeedbackRequest(id);
    if (response.status !== 200) {
      setMessage({message: `Ошибка ${response.status}, попробуйте позже`, type: "danger"})
      return;
    }

    setMessage({message: "Обращение принято", type: "success"})
  };

  const handleOpenConversation = async () => {
    if (window.confirm("Подтвердите открытие чата с пользователем")) {
      const response = await openFeedbackConversationRequest(sender.id, id);
      if (response.status !== 200) {
        setMessage({message: `Ошибка ${response.status}, попробуйте позже`, type: "danger"})
        return;
      }

      const conversationId = response.data.id;
      navigate(`/messages/${conversationId}`);
    }
  };

  return (
    <div className="p-3 mt-4 border rounded">
      {message && (
        <div className={`alert alert-${message.type}`}>
          {message.message}
        </div>
      )}
      <div className="d-flex justify-content-between align-items-start mb-4">
        <div>
          <div className="d-flex align-items-center gap-2 mb-2">
            <h4 className="mb-0 fw-semibold">{ title }</h4>
            <FeedbackTypeComponent type={type} />
            {accepted && (
              <small className="bg-success p-1 px-2 rounded-4 mt-1 text-light">Принят</small>
            )}
          </div>
          <div className="text-muted">{fromIsoDateToDate(createdAt)} • {sender?.firstName + " " + sender?.lastName}</div>
        </div>

        {!accepted && (
          <button
            className="btn btn-outline-success"
            onClick={handleAcceptClick}
          >
            Отметить как принятое
          </button>
        )}
      </div>

      <div className="admin-panel_feedback-text mb-4">
        {text}
      </div>

      <div className="row g-3 mb-4">
        <div className="col-md-6">
          <div className="admin-panel_info-box">
            <small className="text-muted d-block mb-1">Тип обращения</small>

            <strong>{ type }</strong>
          </div>
        </div>

        <div className="col-md-6">
          <div className="admin-panel_info-box">
            <small className="text-muted d-block mb-1">Дата создания</small>

            <strong>{ fromIsoDate(createdAt) }</strong>
          </div>
        </div>
      </div>

      <div className="text-end">
        <button
          className="btn btn-primary mx-1"
          onClick={handleOpenConversation}
        >Открыть чат с пользователем</button>

        <Link
          className="btn btn-outline-secondary mx-1"
          to={"/profile/" + sender.id}
        >Открыть профиль пользователя</Link>

        <button
          className="btn btn-outline-secondary mx-1"
          onClick={onClose}
        >Закрыть</button>
      </div>
    </div>
  );
}
