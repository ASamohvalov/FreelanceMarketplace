import { getFileRUString } from "../../../logic/file";
import "./css/order_report_card_component.css";

export default function OrderReportCardComponent({
  title,
  user,
  status,
  sendDate,
  isReceived=true,
  fileCount,
  onOpen,
  comment=null,
}) {
  return (
    <div className="report-card">

      <div className="d-flex justify-content-between align-items-center mb-2">
        {status === "ACCEPTED" && <span className="order-report-status order-report-status_approved">Принят</span>}
        {status === "REJECTED" && <span className="order-report-status order-report-status_rejected">Отклонен</span>}
        {status === "PENDING" && <span className="order-report-status order-report-status_pending">На проверке</span>}
        <small className="text-muted">{ sendDate }</small>
      </div>

      <div className="mb-2">
        <div className="text-muted small">{isReceived ? "Исполнитель" : "Получатель"}</div>
        <div className="fw-semibold">{user?.firstName + " " + user?.lastName}</div>
      </div>

      <p className="report-text mb-3">
        {title}
      </p>

      <div className="d-flex justify-content-between align-items-center">

        <div className="d-flex align-items-center gap-2 text-muted small">
          <i className="bi bi-paperclip"></i>
          <span>{getFileRUString(fileCount ? fileCount : 0)}</span>
        </div>

        <div className="d-flex gap-2">
          <button
            className="btn btn-sm btn-outline-primary"
            onClick={onOpen}
          >
            Открыть
          </button>
        </div>
      </div>

      {comment && (
        <div className="p-2 border mt-4 rounded">
          <small className="text-secondary">Комментарий</small>
          <div>{comment}</div>
        </div>
      )}
    </div>
  );
}
