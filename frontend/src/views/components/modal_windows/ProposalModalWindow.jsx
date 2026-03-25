import { useState } from "react";
import { sendProposalRequest } from "../../../logic/requests/message/proposalRequest";
import { useNavigate } from "react-router-dom";
import "./css/modal_window.css";
import ModalWindowComponent from "./ModalWindowComponent";

export default function ProposalModalWindow({
  id,
  isVisible,
  onClose,
  onSubmit,
}) {
  const navigate = useNavigate();

  const [desctiption, setDescription] = useState("");
  const [error, setError] = useState("");

  return (
    <ModalWindowComponent
      isVisible={isVisible}
      onClose={onClose}
      error={error}
      title={"Оставление отклика на обсуждение"}
      description={
        "После подтверждения отклика фрилансером у вас откроется чат"
      }
      buttonText={"Отправить отклик"}
      onSubmit={async (e) => {
        e.preventDefault();

        if (desctiption.length < 10) {
          setError("Описание должно содержать хотя бы 10 символов");
          return;
        }

        const response = await sendProposalRequest(id, desctiption);
        if (response.status !== 200) {
          console.log("logic error");
          navigate("/error");
          return;
        }

        alert("Запрос успешно отправлен, ожидайте ответ от фрилансера");
        onSubmit();
        onClose();
      }}
    >
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
    </ModalWindowComponent>
  );
}
