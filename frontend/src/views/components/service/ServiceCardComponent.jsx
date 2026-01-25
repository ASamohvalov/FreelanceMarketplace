import { Link } from "react-router-dom";
import "./service_card_component.css";

export default function ServiceCardComponent({ id, title, price }) {
  return (
    <div className="col col-sm-6 col-md-4 col-lg-3 mx-5">
      <Link to={ `/service/${id}` } className="text-decoration-none">
        <div className="service-card">
          <div className="card-image"></div>
          <div className="card-body-custom">
            <div className="card-title">{ title }</div>
            <div className="card-subtitle">Cris James<br/>programmer</div>
            <div className="card-price text-center">{ price }р</div>
          </div>
        </div>
      </Link>
    </div>
  );
}
