import FooterComponent from "../../components/FooterComponent";
import HeaderComponent from "../../components/HeaderComponent";
import "./css/payment_page.css";

export default function PaymentPage() {
  return (
    <>
      <HeaderComponent />
      <main style={{ height: "90vh" }}>
        <div className="container mt-5">
          <h3 className="fw-semibold">Оплата заказа</h3>
          <p className="mb-4 text-secondary">Исполнитель получит деньги только после окончяния работы</p>

          <div className="row g-4">
            <div className="col-lg-7">
              <div className="payment-card">
                <h5 className="mb-4">Данные карты</h5>

                <div className="mb-3">
                  <label className="form-label">Номер карты</label>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="1234 5678 9012 3456"
                  />
                </div>

                <div className="row">
                  <div className="col-md-6 mb-3">
                    <label className="form-label">Срок действия</label>
                    <input
                      type="text"
                      className="form-control"
                      placeholder="MM / YY"
                    />
                  </div>

                  <div className="col-md-6 mb-3">
                    <label className="form-label">CVV</label>
                    <input
                      type="text"
                      className="form-control"
                      placeholder="123"
                    />
                  </div>
                </div>

                <button className="btn btn-primary w-100 mt-2">Оплатить</button>
              </div>
            </div>

            <div className="col-lg-5">
              <div className="order-summary">
                <h5 className="mb-4">Детали заказа</h5>

                <div className="d-flex justify-content-between mb-2">
                  <span>Услуга</span>
                  <span>Разработка сайта</span>
                </div>

                <div className="d-flex justify-content-between mb-2">
                  <span>Исполнитель</span>
                  <span>Иван Петров</span>
                </div>

                <div className="d-flex justify-content-between mb-2">
                  <span>Стоимость</span>
                  <span>5 000 ₽</span>
                </div>

                <div className="d-flex justify-content-between mb-2">
                  <span>Комиссия сервиса</span>
                  <span>200 ₽</span>
                </div>

                <hr />

                <div className="d-flex justify-content-between total">
                  <span>Итого</span>
                  <span>5 200 ₽</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>
      <FooterComponent />
    </>
  );
}
