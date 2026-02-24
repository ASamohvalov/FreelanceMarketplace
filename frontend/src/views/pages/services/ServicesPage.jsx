import { useEffect } from "react";
import HeaderComponent from "../../components/HeaderComponent";
import { useState } from "react";
import LoadingComponent from "../../components/LoadingComponent";
import { getAllServicesRequest } from "../../../logic/requests/service/serviceRequest";
import { useNavigate } from "react-router-dom";
import ServicesListComponent from "../../components/service/ServicesListComponent";
import FooterComponent from "../../components/FooterComponent";
import "./css/services_page.css";

export default function ServicesPage() {
  const navigate = useNavigate();
  const [services, setServices] = useState([]);
  // const [images, setImages] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    document.title = "Services";

    (async () => {
      const result = await getAllServicesRequest();
      if (result.status !== 200) {
        navigate("/error");
        return;
      }
      setServices(result.data);
      setLoading(false);
    })();
  }, [navigate]);

  return (
    <>
      <HeaderComponent />

      <main>
        <div className="container mt-4 mb-4">
          <div className="row align-items-start">
            <div className="col-lg-3 mb-4">
              <div
                className="sidebar shadow-sm position-sticky"
                style={{ top: "20px" }}
              >
                <h5 className="mb-3">Фильтры</h5>

                <div className="mb-3">
                  <label className="form-label">Название</label>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Поиск..."
                  />
                </div>

                <div className="mb-3">
                  <label className="form-label">Цена до</label>
                  <input type="range" className="form-range" />
                </div>

                <div className="mb-3">
                  <label className="form-label">Тип услуги</label>
                  <select className="form-select">
                    <option>Все</option>
                    <option>Frontend</option>
                    <option>Backend</option>
                    <option>Design</option>
                  </select>
                </div>

                <button className="btn btn-primary w-100">Применить</button>
              </div>
            </div>

            {loading ? (
              <LoadingComponent />
            ) : (
              <ServicesListComponent services={services} />
            )}
          </div>
        </div>
      </main>
      <FooterComponent />
    </>
  );
}
