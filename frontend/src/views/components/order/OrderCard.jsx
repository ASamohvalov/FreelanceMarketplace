import { Link } from "react-router-dom";
import {
  hideOwnServicesRequest,
  showOwnServicesRequest,
} from "../../../logic/requests/service/serviceRequest";
import { useState } from "react";
import "./css/order_card.css";
import { fromIsoDateToDate, sendAtDateToRUString } from "../../../logic/time";
import OrderStatusComponent from "./OrderStatusComponent";

function handleHide(e, id) {
  e.stopPropagation();
  e.preventDefault();
  hideOwnServicesRequest(id);
}
function handleShow(e, id) {
  e.stopPropagation();
  e.preventDefault();
  showOwnServicesRequest(id);
}

export default function OrderCard({
  id,
  serviceId,
  title,
  price,
  isPreview,
  hidden,
  customer = null,
  freelancer = null,
  deadline,
  orderDate,
  completionDate = null,
  status,
  hideButtons=false
}) {
  const [hiddenState, setHidden] = useState(hidden);

  return (
    <div className="order-card">
      <div className="d-flex justify-content-between mb-2">
        <strong>{title}</strong>
        <OrderStatusComponent status={status} />
      </div>

      {customer && (
        <div className="text-muted small mb-2">
          Заказчик: {customer.firstName + " " + customer.lastName}
        </div>
      )}

      {freelancer && (
        <div className="text-muted small mb-2">
          Исполнитель: {freelancer.firstName + " " + freelancer.lastName}
        </div>
      )}

      <hr />

      {status !== "PENDING" && (
        <div className="text-muted small mb-2">
          Дедлайн: {fromIsoDateToDate(deadline)}
        </div>
      )}
      <div className="text-muted small mb-2">
        Дата заказа: {fromIsoDateToDate(orderDate)}
      </div>
      {status !== "IN_PROGRESS" && status !== "PENDING" && status !== "CANCELLED" && (
        <div className="text-muted small mb-2">
          Дата завершения: {fromIsoDateToDate(completionDate)}
        </div>
      )}
      <div className="fw-semibold mb-3">{price} ₽</div>

      {!hideButtons && (
        <div className="d-flex gap-2 flex-wrap">
          <Link className="btn btn-sm btn-outline-primary" to={`/service/${serviceId}`}>К услуге</Link>
          <Link className="btn btn-sm btn-primary" to={`/order/info/${id}`}>Открыть заказ</Link>
        </div>
      )}
    </div>
  );
}
