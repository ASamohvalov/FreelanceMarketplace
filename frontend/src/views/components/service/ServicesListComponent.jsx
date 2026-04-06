import { useLocation } from "react-router-dom";
import { getUserData } from "../../../logic/jwt";
import ServiceCardComponent from "./ServiceCardComponent";
import OrderCard from "../order/OrderCard";

export default function ServicesListComponent({ services, orderInfo, isOrder }) {
  const me = getUserData();
  const location = useLocation();
  console.log(services, orderInfo);

  return (
    <div className="col-lg-9">
      <div className="row g-4">
        {services.map((service, item) => {
          return (
            <div className="col-md-6 col-xl-4" key={item}>
              {isOrder ? (
                <OrderCard
                  id={service.id}
                  hidden={service.hide}
                  title={service.title}
                  price={service.price}
                  orderInfo={orderInfo?.filter(item=>item.service.id===service.id)}
                  freelancerName={
                    (service?.freelancer?.firstName || me.firstName) +
                    " " +
                    (service?.freelancer?.lastName || me.lastName)
                  }
                  image={service.imageURL || null}
                  from={location.pathname}
                  />
                ) : (
                  <ServiceCardComponent
                  id={service.id}
                  hidden={service.hide}
                  title={service.title}
                  price={service.price}
                  orderInfo={orderInfo?.filter(item=>item.service.id===service.id)}
                  freelancerName={
                    (service?.freelancer?.firstName || me.firstName) +
                    " " +
                    (service?.freelancer?.lastName || me.lastName)
                  }
                  image={service.imageURL || null}
                  from={location.pathname}
                />
              )}
            </div>
          );
        })}
      </div>
    </div>
  );
}
