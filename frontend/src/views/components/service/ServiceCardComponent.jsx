import { Link } from "react-router-dom";
import "./service_card_component.css";

export default function ServiceCardComponent({
  id,
  title,
  price,
  freelancerName,
  image,
  isPreview,
}) {
  return (
    <Link
      to={`/service/${id}`}
      onClick={(e) => isPreview && e.preventDefault()}
      className={`text-decoration-none text-body ${isPreview && "service-card-disable_link"}`}
    >
      <div className="service-card p-3 shadow-sm">
        {image === null ? (
          <div className="service-img mb-3"></div>
        ) : (
          <img
            className="preview-img"
            src={URL.createObjectURL(image)}
            alt="previewimg"
          />
        )}
        <h6 className="fw-semibold">{title}</h6>
        <small className="text-muted">{freelancerName} • Developer</small>
        <div className="d-flex justify-content-between align-items-center mt-3">
          <span className="service-price">{price}₽</span>
          <button className="btn btn-sm btn-outline-primary">Подробнее</button>
        </div>
      </div>
    </Link>
  );
}
