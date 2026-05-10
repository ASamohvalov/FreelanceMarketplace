import "./css/order_card.css";

export default function OrderStatusComponent({ status }) {
  return (
    <>
      {status === "COMPLETED" && (
        <span className="order-report-status order-report-status_approved">
          Завершён
        </span>
      )}
      {status === "REJECTED" && (
        <span className="order-report-status order-report-status_rejected">
          Отклонен
        </span>
      )}
      {status === "IN_PROGRESS" && (
        <span className="order-report-status order-report-status_pending">
          В работе
        </span>
      )}
      {status === "CANCELLED" && (
        <span className="order-report-status order-report-status_rejected">
          Отменён исполнителем
        </span>
      )}
      {status === "PENDING" && (
        <span className="order-report-status order-report-status_pending">
          В ожидании
        </span>
      )}
      {status === "SUBMITTED" && (
        <span className="order-report-status order-report-status_approved">
          Отправлен на проверку
        </span>
      )}
      {status === "WAITING_FOR_REJECT" && (
        <span className="order-report-status order-report-status_rejected">
          Направлен на отмену
        </span>
      )}
    </>
  );
}
