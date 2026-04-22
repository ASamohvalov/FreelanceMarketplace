import { useRef, useState } from "react";
import "./css/modal_window.css";
import {
  acceptOrderReportRequest,
  rejectOrderReportRequest,
} from "../../../logic/requests/order/orderReportRequest";
import { useNavigate } from "react-router-dom";
import ReactMarkdown from "react-markdown";
import { getUserData } from "../../../logic/jwt";

export default function OrderReportModalWindow({
  id,
  title,
  user,
  sendDate,
  status, // todo
  report,
  isVisible,
  onClose,
  isReceived = true,
}) {
  const commentBoxRef = useRef(null);
  const navigate = useNavigate();

  const [comment, setComment] = useState("");

  const userData = getUserData();

  return (
    <div className={`order-report-modal-window ${!isVisible && "d-none"}`}>
      <div className="d-flex justify-content-between align-items-start mb-3">
        <div>
          <h4 className="fw-semibold mb-1">{title}</h4>
          <small className="text-muted">
            {isReceived
              ? user?.firstName + " " + user?.lastName
              : userData.firstName + " " + userData.lastName}{" "}
            • {sendDate}
          </small>
        </div>

        {status === "ACCEPTED" && (
          <span className="order-report-status order-report-status_approved">
            Принят
          </span>
        )}
        {status === "REJECTED" && (
          <span className="order-report-status order-report-status_rejected">
            Отклонен
          </span>
        )}
        {status === "PENDING" && (
          <span className="order-report-status order-report-status_pending">
            На проверке
          </span>
        )}
      </div>

      <hr />

      <div className="mb-4">
        <ReactMarkdown>{report}</ReactMarkdown>
      </div>

      <div className="mb-4">
        <h6 className="fw-semibold mb-3">Файлы</h6>

        <div className="order-report-modal-window_file-item">
          <div>
            <i className="bi bi-file-earmark-text me-2"></i>
            report.pdf
          </div>
          <button className="btn btn-sm btn-outline-primary">Скачать</button>
        </div>

        <div className="order-report-modal-window_file-item">
          <div>
            <i className="bi bi-image me-2"></i>
            design.png
          </div>
          <button className="btn btn-sm btn-outline-primary">Скачать</button>
        </div>
      </div>

      <hr />

      <div className="d-flex gap-3 mb-3">
        {isReceived && status === "PENDING" && (
          <button
            className="btn btn-primary"
            onClick={() => {
              commentBoxRef.current.style.display = "block";
            }}
          >
            Отправить ответ
          </button>
        )}
        <button className="btn btn-outline-secondary" onClick={onClose}>
          Закрыть
        </button>
      </div>

      <div
        className="order-report-modal-window_comment-box mt-3"
        ref={commentBoxRef}
      >
        <label className="form-label">
          Комментарий{" "}
          <span className="order-report-modal-window_optional">
            (необязательно)
          </span>
        </label>

        <textarea
          value={comment}
          onChange={(e) => setComment(e.target.value)}
          className="form-control mb-3"
          rows="4"
          placeholder="Напишите комментарий..."
        ></textarea>

        <div className="d-flex gap-3 mb-3">
          <button
            className="btn btn-success"
            onClick={async () => {
              const response = await acceptOrderReportRequest(id, comment);
              if (response.status !== 200) {
                navigate(`/error?code=${response.status}`);
                return;
              }
              alert("Отчёт успешно принят");
              onClose();
            }}
          >
            <i className="bi bi-check-lg me-1"></i>
            Принять
          </button>

          <button
            className="btn btn-outline-danger"
            onClick={async () => {
              const response = await rejectOrderReportRequest(id, comment);
              if (response.status !== 200) {
                navigate(`/error?code=${response.status}`);
                return;
              }
              alert("Отчёт успешно отклонен");
              onClose();
            }}
          >
            <i className="bi bi-x-lg me-1"></i>
            Отклонить
          </button>
        </div>
      </div>
    </div>
  );
}
