import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { feedbackTypes } from "../../../logic/feedback";
import { sendFeedbackRequest } from "../../../logic/requests/feedbackRequest";

export default function SendFeedbackPage() {
  const navigate = useNavigate();

  const [feedback, setFeedback] = useState("");
  const [title, setTitle] = useState("");
  const [selectedFeedbackType, setSelectedFeedbackType] = useState(
    Object.keys(feedbackTypes).find((t) => t),
  );
  const [message, setMessage] = useState({ message: "", type: "success" });

  const handleSubmit = async () => {
    if (title.length < 1 || title.length > 50) {
      setMessage({
        message: "Текст заголовка не должен быть пустым и более 50 символов",
        type: "danger",
      });
      return;
    }

    if (feedback.length < 30) {
      setMessage({
        message: "Текст описания должен быть более 30 символов",
        type: "danger",
      });
      return;
    }


    const response = await sendFeedbackRequest(feedback, selectedFeedbackType, title);
    if (response.status !== 200) {
      navigate(`/error?code=${response.status}`);
      return;
    }

    setMessage({
      message: "Обращение отправлено, ожидайте ответа",
      type: "success",
    });
  };

  return (
    <div className="g-4 mt-4">
      <div className="brief-container">
        <h3 className="mb-4 fw-semibold">Обратная связь</h3>

        <div className="information-block">
          <div className="information-block_title">Заголовок</div>

          <input
            placeholder="С чем связяно ваше обращение"
            className="form-control mb-3"
            onChange={(e) => setTitle(e.target.value)}
            value={title}
          />
        </div>

        <div className="information-block">
          <div className="information-block_title">Описание обращения</div>

          <textarea
            value={feedback}
            onChange={(e) => setFeedback(e.target.value)}
            className="form-control mb-3"
            rows="4"
            placeholder="Опишите причину своего обращения..."
          ></textarea>
        </div>

        <div className="information-block">
          <div className="information-block_title">Тип обращения</div>

          <select
            className="form-select"
            value={selectedFeedbackType}
            onChange={(e) => setSelectedFeedbackType(e.target.value)}
          >
            {Object.keys(feedbackTypes).map((key) => (
              <option key={key} value={key}>
                {feedbackTypes[key]}
              </option>
            ))}
          </select>
        </div>

        {message.message && (
          <div className={`mt-3 alert alert-${message.type}`}>
            {message.message}
          </div>
        )}

        <div className="text-end">
          <button className="btn btn-primary" onClick={handleSubmit}>
            Отправить
          </button>
        </div>
      </div>
    </div>
  );
}
