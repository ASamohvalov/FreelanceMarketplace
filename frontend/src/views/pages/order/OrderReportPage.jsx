import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { hasRole, isAuth } from "../../../logic/jwt";
import {
  getReceivedOrderReportsRequest,
  getSentOrderReportsRequest,
} from "../../../logic/requests/order/orderReportRequest";
import {
  getOrderCustomerRequest,
  getOrderFreelancerRequest,
} from "../../../logic/requests/order/orderRequest";
import { fromIsoDate } from "../../../logic/time";
import OrderReportModalWindow from "../../components/modal_windows/OrderReportModalWindow";
import OrderReportCardComponent from "../../components/order/OrderReportCardComponent";
import "./css/order_report_page.css";

export default function OrderReportPage() {
  const navigate = useNavigate();

  const [isOrderReportWindowVisible, setIsOrderReportWindowVisible] =
    useState(false);

  const [freelancerOrders, setFreelancerOrders] = useState([]);
  const [customerOrders, setCustomerOrders] = useState([]);

  const [showReceivedOrderReports, setShowReceivedOrderReports] =
    useState(true);

  const [receivedOrderReports, setReceivedOrderReports] = useState([]);
  const [sentOrderReports, setSentOrderReports] = useState([]);

  const [selectedOrderReport, setSelectedOrderReport] = useState({});

  const isFreelancer = hasRole("ROLE_FREELANCER");

  useEffect(() => {
    if (!isAuth()) {
      navigate("/error");
      return;
    }

    (async () => {
      // get all customer orders
      const customerResponse = await getOrderCustomerRequest();
      if (customerResponse.status !== 200) {
        navigate(`/error?code=${customerResponse.status}`);
        return;
      }
      setCustomerOrders(customerResponse.data);

      // get all customer reports
      const customerReportsResponse = await getReceivedOrderReportsRequest();
      if (customerReportsResponse.status !== 200) {
        navigate(`/error?code=${customerResponse.status}`);
        return;
      }
      setReceivedOrderReports(customerReportsResponse.data);

      if (isFreelancer) {
        // get all executor orders
        const freelancerResponse = await getOrderFreelancerRequest();
        if (freelancerResponse.status !== 200) {
          navigate(`/error?code=${customerResponse.status}`);
          return;
        }
        setFreelancerOrders(freelancerResponse.data);

        // get all executor reports
        const sentReportsResponse = await getSentOrderReportsRequest();
        if (sentReportsResponse.status !== 200) {
          navigate(`/error?code=${sentReportsResponse.status}`);
          return;
        }
        setSentOrderReports(sentReportsResponse.data);
      }
    })();
  }, [navigate, isFreelancer]);

  function orderReportOpen(data) {
    setSelectedOrderReport(data);
    setIsOrderReportWindowVisible(true);
  }

  return (
    <main style={{ minHeight: "90vh" }}>
      <OrderReportModalWindow
        isReceived={showReceivedOrderReports}
        isVisible={isOrderReportWindowVisible}
        onClose={() => setIsOrderReportWindowVisible(false)}
        title={selectedOrderReport?.title}
        user={selectedOrderReport?.freelancer}
        sendDate={fromIsoDate(selectedOrderReport?.createdAt)}
        status={selectedOrderReport?.status}
        report={selectedOrderReport?.report}
        id={selectedOrderReport?.id}
      />

      <div className="container mt-5 mb-5">
        <h3 className="mb-4 fw-semibold">Отчёты по заказам</h3>

        <div className="report-nav mb-4">
          {isFreelancer && (
            <>
              <button
                className={showReceivedOrderReports ? "active" : ""}
                onClick={() => {
                  setShowReceivedOrderReports(true);
                }}
              >
                Полученные
              </button>
              <button
                className={!showReceivedOrderReports ? "active" : ""}
                onClick={() => {
                  setShowReceivedOrderReports(false);
                }}
              >
                Отправленные
              </button>
            </>
          )}

          <select className="form-select" style={{ width: "35%" }}>
            <option defaultValue={true}>Все заказы</option>
            {showReceivedOrderReports
              ? customerOrders.map((order, idx) => (
                  <option value={order.order.id} key={idx}>
                    {order.service.freelancer.firstName +
                      " " +
                      order.service.freelancer.lastName +
                      " | " +
                      order.service.title}
                  </option>
                ))
              : freelancerOrders.map((order, idx) => (
                  <option value={order.order.id} key={idx}>
                    {order.customer.firstName +
                      " " +
                      order.customer.lastName +
                      " | " +
                      order.service.title}
                  </option>
                ))}
          </select>
        </div>

        <div className="row g-4">
          {showReceivedOrderReports
            ? receivedOrderReports.map((report, idx) => (
                <div className="col-md-6 col-xl-4" key={idx}>
                  <OrderReportCardComponent
                    title={report.title}
                    user={report.freelancer}
                    sendDate={fromIsoDate(report.createdAt)}
                    onOpen={() => {
                      orderReportOpen(report);
                    }}
                    status={report.status}
                  />
                </div>
              ))
            : sentOrderReports.map((report, idx) => (
                <div className="col-md-6 col-xl-4" key={idx}>
                  <OrderReportCardComponent
                    title={report.title}
                    user={report.user}
                    sendDate={fromIsoDate(report.createdAt)}
                    isReceived={false}
                    onOpen={() => {
                      orderReportOpen(report);
                    }}
                    status={report.status}
                  />
                </div>
              ))}
        </div>
      </div>
    </main>
  );
}
