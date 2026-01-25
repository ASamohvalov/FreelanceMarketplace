import ServiceCardComponent from "./ServiceCardComponent";

export default function ServicesListComponent({ services }) {
  return (
    <div className="row g-1">
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

      <ServiceCardComponent
        id={ 198203 }
        title={ "sfkjdlj " }
        price={ 123 }
      />
    </div>
  );
}
