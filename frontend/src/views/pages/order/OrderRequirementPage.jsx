import { Link, useNavigate, useParams } from "react-router-dom";
import "./css/order_requirement_page.css";
import { useEffect } from "react";
import { useState } from "react";
import { getOrderRequirementRequest } from "../../../logic/requests/order/orderRequest";
import FileItemComponent from "../../components/order/FileItemComponent";
import { getRequirementFileUrl } from "../../../logic/image";

export default function OrderRequirementPage() {
  const { orderId } = useParams();
  const navigate = useNavigate();

  const [orderRequirement, setOrderRequirement] = useState({});

  useEffect(() => {
    (async () => {
      const response = await getOrderRequirementRequest(orderId);
      if (response.status !== 200) {
        navigate(`/error?code=${response.status}`);
        return;
      }

      setOrderRequirement(response.data);
    })();
  }, [navigate, orderId]);

  return (
    <main className="container">
      <div className="row g-4 mt-4">
        <div className="order-requirement-page_brief-container">
          <div className="d-flex justify-content-between">
            <h4 className="mb-4">Техническое задание</h4>

            <Link className="btn btn-primary mb-3" to={`/order/info/${orderRequirement?.orderId}`}>Назад к заказу</Link>
          </div>

          <div className="order-requirement-page_block">
            <div className="title">Описание задачи</div>

            <p>{orderRequirement?.description}</p>
          </div>

          <div className="order-requirement-page_block">
            <div className="title">Файлы</div>

            {orderRequirement?.orderRequirementFileIds?.map((fileId, idx) => (
              <FileItemComponent
                key={idx}
                fileId={fileId}
                idx={idx}
                getReportFileUrl={getRequirementFileUrl}
              />
            ))}
          </div>

          <div className="order-requirement-page_block">
            <div className="order-requirement-page_title">Комментарий</div>

            <p>{orderRequirement?.comment || "Комментарий отсутствует"}</p>
          </div>
        </div>
      </div>
    </main>
  );
}
