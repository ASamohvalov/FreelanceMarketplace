import { useLocation } from "react-router-dom";
import { getUserData } from "../../../logic/jwt";
import ServiceCardComponent from "./ServiceCardComponent";

export default function ServicesListComponent({ services, orderInfo }) {
  const me = getUserData()
  const location = useLocation();
  services.forEach(element => {
    return element.service
  });
  console.log(services);
  
  
  return (
    <div className="col-lg-9">
      <div className="row g-4">
        {services.map((service, item) => {
          return (
            <div className="col-md-6 col-xl-4" key={item}>
              <ServiceCardComponent
                id={service.id}
                hidden = {service.hide}
                title={service.title}
                price={service.price}
                orderInfo={orderInfo?.[item]}
                freelancerName={
                  (service?.freelancer?.firstName || me.firstName) +
                  " " +
                  (service?.freelancer?.lastName || me.lastName)
                }
                image={service.imageURL || null}
                from={location.pathname}
              />
            </div>
          );
        })}
      </div>
    </div>
  );
}
