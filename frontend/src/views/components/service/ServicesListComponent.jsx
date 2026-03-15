import ServiceCardComponent from "./ServiceCardComponent";

export default function ServicesListComponent({ services }) {
  return (
    <div className="col-lg-9">
      <div className="row g-4">
        {services.map((service, item) => {
          return (
            <div className="col-md-6 col-xl-4" key={item}>
              <ServiceCardComponent
                id={service.id}
                title={service.title}
                price={service.price}
                freelancerName={
                  service.freelancer.firstName +
                  " " +
                  service.freelancer.lastName
                }
                image={service.imageURL || null}
              />
            </div>
          );
        })}
      </div>
    </div>
  );
}
