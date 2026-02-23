import { useState } from "react";
import { sendProposalRequest } from "../../../logic/requests/message/proposalRequest";
import { useNavigate } from "react-router-dom";
import "./css/proposal_modal_window.css";

export default function ProposalModalWindow({ id, isVisible, onClose }) {
  const navigate = useNavigate();

  const [desctiption, setDescription] = useState("");
  const [error, setError] = useState("");

  async function handleSubmit(event) {
    event.preventDefault();

    if (desctiption.length < 10) {
      setError("Описание должно содержать хотя бы 10 символов");
      return;
    }

    const response = await sendProposalRequest(id, desctiption);
    if (response.status !== 200) {
      console.log("logic error");
      navigate("/error");
    }

    alert("Запрос успешно отправлен, ожидайте ответ от фрилансера");
    onClose();
  }

  return (
    <div
      className={`proposal-modal-window p-4 rounded-4 shadow ${!isVisible && "d-none"}`}
      style={{ width: "min(500px, 50%)" }}
    >
      <form onSubmit={handleSubmit} className="mb-3 mt-3 sign-form">
        <label htmlFor="desctiption">Описание обращения</label>
        <textarea
          className={`form-control mb-1 ${error !== "" && "is-invalid"}`}
          id="desctiption"
          type="text"
          value={desctiption}
          placeholder="опишите свой вопрос по задаче"
          onChange={(e) => {
            setDescription(e.target.value);
            setError("");
          }}
        />
        <div className="p-1 mb-1 text-danger">
          <p>{error}</p>
        </div>
        <div className="p-1">
          <p>После написания оклика у вас откроется чат с фрилансером</p>
        </div>
        <div className="d-flex gap-3 justify-content-center">
          <button className="btn btn-primary sign-form_submit" type="submit">
            Отправить оклик
          </button>
          <a className="btn btn-primary sign-form_submit" onClick={onClose}>
            Закрыть
          </a>
        </div>
      </form>
    </div>
  );
}
