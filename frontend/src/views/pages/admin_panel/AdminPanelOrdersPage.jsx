import { useState } from "react";
import { NavLink } from "react-router-dom";
import "./css/admin_pages.css";
import { useEffect } from "react";
import { completeOrderByModeratorRequest, getAllOrdersRequest, rejectOrderByModeratorRequest } from "../../../logic/requests/order/orderRequest";
import { fromIsoDate } from "../../../logic/time";
import PaginateComponent from "../../components/pagination/PaginateComponent";

export default function AdminPanelOrdersPage() {
  const [message, setMessage] = useState({});

  const [orders, setOrders] = useState({});

  useEffect(() => {
    (async () => {
      const response = await getAllOrdersRequest(0, 5);
      if (response.status !== 200) {
        setMessage({ message: `Ошибка ${response.status}, попробуйте позже`, type: "danger" });
        return;
      }
      setOrders(response.data);
    })();
  }, []);

  const canMakeVerdict = (status) =>
    status !== "CANCELLED" && status !== "REJECTED" && status !== "COMPLETED";

  const rejectOrder = async (id) => {
    if (window.confirm("Вы уверены что хотите завершить заказ в пользу заказчика?")) {
      const response = await rejectOrderByModeratorRequest(id);
      if (response.status !== 200) {
        setMessage({ message: `Ошибка ${response.status}, попробуйте позже`, type: "danger" });
        return;
      }
      setMessage({ message: "Заказ завершен, деньги возвращены заказчику", type: "success" });
    }
  };

  const completeOrder = async (id) => {
    if (window.confirm("Вы уверены что хотите завершить заказ в пользу исполнителя?")) {
      const response = await completeOrderByModeratorRequest(id);
      if (response.status !== 200) {
        setMessage({ message: `Ошибка ${response.status}, попробуйте позже`, type: "danger" });
        return;
      }
      setMessage({ message: "Заказ завершен, деньги отправлены исполнителю", type: "success" });
    }
  };

  return (
    <main className="col-10 p-4 admin_main-flex">
      {message && (
        <div className={`alert alert-${message.type}`}>
          {message.message}
        </div>
      )}

      <div className="admin-card mb-4">
        <h5 className="mb-3">Заказы</h5>

        <table className="table text-center">
          <thead>
            <tr>
              <th>Исполнитель</th>
              <th>Заказчик</th>
              <th>Статус</th>
              <th>Услуга</th>
              <th>Дата оформления</th>
              <th>Вердикт</th>
            </tr>
          </thead>

          <tbody>
            {orders?.content?.map(order => (
              <tr key={order.id}>
                <td>
                  <NavLink to={`/profile/${order.freelancer.id}`}>
                    {order.freelancer.firstName + " " + order.freelancer.lastName}
                  </NavLink>
                </td>
                <td>
                  <NavLink to={`/profile/${order.customer.id}`}>
                    {order.customer.firstName + " " + order.customer.lastName}
                  </NavLink>
                </td>
                <td>{order.status}</td>
                <td>
                  <NavLink to={`/service/${order.serviceId}`}>
                    {order.serviceTitle}
                  </NavLink>
                </td>
                <td>{fromIsoDate(order.orderDate)}</td>
                <td>
                  {canMakeVerdict(order.status) ? (
                    <div>
                      <button
                        onClick={() => completeOrder(order.id)}
                        className="btn btn-outline-secondary mx-1 w-100"
                      >Завершить в пользу исполнителя</button>
                      <button
                        onClick={() => rejectOrder(order.id)}
                        className="btn btn-outline-primary mx-1 mt-1 w-100"
                      >Завершить в пользу заказчика</button>
                    </div>
                  ) : order.status === "COMPLETED"
                    ? "Заказ завершен в пользу исполнителя"
                    : "Заказ завершен в пользу заказчика"}
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        <PaginateComponent
          data={orders}
          setData={setOrders}
          requestFunction={getAllOrdersRequest}
          onError={(code) => setMessage({ message: `Ошибка ${code}, попробуйте позже`, type: "danger" })}
        />
      </div>
    </main>
  );
}
