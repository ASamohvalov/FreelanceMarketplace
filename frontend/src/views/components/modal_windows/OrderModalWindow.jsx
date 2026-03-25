import { useNavigate } from "react-router-dom";
import "./css/modal_window.css";
import { useState } from "react";
import ModalWindowComponent from "./ModalWindowComponent";

export default function OrderModalWindow({ id, isVisible, onClose }) {
  const navigate = useNavigate();

  const [deadlineDate, setDeadlineDate] = useState("");
  const [error, setError] = useState("");

  return (
    <ModalWindowComponent
      isVisible={isVisible}
      onClose={onClose}
      title={"Оформление заказа"}
      description={"После оформления заказа у вас откроется чат с исполнителем"}
      buttonText={"К оплате"}
      error={error}
      onSubmit={async (e) => {
        e.preventDefault();
        if (deadlineDate === "") {
          setError("заполните поле")
          return;
        }
        const deadline = new Date(deadlineDate);
        navigate(`/pay/${id}`, {
          state: {
            deadlineDate: deadline
          }
        })
      }}
    >
      <label htmlFor="deedline-date">Дедлайн для выполнения задачи</label>
      {/* TODO - date only in a future */}
      <input
        className={`form-control mb-1 ${error !== "" && "is-invalid"}`}
        id="deedline-date"
        type="date"
        value={deadlineDate}
        onChange={(e) => {
          setDeadlineDate(e.target.value)
          setError("");
        }}
      />
    </ModalWindowComponent>
  );
}
