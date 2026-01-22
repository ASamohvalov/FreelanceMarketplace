import { useEffect } from "react";
import HeaderComponent from "../../components/HeaderComponent";
import { useState } from "react";
import LoadingComponent from "../../components/LoadingComponent";
import { getAllServicesRequest, getImageByServiceId } from "../../../logic/requests/service/serviceRequest";
import { useNavigate } from "react-router-dom";
import ServiceCardComponent from "../../components/ServiceCardComponent";

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

  /*
  useEffect(() => {
    if (!loading) {
      services.map(async (service) => {
        const imgResult = await getImageByServiceId(service.id);
        if (imgResult.status !== 200) {
          navigate("/error");
          return;
        }
        // setImages(prev => [...prev, imgResult.data]); // todo
      });
    }
  }, [services, loading, navigate]);
  */

  return (
    <>
      <HeaderComponent />
      <main>
        {loading ? (
          <LoadingComponent />
        ) : (
          <div className="p-1">
            <div className="row row-cols-1 row-cols-md-3 g-4">
              {
                services.map((service, item) => {
                  return (
                    <ServiceCardComponent
                      key={ item }
                      id={ service.id }
                      title={ service.title }
                      price={ service.price }
                    />
                  );
                })
              }
            </div>
          </div>
        )}
      </main>
    </>
  );
}
