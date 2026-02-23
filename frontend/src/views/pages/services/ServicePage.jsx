import { useNavigate, useParams } from "react-router-dom";
import HeaderComponent from "../../components/HeaderComponent";
import "./css/service_page.css";
import FooterComponent from "../../components/FooterComponent";
import { useEffect } from "react";
import { useState } from "react";
import { getServiceByIdRequest } from "../../../logic/requests/service/serviceRequest";

export default function ServicePage() {
  const { id } = useParams();

  const navigate = useNavigate();

  const [serviceData, setServiceData] = useState([]);

  useEffect(() => {
    (async () => {
      const response = await getServiceByIdRequest(id);
      if (response.status !== 200) {
        navigate("/error");
        return;
      }
      setServiceData(response.data);
    })();
  }, [navigate, id]);

  return (
    <>
      <HeaderComponent />
      <div className="container my-4">
        <nav className="mb-3 text-muted small">
          Services / {serviceData.category} / {serviceData.subcategory}
        </nav>

        <h2 className="fw-bold mb-2">{serviceData.title}</h2>

        <div className="d-flex align-items-center gap-3 mb-4">
          <span className="text-warning">★★★★★</span>
          <span>4.9 (128 reviews)</span>
          <span className="badge bg-light text-dark">{serviceData.category}</span>
          <span className="badge bg-light text-dark">{serviceData.subcategory}</span>
        </div>

        <div className="row">
          <div className="col-lg-8">
            <div className="service-collage">
              <div className="collage-main"></div>
              <div className="collage-side">
                <div className="collage-item"></div>
                <div className="collage-item"></div>
                <div className="collage-item"></div>
                <div className="collage-item"></div>
              </div>
            </div>

            <div className="card p-4 mb-4 rounded-4">
              {serviceData.description}
            </div>

            <div className="card p-4 mb-4 rounded-4">
              <h4 className="mb-3">Reviews</h4>

              <div className="review">
                <div className="d-flex justify-content-between">
                  <strong>Alex</strong>
                  <span className="text-warning">★★★★★</span>
                </div>
                <p className="mt-2">
                  Amazing experience. The website looks clean and works perfectly.
                </p>
              </div>

              <div className="review">
                <div className="d-flex justify-content-between">
                  <strong>Maria</strong>
                  <span className="text-warning">★★★★☆</span>
                </div>
                <p className="mt-2">
                  Fast delivery and good communication. Would order again.
                </p>
              </div>
            </div>
          </div>
          <div className="col-lg-4">

            <div className="card p-4 service-sidebar rounded-4">
              <div className="price mb-3">{serviceData.price} ₽</div>

              <button className="btn btn-primary w-100 mb-3">
                Оформить заказ
              </button>

              <button
                className="btn btn-primary w-100 mb-3"
                onClick={() => alert("kadjkad")}
              >
                Оставить отклик на обсуждение
              </button>
              <hr/>

              <div className="d-flex align-items-center gap-3">
                <div className="avatar">CJ</div>
                <div>
                  <strong>{serviceData.freelancer?.firstName + " " + serviceData.freelancer?.lastName}</strong>
                  <div className="text-muted small">Web Developer</div>
                </div>
              </div>

              <ul className="list-unstyled mt-3 small">
                <li>✔ Response time: 1 hour</li>
                <li>✔ Delivery: 3 days</li>
                <li>✔ Revisions: 2</li>
              </ul>
            </div>

          </div>
        </div>

        <h4 className="mt-5 mb-4">More services by this seller</h4>

        <div className="row g-4">
          <div className="col-md-4 col-lg-3">
            <div className="service-card">
              <div className="service-card-img"></div>
              <div className="service-card-body">
                <strong>Landing page design</strong>
                <div className="text-muted small">from 1499 ₽</div>
              </div>
            </div>
          </div>

          <div className="col-md-4 col-lg-3">
            <div className="service-card">
              <div className="service-card-img"></div>
              <div className="service-card-body">
                <strong>WooCommerce setup</strong>
                <div className="text-muted small">from 2499 ₽</div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <FooterComponent />
    </>
  );
}
