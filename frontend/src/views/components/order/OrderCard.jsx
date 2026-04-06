import { Link } from "react-router-dom";
import "../service/service_card_component.css";
import {
  hideOwnServicesRequest,
  showOwnServicesRequest,
} from "../../../logic/requests/service/serviceRequest";
import { useState } from "react";
import { now } from "../../../logic/time";

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
  title,
  price,
  freelancerName,
  image,
  isPreview,
  from,
  hidden,
  orderInfo,
}) {
  const [hiddenState, setHidden] = useState(hidden);
  const orderDate =
    orderInfo && new Date(Date.parse(orderInfo[0].order?.orderDate));
  const deadline =
    orderInfo &&
    new Date(Date.parse(orderInfo[0].order?.deadlineDate) - now()).getDate();

  return (
    <Link
      to={`/service/${id}`}
      onClick={(e) => isPreview && e.preventDefault()}
      className={`text-decoration-none text-body ${isPreview && "service-card-disable_link"}`}
    >
      <div
        className={`${orderInfo && !orderInfo[0].order.status === "IN_PROGRESS" && "bg-secondary"} service-card p-3 shadow-sm`}
      >
        {image === null ? (
          <div className="service-img mb-3"></div>
        ) : (
          <img
            className="preview-img"
            src={
              isPreview
                ? URL.createObjectURL(image)
                : image.startsWith("http")
                  ? image
                  : `http://${image}`
            }
            alt="previewimg"
          />
        )}
        <h6 className="text-muted">{freelancerName} • Фрилансер</h6>
          <h6 className="fw-semibold">{title}</h6>
        <hr></hr>
        <h6 className="text-muted">
          {orderInfo[0].customer.firstName +
            " " +
            orderInfo[0].customer.lastName}{" "}
          • Клиент
        </h6>
        {orderInfo && (
          <h6>
            Дата заказа:{" "}
            {"0" + orderDate.getMonth() + ".0" + orderDate.getDate()}
          </h6>
        )}
        {orderInfo && <h6>Осталось до конца: {deadline.toString()} д.</h6>}
        <h6>Статус: {orderInfo[0].order.status}</h6>
        <hr></hr>
        <div className="d-flex justify-content-center mb-3 align-items-center mt-3">
          <span className="service-price">Цена: {price}₽</span>
        </div>
          <button className="btn w-100 btn-outline-primary">Подробнее</button>
          <button className="btn w-100 mt-2 btn-outline-primary">Сдать</button>
      </div>
    </Link>
  );
}
