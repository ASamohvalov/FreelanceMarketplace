import ServiceCardComponent from "./ServiceCardComponent";

export default function ServicesListComponent({ services }) {
  return (
    <div className="col-lg-9">
        <div className="row g-4">
      {
        services.map((service, item) => {
          return (
            <ServiceCardComponent
              key={ item }
              id={ service.id }
              title={ service.title }
              price={ service.price }
              freelancerName={ service.freelancer.firstName + " " + service.freelancer.lastName }
            />
          );
        })
      }
    </div>
    </div>
  );
}
