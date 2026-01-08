import { Link } from "react-router-dom";

export default function ServiceCardComponent({ id, name, imgsrc, price }) {

  return (
    <div className="col">
      <Link to={ `/service/${id}` }>
        <div className="card">
          <img src={ imgsrc } className="card-img-top" alt="card image" />
          <div className="card-body">
            <h5 className="card-title">{ name }</h5>
            <p className="card-text">{ price }</p>
          </div>
        </div>
      </Link>
    </div>
  );
}
