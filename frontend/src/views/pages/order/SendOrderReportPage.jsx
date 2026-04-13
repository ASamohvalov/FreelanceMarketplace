import { Link, useLocation, useNavigate } from "react-router-dom";
import NavLocation from "../../components/elements/NavLocation";
import { useState } from "react";
import ReactMarkdown from "react-markdown";
import { useEffect } from "react";
import { hasRole, isAuth } from "../../../logic/jwt";
import FileUploadComponent from "../../components/FileUploadComponent";
import { sendOrderReportRequest } from "../../../logic/requests/order/orderRequest";

export default function SendOrderReportPage() {
  const { state } = useLocation();
  const [report, setReport] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();
  const [files, setFiles] = useState([]);

  useEffect(() => {
    if (!state || !isAuth() || !hasRole("ROLE_FREELANCER")) {
      navigate(-1);
      return;
    }

    document.title = "Написать отчет";
  }, [navigate, state]);

  async function handleSubmit() {
    if (!report) {
      setError("Заполните поле описания отчёта");
    }

    const response = await sendOrderReportRequest(report, state.orderId);
    if (response.status !== 200) {
      navigate(`/error?code=${response.status}`);
    }

    navigate("/order/report/send/success");
  }

  return (
    <main style={{ height: "93vh" }}>
      <div className="container my-4">
        <NavLocation>
          <Link to="/MyOrders">Заказы </Link> / Написание отчёта
        </NavLocation>
        <h2 className="fw-bold mb-2">Отчёт по заказу</h2>
        <p className="text-muted mb-4">
          Заполните приведенную ниже информацию, чтобы клиенты могли легко найти
          и понять суть вашего сервиса.
        </p>

        <div className="card p-4 form-section rounded-4">
          <h5>
            Описание{" "}
            <a
              href="https://www.markdownlang.com/ru/cheatsheet/"
              target="_blank"
            >
              <small className="h6 text-decoration-none">
                (в формате MarkDown)
              </small>
            </a>
          </h5>

          <div className="mb-3">
            <textarea
              className="form-control"
              rows="7"
              placeholder="Опишите свою проделанную работу"
              value={report}
              onChange={(e) => {
                setReport(e.target.value)
                setError("");
              }}
            ></textarea>
          </div>

          <div className="border-top p-1 mt-1">
            <ReactMarkdown>{report}</ReactMarkdown>
          </div>

          <div className="form-text">
            <span className="text-danger">{error}</span>
          </div>
        </div>

        <FileUploadComponent
          title={"Загрузка файлов"}
          files={files}
          setFiles={setFiles}
          maxFiles={5}
        />

        <div className="card p-4 rounded-4">
          <button
            className="btn btn-primary btn-lg w-100"
            onClick={handleSubmit}
          >
            Отправить отчёт
          </button>
        </div>
      </div>
    </main>
  );
}
