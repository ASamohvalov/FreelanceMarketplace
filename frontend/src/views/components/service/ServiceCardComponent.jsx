import { Link } from "react-router-dom";
import "./service_card_component.css";

export default function ServiceCardComponent({ id, title, price }) {
  return (
    <div class="col-md-6 col-xl-4">
      <Link to={`/service/${id}`} className="text-decoration-none text-body">
        <div class="card p-3 shadow-sm">
          <div class="service-img mb-3"></div>
          <h6 class="fw-semibold">{title}</h6>
          <small class="text-muted">Cris James • Developer</small>
          <div class="d-flex justify-content-between align-items-center mt-3">
            <span class="price">{price}₽</span>
            <button class="btn btn-sm btn-outline-primary">Подробнее</button>
          </div>
        </div>
      </Link>
    </div>
  );
}
