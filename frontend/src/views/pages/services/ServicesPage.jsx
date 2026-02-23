import { useEffect } from "react";
import HeaderComponent from "../../components/HeaderComponent";
import { useState } from "react";
import LoadingComponent from "../../components/LoadingComponent";
import { getAllServicesRequest } from "../../../logic/requests/service/serviceRequest";
import { useNavigate } from "react-router-dom";
import ServicesListComponent from "../../components/service/ServicesListComponent";
import FooterComponent from "../../components/FooterComponent";

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

      <div class="row p-4">
        <aside className="col-lg-3 mb-4">
          <div className="card mb-3 p-4 rounded-4">
            <div className="d-flex justify-content-between align-items-center mb-2">
              <h6 className="mb-0">Filters</h6>
              <button className="btn btn-sm btn-light">Reset All</button>
            </div>

            <label className="form-label mt-2">Name</label>
            <div className="input-group mb-2">
              <input type="text" className="form-control" />
              <button className="btn btn-primary">Find</button>
            </div>

            <label className="form-label mt-2">Price</label>
            <input type="range" className="form-range" />

            <label className="form-label mt-2">Service type</label>
            <select className="form-select">
              <option selected>Choose...</option>
              <option>WordPress</option>
              <option>Design</option>
            </select>
          </div>

          <div className="card p-4 rounded-4">
            <h6>Navigation</h6>
            <ul className="list-unstyled mt-2">
              <li className="mb-2">👤 Personal Account</li>
              <li className="mb-2">💬 Messages</li>
              <li>📢 Feedback</li>
            </ul>
          </div>
        </aside>
        <main className="col-lg-9">
          {loading ? (
            <LoadingComponent />
          ) : (
            <ServicesListComponent services={services} />
          )}
        </main>
      </div>

      <FooterComponent />
    </>
  );
}
