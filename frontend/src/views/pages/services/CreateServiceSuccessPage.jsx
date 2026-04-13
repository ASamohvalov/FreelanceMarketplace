import { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";

export default function CreateServiceSuccessPage() {
  const navigate = useNavigate();
  const { state } = useLocation();

  useEffect(() => {
    if (!state) {
      navigate("/error?code=403");
    }
  }, [navigate, state]);

  return (
    <main style={{ height: "90vh" }}>
      <div className="container mt-5 mb-5">
        <div className="order-success-page_card mx-auto">
          <div className="text-center mb-4">
            <div className="order-success-page_icon mb-3">
              <i className="bi bi-check-circle-fill"></i>
            </div>
            <h3 className="fw-semibold">Вы успешно создали усулгу</h3>
          </div>

          <div className="order-success-page_info mb-4">
            <div className="row">
              <div className="col-md-6 mb-3">
                <div className="text-muted small">Услуга</div>
                <div className="fw-semibold">{state.serviceName}</div>
              </div>

              <div className="col-md-6 mb-3">
                <div className="text-muted small">Категория</div>
                <div className="fw-semibold">
                  {state.category.name + " / " + state.selectedSubcategory.name}
                </div>
              </div>

              <div className="col-md-6">
                <div className="text-muted small">Стоимость</div>
                <div className="fw-semibold">{state.price} ₽</div>
              </div>
            </div>
          </div>

          <div className="d-flex gap-3 justify-content-center flex-wrap">
            <button
              className="btn btn-main px-4"
              onClick={() => navigate(`/service/${state.serviceId}`)}
            >
              <i className="bi bi-handbag me-2"></i>
              Открыть услугу
            </button>
          </div>
        </div>
      </div>
    </main>
  );
}
