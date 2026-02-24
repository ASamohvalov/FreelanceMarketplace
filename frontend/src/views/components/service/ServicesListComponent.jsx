import ServiceCardComponent from "./ServiceCardComponent";

export default function ServicesListComponent({ services }) {
  return (
    <div class="col-lg-9">
        <div class="row g-4">
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
  );
}
