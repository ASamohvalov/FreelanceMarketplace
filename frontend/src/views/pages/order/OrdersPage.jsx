import { useEffect } from "react";
import HeaderComponent from "../../components/HeaderComponent";
import { useState } from "react";
import LoadingComponent from "../../components/LoadingComponent";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";
import ServicesListComponent from "../../components/service/ServicesListComponent";
import "../services/css/services_page.css";
import { Filters } from "../../components/services/Filters";
import { getUserData } from "../../../logic/jwt";
import {
  getOrderCustomerRequest,
  getOrderFreelancerRequest,
} from "../../../logic/requests/service/serviceRequest";

export default function OrdersPage({ func, freelancer }) {
  const navigate = useNavigate();
  const [services, setServices] = useState([]);
  const [orders, setOrders] = useState([]);
  // const [images, setImages] = useState([]);
  const [loading, setLoading] = useState(true);
  const [variant, setVariant] = useState("заказы");
  const [searchParams, setSearchParams] = useSearchParams();
  const [filters, setFilters] = useState({
    title: searchParams.get("search") || "",
    price: 0,
    maxPrice: 0,
    maxPriceFreelancer: 0,
    active: searchParams.get("search") || false,
  });

  const isFreelancer = getUserData()?.roles.includes("ROLE_FREELANCER");
  useEffect(() => {
    document.title = "Мои заказы";

    (async () => {
      const result = await getOrderCustomerRequest();
      let resultFreelancer;
      if (isFreelancer) {
        resultFreelancer = await getOrderFreelancerRequest();
        if (resultFreelancer.status !== 200) {
          navigate("/error");
          return;
        }
        setOrders(resultFreelancer.data);
      }
      if (result.status !== 200) {
        navigate("/error");
        return;
      }
      setServices(result.data);

      const maxPrice = Math.max(
        ...result.data.map((service) => service.service.price),
      );
      let maxPriceFreelancer = 0;
      if (isFreelancer) {
        maxPriceFreelancer = Math.max(
          ...resultFreelancer.data.map((service) => service.service.price),
        );
      }
      setFilters((prev) => ({
        ...prev,
        price: maxPrice,
        maxPrice: maxPrice,
        maxPriceFreelancer: maxPriceFreelancer,
      }));
      setLoading(false);
    })();
  }, [navigate, func, isFreelancer]);
  console.log(orders);

  return (
    <>
      <main style={{ minHeight: "80vh", marginLeft: 80 }}>
        <div className="container mt-4 mb-4">
          <div className="row align-items-start">
            <div
              className="col-lg-3 mb-4 position-sticky"
              style={{ top: "82px" }}
            >
              <Filters
                services={[services, setServices]}
                filters={filters}
                setFilters={setFilters}
                variant={variant}
              />
              {isFreelancer && (
                <div className="flex flex-row gap-5">
                  <button
                    onClick={() => {
                      setVariant("заказы");
                    }}
                  >
                    Мои заказы
                  </button>
                  <button
                    onClick={() => {
                      setVariant("работы");
                      setFilters((prev) => ({
                        ...prev,
                        price: filters.maxPriceFreelancer,
                      }));
                    }}
                  >
                    Мои работы
                  </button>
                </div>
              )}
            </div>

            {loading ? (
              <LoadingComponent />
            ) : variant === "заказы" ? (
              <ServicesListComponent
                services={services
                  .map((service) => service.service)
                  .filter((service) => {
                    return (
                      service.title
                        .toLowerCase()
                        .includes(filters.title.toLowerCase()) &&
                      service.price <= filters.price
                    );
                  })}
                orderInfo={services.map((service) => service.order)}
              />
            ) : (
              <ServicesListComponent
                services={orders
                  .map((service) => service.service)
                  .filter((service) => {
                    return (
                      service.title
                        .toLowerCase()
                        .includes(filters.title.toLowerCase()) &&
                      service.price <= filters.price
                    );
                  })}
                orderInfo={services.map((service) => service.order)}
              />
            )}
          </div>
        </div>
      </main>
    </>
  );
}
