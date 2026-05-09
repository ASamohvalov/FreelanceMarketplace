import "./css/success_info_component.css"

export default function SuccessInfoComponent({title, description, children}) {

  return (
      <div className="container mt-5 mb-5">
        <div className="order-success-component_card mx-auto">
          <div className="text-center mb-4">
            <div className="order-success-component_icon mb-3">
              <i className="bi bi-check-circle-fill"></i>
            </div>
            <h3 className="fw-semibold">{title}</h3>
            <p className="text-muted">
              {description}
            </p>
          </div>

          {children}
        </div>
      </div>
  );
}
