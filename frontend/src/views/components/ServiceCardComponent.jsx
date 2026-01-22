import { useEffect } from "react";
import { useRef } from "react";
import { Link } from "react-router-dom";

export default function ServiceCardComponent({ id, title, imageBytes, price }) {
  const image = useRef(null);

  useEffect(() => {
    image.current.src = "data:image/png;base64," + imageBytes;
  }, [image, imageBytes]);

  return (
    <div className="col">
      <Link to={ `/service/${id}` } className="text-decoration-none">
        <div className="card">
          {
            !imageBytes ? <div className="card-img-top bg-secondary" style={{ height: "180px" }} />
              : <img className="card-img-top" alt="card image" />
          }

          <div className="card-body">
            <h5 className="card-title">{ title }</h5>
            <p className="card-text">{ price }p</p>
          </div>
        </div>
      </Link>
    </div>
  );
}
