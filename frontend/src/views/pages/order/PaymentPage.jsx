import { useEffect } from "react";
import FooterComponent from "../../components/FooterComponent";
import HeaderComponent from "../../components/HeaderComponent";
import "./css/payment_page.css";
import { useNavigate, useParams } from "react-router-dom";
import getPaymentInfoRequest from "../../../logic/requests/service/serviceRequest";
import { useState } from "react";

export default function PaymentPage() {
  const { serviceId } = useParams();
  const navigate = useNavigate();

  const [paymentInfo, setPaymentInfo] = useState({});

  const [cardNumber, setCardNumber] = useState("");
  const [validityPeriod, setValidityPeriod] = useState("");
  const [cardCVV, setCardCVV] = useState("");

  useEffect(() => {
    (async () => {
      const paymentResponse = await getPaymentInfoRequest(serviceId);
      if (paymentResponse.status !== 200) {
        navigate(`/error?code=${paymentResponse.status}`);
        return;
      }
      setPaymentInfo(paymentResponse.data);
    })();
  }, [serviceId, navigate]);

  function onSubmit() {
    // todo validate
  }

  return (
    <>
      <HeaderComponent />
      <main style={{ height: "90vh" }}>
        <div className="container mt-5">
          <h3 className="fw-semibold">Оплата заказа</h3>
          <p className="mb-4 text-secondary">
            Исполнитель получит деньги только после окончяния работы
          </p>

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
                    value={cardNumber}
                    onChange={(e) => {
                      if (e.target.value.length !== 16) {
                        setCardNumber(e.target.value);
                      }
                    }}
                  />
                </div>

                <div className="row">
                  <div className="col-md-6 mb-3">
                    <label className="form-label">Срок действия</label>
                    <input
                      type="text"
                      className="form-control"
                      placeholder="MM / YY"
                      value={validityPeriod}
                      onChange={(e) => setValidityPeriod(e.target.value)}
                    />
                  </div>

                  <div className="col-md-6 mb-3">
                    <label className="form-label">CVV</label>
                    <input
                      type="text"
                      className="form-control"
                      placeholder="123"
                      value={cardCVV}
                      onChange={(e) => setCardCVV(e.target.value)}
                    />
                  </div>
                </div>

                <button
                  onClick={onSubmit}
                  className="btn btn-primary w-100 mt-2"
                >
                  Оплатить
                </button>
              </div>
            </div>

            <div className="col-lg-5">
              <div className="order-summary">
                <h5 className="mb-4">Детали заказа</h5>

                <div className="d-flex justify-content-between mb-2">
                  <span>Услуга</span>
                  <span>{paymentInfo?.serviceName}</span>
                </div>

                <div className="d-flex justify-content-between mb-2">
                  <span>Исполнитель</span>
                  <span>
                    {paymentInfo?.freelancerFirstName +
                      " " +
                      paymentInfo.freelancerLastName}
                  </span>
                </div>

                <div className="d-flex justify-content-between mb-2">
                  <span>Стоимость</span>
                  <span>{paymentInfo?.price} ₽</span>
                </div>

                <div className="d-flex justify-content-between mb-2">
                  <span>Комиссия сервиса</span>
                  <span>{paymentInfo?.commission} ₽</span>
                </div>

                <hr />

                <div className="d-flex justify-content-between total">
                  <span>Итого</span>
                  <span>{paymentInfo?.price + paymentInfo?.commission} ₽</span>
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
