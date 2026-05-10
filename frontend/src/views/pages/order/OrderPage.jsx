import { Link, useNavigate, useParams } from "react-router-dom";
import OrderCard from "../../components/order/OrderCard";
import "./css/order_page.css";
import { useEffect } from "react";
import { useState } from "react";
import {
  getOrderByIdRequest,
  sendAcceptOrderRequest,
  sendCancelOrderRequest,
  sendRejectOrderRequest,
} from "../../../logic/requests/order/orderRequest";
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

  const status = order?.order?.status;
  const orderId = order?.order?.id;

  const canSubmitWork = userExecutor && !["COMPLETED", "CANCELED", "PENDING", "SUBMITTED", "REJECTED"].includes(status);
  const canLeaveReview = !userExecutor && status === "COMPLETED";
  const waitingForRejectShow = status === "WAITING_FOR_REJECT" && (userExecutor ? order?.order?.rejectByCustomer : order?.order?.rejectByFreelancer);
  const canReject = !["REJECTED", "CANCELED", "COMPLETED"].includes(status) && (!userExecutor || status !== "PENDING");
  const canManagePending = userExecutor && status === "PENDING";

  useEffect(() => {
    (async () => {
      const response = await getOrderByIdRequest(id);
      if (response.status !== 200) {
        navigate(`/error?code=${response.status}`);
      }
      setOrder(response.data);

      document.title = response.data.service.title;

      setUserExecutor(response.data.freelancer.userId === getUserData().id);
    })();
  }, [id, navigate]);

  return (
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

                {canSubmitWork && (
                  <button
                    className="btn btn-success"
                    onClick={() => navigate("/order/report/send", {
                      state: { orderId, serviceTitle: order.service.title, serviceId: order.service.id, freelancer: order.freelancer }
                    })}
                  >
                    <i className="bi bi-check-circle me-1" />
                    Сдать работу
                  </button>
                )}

                {canLeaveReview && (
                  <button
                    className="btn btn-outline-secondary"
                    onClick={() => navigate("/review/send", {
                      state: { orderId, serviceId: order.service.id, serviceTitle: order.service.title, freelancer: order.freelancer }
                    })}
                  >
                    <i className="bi bi-star me-1" />
                    Оставить отзыв
                  </button>
                )}

                {canReject && (
                  <button
                    className="btn btn-outline-danger"
                    onClick={async () => {
                      if (!confirm("Вы уверены что хотите отменить заказ?")) return;
                      const response = await sendRejectOrderRequest(
                        order.order.id,
                      );
                      if (response.status !== 200) {
                        navigate(`/error?code=${response.status}`);
                        return;
                      }

                      if (userExecutor && !order.order.rejectByCustomer) {
                        alert("Направлен запрос на отмену заказа, ожидайте ответ от заказчика");
                      } else if (status !== "PENDING" && !order.order.rejectByFreelancer) {
                        alert("Направлен запрос на отмену заказа, ожидайте ответ от исполнителя");
                      } else {
                        alert("Заказ отменён, деньги возвращены");
                      }
                    }}
                  >
                    <i className="bi bi-x-circle me-1" />
                    Отменить заказ
                  </button>
                )}

                {canManagePending && (
                  <>
                    <button
                      className="btn btn-outline-success"
                      onClick={async () => {
                        if (!confirm("Вы уверены? Заказ нельзя будет отменить без согласия заказчика, убедитесь что прочитали ТЗ")) {
                          return;
                        }
                        const response = await sendAcceptOrderRequest(order.order.id);
                        if (response.status !== 200) {
                          navigate(`/error?code=${response.status}`);
                          return;
                        }
                        alert("Заказ принят, начинайте выполнение заказа");
                      }}
                    >
                      <i className="bi bi-check-circle me-1" />
                      Начать выполнение
                    </button>

                    <button
                      className="btn btn-outline-danger"
                      onClick={async () => {
                        if (!confirm("Вы уверены в отмене заказа?")) {
                          return;
                        }
                        const response = await sendCancelOrderRequest(order.order.id);
                        if (response.status !== 200) {
                          navigate(`/error?code=${response.status}`);
                          return;
                        }
                        alert("Заказ отменён");
                      }}
                    >
                      <i className="bi bi-x-circle me-1" />
                      Отклонить выполнение
                    </button>
                  </>
                )}

              </div>

              {waitingForRejectShow && (
                <div className="mt-3 text-decoration-underline">
                  Направлен запрос на отмену заказа, отмените заказ
                </div>
              )}
            </div>

            <div className="order-card_box">
              <Link
                className="btn btn-outline-primary w-100"
                to={{
                  pathname: "/order/reports",
                  search: `?orderId=${order?.order?.id}`,
                }}
              >
                Открыть отчёты
              </Link>

              <Link
                className="btn btn-outline-secondary w-100 mt-4"
                to={"/order/requirement/" + order?.order?.id}
              >
                Открыть техническое задание
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
                {order?.order?.status === "PENDING" && <div>В ожидании</div>}
                {order?.order?.status === "CANCELED" && <div>Отменен</div>}
                {order?.order?.status === "SUBMITTED" && <div>Отправлен на проверку</div>}
              </div>

              <div className="mb-2">
                <div className="text-muted small">Дата заказа</div>
                <div>{fromIsoDateToDate(order?.order?.orderDate)}</div>
              </div>

              {(order?.order?.status !== "PENDING" && order?.order?.status !== "CANCELED") && (
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
              )}

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
  );
}
