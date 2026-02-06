import { Link } from "react-router-dom";
import "./service_card_component.css";

export default function ServiceCardComponent({ id, title, price }) {
  return (
    <div className="col-sm-6 col-md-4 col-lg-3">
      <Link to={ `/service/${id}` } className="text-decoration-none">
        <div class="service-card">
          <div class="service-img"></div>
          <div class="service-info">
            <h5>{ title }</h5>
            <small>Cris James<br/>programmer</small>
            <div class="price">{ price }p</div>
          </div>
        </div>
      </Link>
    </div>
  );
}
