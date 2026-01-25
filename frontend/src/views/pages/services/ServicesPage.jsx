import { useEffect } from "react";
import HeaderComponent from "../../components/HeaderComponent";
import { useState } from "react";
import LoadingComponent from "../../components/LoadingComponent";
import { getAllServicesRequest } from "../../../logic/requests/service/serviceRequest";
import { useNavigate } from "react-router-dom";
import ServicesListComponent from "../../components/service/ServicesListComponent";

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
      <main className="p-5 row">
        <div className="col-3 bg-secondary-custom rounded-4">
        </div>
        <div className="col-7">
          {loading ? (
            <LoadingComponent />
          ) : (
            <div className="px-5">
              <ServicesListComponent services={ services } />
            </div>
          )}
        </div>
      </main>
    </>
  );
}
