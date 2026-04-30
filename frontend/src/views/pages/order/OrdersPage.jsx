import { useEffect } from "react";
import HeaderComponent from "../../components/HeaderComponent";
import { useState } from "react";
import LoadingComponent from "../../components/LoadingComponent";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";
import ServicesListComponent from "../../components/service/ServicesListComponent";
import "../services/css/services_page.css";
import { Filters } from "../../components/services/Filters";
import { getUserData, hasRole } from "../../../logic/jwt";
import {
  getOrderCustomerRequest,
  getOrderFreelancerRequest,
} from "../../../logic/requests/order/orderRequest";
import OrderCard from "../../components/order/OrderCard";

export default function OrdersPage({ func, freelancer }) {
  const navigate = useNavigate();
  const [services, setServices] = useState([]);
  const [orders, setOrders] = useState([]);
  // const [images, setImages] = useState([]);
  const [loading, setLoading] = useState(true);
  const [variant, setVariant] = useState("orders");
  const [searchParams, setSearchParams] = useSearchParams();
  const [filters, setFilters] = useState({
    title: searchParams.get("search") || "",
    price: 0,
    maxPrice: 0,
    maxPriceFreelancer: 0,
    active: searchParams.get("search") || false,
  });

  const isFreelancer = hasRole("ROLE_FREELANCER");
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

        setVariant("works");
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

  return (
    <main style={{ minHeight: "90vh" }}>
      <div className="container mt-5 mb-5">
        <h3 className="mb-4 fw-semibold">Заказы</h3>

        <div className="order-nav mb-4">
          {isFreelancer && (
            <>
              <button
                className={variant === "works" ? "active" : ""}
                onClick={() => {
                  setVariant("works");
                }}
              >
                Мои работы
              </button>
              <button
                className={variant === "orders" ? "active" : ""}
                onClick={() => {
                  setVariant("orders");
                }}
              >
                Мои заказы
              </button>
            </>
          )}
        </div>

        <div className="row g-4">
          {variant === "works"
            ? orders.map((order, idx) => (
                <div className="col-md-6 col-xl-4" key={idx}>
                  <OrderCard
                    id={order.order.id}
                    serviceId={order.service.id}
                    title={order.service.title}
                    customer={order.customer}
                    deadline={order.order.deadlineDate}
                    orderDate={order.order.orderDate}
                    completionDate={order.order.completionDate || null}
                    price={order.service.price}
                    status={order.order.status}
                  />
                </div>
              ))
            : services.map((order, idx) => (
                <div className="col-md-6 col-xl-4" key={idx}>
                  <OrderCard
                    id={order.order.id}
                    serviceId={order.service.id}
                    title={order.service.title}
                    freelancer={order.service.freelancer}
                    deadline={order.order.deadlineDate}
                    orderDate={order.order.orderDate}
                    completionDate={order.order.completionDate || null}
                    price={order.service.price}
                    status={order.order.status}
                  />
                </div>
              ))}
        </div>
      </div>
    </main>
  );
}
