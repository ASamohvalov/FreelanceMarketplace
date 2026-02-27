import { Link } from "react-router-dom";
import "./service_card_component.css";

export default function ServiceCardComponent({ id, title, price, freelancerName }) {
  return (
    <div className="col-md-6 col-xl-4">
      <Link to={`/service/${id}`} className="text-decoration-none text-body">
        <div className="service-card p-3 shadow-sm">
          <div className="service-img mb-3"></div>
          <h6 className="fw-semibold">{title}</h6>
          <small className="text-muted">{freelancerName} • Developer</small>
          <div className="d-flex justify-content-between align-items-center mt-3">
            <span className="service-price">{price}₽</span>
            <button className="btn btn-sm btn-outline-primary">Подробнее</button>
          </div>
        </div>
      </Link>
    </div>
  );
}
