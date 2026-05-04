import { Link, useNavigate, useParams } from "react-router-dom";
import OrderCard from "../../components/order/OrderCard";
import "./css/order_page.css";
import { useEffect } from "react";
import { useState } from "react";
import { getOrderByIdRequest, sendRejectOrderRequest } from "../../../logic/requests/order/orderRequest";
import {
  calculateDays,
  fromIsoDateToDate,
  getDayRUString,
} from "../../../logic/time";
import { getUserData } from "../../../logic/jwt";

export default function OrderPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [order, setOrder] = useState([]);
  const [userExecutor, setUserExecutor] = useState(false);

  useEffect(() => {
    document.title = "заказ";

    (async () => {
      const response = await getOrderByIdRequest(id);
      if (response.status !== 200) {
        navigate(`/error?code=${response.status}`);
      }
      setOrder(response.data);

      setUserExecutor(response.data.freelancer.userId === getUserData().id);
    })();
  }, [id, navigate]);

  return (
    <main style={{ minHeight: "90vh" }}>
      <div className="container mt-5 mb-5">
        <div className="row g-4">
          <div className="col-lg-8">
            <OrderCard
              id={order?.order?.id}
              serviceId={order?.service?.id}
              title={order?.service?.title}
              customer={order?.customer}
              freelancer={order?.freelancer}
              deadline={order?.order?.deadlineDate}
              orderDate={order?.order?.orderDate}
              completionDate={order?.order?.completionDate || null}
              price={order?.service?.price}
              status={order?.order?.status}
              hideButtons={true}
            />

            <div className="order-card_box mb-4 mt-4">
              <h6 className="fw-semibold mb-3">Действия</h6>

              <div className="d-flex flex-wrap gap-2">
                <Link className="btn btn-primary" to="/messages">
                  <i className="bi bi-chat-dots me-1"></i>
                  Перейти в чат
                </Link>

                {userExecutor ? (order.order.status !== "COMPLETED") && (
                  <button
                    className="btn btn-success"
                    onClick={() =>
                      navigate("/order/report/send", {
                        state: {
                          orderId: order.order.id,
                          serviceTitle: order.service.title,
                          serviceId: order.service.id,
                          freelancer: order.freelancer,
                        },
                      })
                    }
                  >
                    <i className="bi bi-check-circle me-1" />
                    Сдать работу
                  </button>
                ) : (
                  <>
                    {order?.order?.status === "COMPLETED" && (
                      <Link
                        className="btn btn-outline-secondary"
                        to="/review/send"
                      >
                        <i className="bi bi-star me-1" />
                        Оставить отзыв
                      </Link>
                    )}

                      <button
                        className="btn btn-outline-danger"
                        onClick={async () => {
                          const response = await sendRejectOrderRequest(order.order.id);
                          if (response.status !== 200) {
                            navigate(`/error?code=${response.status}`);
                            return;
                          }
                          alert("Заказ отменён, деньги возвращены");
                        }}
                      >
                        <i className="bi bi-x-circle me-1" />
                      Отменить заказ
                    </button>
                  </>
                )}
              </div>
            </div>

            <div className="order-card_box">
              <Link
                className="btn btn-outline-primary w-100"
                to={{pathname:"/order/reports", search: `?orderId=${order?.order?.id}`}}

              >
                Открыть отчёты
              </Link>
            </div>
          </div>

          <div className="col-lg-4">
            <div className="order-card_box order-page_sidebar position-sticky">
              <h6 className="fw-semibold mb-3">Информация</h6>

              <div className="mb-2">
                <div className="text-muted small">Статус</div>

                {order?.order?.status === "COMPLETED" && <div>Завершён</div>}
                {order?.order?.status === "REJECTED" && <div>Отклонен</div>}
                {order?.order?.status === "IN_PROGRESS" && <div>В работе</div>}
              </div>

              <div className="mb-2">
                <div className="text-muted small">Дата заказа</div>
                <div>{fromIsoDateToDate(order?.order?.orderDate)}</div>
              </div>

              <div className="mb-2">
                <div className="text-muted small">До окончания срока</div>
                <div>
                  {getDayRUString(
                    calculateDays(
                      order?.order?.orderDate,
                      order?.order?.deadlineDate,
                    ),
                  )}
                </div>
              </div>

              <div className="mb-2">
                <div className="text-muted small">Ревизии</div>
                <div>3</div>
              </div>

              <hr />

              <Link
                className="btn btn-outline-primary w-100 mb-2"
                to={`/service/${order?.service?.id}`}
              >
                К услуге
              </Link>
            </div>
          </div>
        </div>
      </div>
    </main>
  );
}
