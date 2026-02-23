import ServiceCardComponent from "./ServiceCardComponent";

export default function ServicesListComponent({ services }) {
  return (
    <div className="row g-4">
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
  );
}
