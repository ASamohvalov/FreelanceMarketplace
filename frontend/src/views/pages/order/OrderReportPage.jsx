import { useCallback, useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
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
import { StatusSelect } from "../../components/elements/StatusSelect";

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
  const [filteredOrderReports, setFilteredOrderReports] = useState({
    status: "all",
    orderId: "all",
  });

  const [selectedOrderReport, setSelectedOrderReport] = useState({});

  const isFreelancer = hasRole("ROLE_FREELANCER");
  const [searchParams] = useSearchParams();
  const orderId = searchParams.get("orderId");

  useEffect(() => {
    if (orderId) {
      setFilteredOrderReports((prev) => ({ ...prev, orderId: orderId }));
    }
  }, [orderId]);

  useEffect(() => {
    if (!isAuth()) {
      navigate("/error");
      return;
    }

    document.title = "Отчёты";

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
  const handleStatusesSelect = (e) => {
    const status = e.target.value;
    if (status === "all") {
      setFilteredOrderReports((prev) => ({ ...prev, status: "all" }));
      return;
    }

    setFilteredOrderReports((prev) => ({ ...prev, status: status }));
  };

  const handleOrderSelect = (e) => {
    const orderId = e.target.value;

    if (orderId === "all") {
      setFilteredOrderReports((prev) => ({ ...prev, orderId: "all" }));
      return;
    } else {
      setFilteredOrderReports((prev) => ({ ...prev, orderId: orderId }));
      return;
    }
  };

  return (
    <>
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
        fileIds={selectedOrderReport?.files}
      />

      <div className="container mt-5 mb-5">
        <h3 className="mb-4 fw-semibold">{orderId ? "Отчёты по заказу" : "Отчёты по заказам"}</h3>

        {(isFreelancer || !orderId) && (
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
            {!orderId && (
              <>
                <select
                  className="form-select"
                  style={{ width: "35%" }}
                  onChange={handleOrderSelect}
                >
                  <option defaultValue={true} value={"all"}>
                    Все заказы
                  </option>
                  {showReceivedOrderReports
                    ? customerOrders.map((order, idx) => (
                      <option value={order.order.id} key={idx}>
                        {order.service.freelancer.firstName +
                          " " +
                          order.service.freelancer.lastName +
                          " • " +
                          order.service.title}
                      </option>
                    ))
                    : freelancerOrders
                      .filter((item) => {
                        if (orderId) {
                          return item.order.id === orderId;
                        } else return true;
                      })
                      .map((order, idx) => (
                        <option value={order.order.id} key={idx}>
                          {order.customer.firstName +
                            " " +
                            order.customer.lastName +
                            " | " +
                            order.service.title}
                        </option>
                      ))}
                </select>
                <StatusSelect handleStatusesSelect={handleStatusesSelect} />
              </>
            )}
          </div>
        )}

        <div className="row g-4">
          {showReceivedOrderReports
            ? receivedOrderReports
                .filter((report) =>
                  filteredOrderReports.orderId === "all"
                    ? true
                    : report.orderId === filteredOrderReports.orderId,
                )
                .filter((report) =>
                  filteredOrderReports.status === "all"
                    ? true
                    : report.status === filteredOrderReports.status,
                )
                .map((report, idx) => (
                  <div className="col-md-6 col-xl-4" key={idx}>
                    <OrderReportCardComponent
                      title={report.title}
                      user={report.freelancer}
                      sendDate={fromIsoDate(report.createdAt)}
                      onOpen={() => {
                        orderReportOpen(report);
                      }}
                      status={report.status}
                      fileCount={report.files?.length}
                      comment={report.customerComment}
                    />
                  </div>
                ))
            : sentOrderReports
                .filter((report) =>
                  filteredOrderReports.orderId === "all"
                    ? true
                    : report.orderId === filteredOrderReports.orderId,
                )
                .filter((report) =>
                  filteredOrderReports.status === "all"
                    ? true
                    : report.status === filteredOrderReports.status,
                )
                .map((report, idx) => (
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
                      fileCount={report.files?.length}
                      comment={report.customerComment}
                    />
                  </div>
                ))}
        </div>
      </div>
    </>
  );
}
