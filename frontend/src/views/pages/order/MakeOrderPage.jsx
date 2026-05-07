import { useNavigate, useParams } from "react-router-dom";
import FileUploadComponent from "../../components/FileUploadComponent";
import { useEffect } from "react";
import { useState } from "react";
import "./css/make_order_page.css";

export default function MakeOrderPage() {
  const navigate = useNavigate();
  const { serviceId } = useParams();
  const [error, setError] = useState("");

  const [description, setDesrciption] = useState("");
  const [comment, setComment] = useState("");
  const [deadlineDays, setDeadlineDays] = useState(1);

  const [files, setFiles] = useState([]);

  useEffect(() => {
    document.title = "Оформление заказа";

    if (!serviceId) {
      navigate("/error?code=404");
    }
  }, [navigate, serviceId]);

  const handleSubmit = () => {
    if (description.length < 30) {
      setError("Длина описания должна быть не менее 30 символов");
      return;
    }

    navigate(`/pay/${serviceId}`, {
      state: {
        description: description,
        comment: comment,
        deadlineDays: deadlineDays,
        files: files,
      }
    });
  };

  return (
    <div className="make-order-page_brief-container">
      <h3 className="mb-4 fw-semibold">Опишите задачу</h3>
      <div className="make-order-page_block card">
        <h5 className="mb-3">Что нужно сделать?</h5>

        <textarea
          className="form-control"
          rows="5"
          placeholder="Опишите задачу максимально подробно..."
          value={description}
          onChange={(e) => setDesrciption(e.target.value)}
        />
      </div>

      <FileUploadComponent
        files={files}
        setFiles={setFiles}
        title={"Файлы (если есть)"}
        maxFiles={5}
      />

      <div className="make-order-page_block card">
        <h5 className="mb-3">Срок выполнения (дней)</h5>

        <input
          type="number"
          className="form-control"
          value={deadlineDays}
          min="1"
          max="100"
          onChange={(e) => {
            const val = e.target.value === '' ? '' : Number(e.target.value);
            const min = 0;
            const max = 100;

            if (val !== '' && val > max) {
              setDeadlineDays(max);
            } else if (val !== '' && val < min) {
              setDeadlineDays(val);
            } else {
              setDeadlineDays(val);
            }
          }}
        />
      </div>

      <div className="make-order-page_block card">
        <h5 className="mb-3">Комментарий исполнителю (не обязательно)</h5>

        <textarea
          className="form-control"
          rows="3"
          placeholder="Дополнительная информация..."
          value={comment}
          onChange={(e) => setComment(e.target.value)}
        ></textarea>
      </div>

      {error && (
        <div className="alert alert-danger">
          {error}
        </div>
      )}

      <div className="text-end">
        <button
          className="btn btn-primary btn-lg"
          onClick={handleSubmit}
        >Продолжить</button>
      </div>
    </div>
  );
}
