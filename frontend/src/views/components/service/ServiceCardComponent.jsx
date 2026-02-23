import { Link } from "react-router-dom";
import "./service_card_component.css";

export default function ServiceCardComponent({ id, title, price }) {
  return (
    <div className="col-sm-6 col-md-4 col-lg-3">
      <Link to={ `/service/${id}` } className="text-decoration-none">
        <div className="service-card">
          <div className="service-img"></div>
          <div className="service-info">
            <h5>{ title }</h5>
            <small>Cris James<br/>programmer</small>
            <div className="price">{ price }p</div>
          </div>
        </div>
      </Link>
    </div>
  );
}
