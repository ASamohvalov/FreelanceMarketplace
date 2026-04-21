import "./css/send_review_page.css";
import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { sendReviewRequest } from "../../../logic/requests/review/reviewRequest";

export default function SendReviewPage() {
  // { orderId, serviceTitle, freelancer }
  const { state } = useLocation();
  const navigate = useNavigate();

  const [rating, setRating] = useState(5);
  const [review, setReview] = useState("");

  const [message, setMessage] = useState(null);

  useEffect(() => {
    if (!state) {
      navigate("/error");
    }
  }, [navigate, state])

  return (
    <main style={{ minHeight: "90vh" }}>
      <div className="container mt-5 mb-5">
        <h3 className="mb-4 fw-semibold">Оставить отзыв</h3>

        <div
          className="send-review-page_card mx-auto"
          style={{ maxWidth: "800px" }}
        >
          <div className="order-preview mb-4">
            <div className="fw-semibold">{state.serviceTitle}</div>
            <div className="text-muted small">Исполнитель: {state.freelancer.firstName + " " + state.freelancer.lastName}</div>
          </div>

          <div className="mb-4">
            <label className="form-label fw-semibold">Оценка</label>
            <div className="send-review-page_stars">
              {[1, 2, 3, 4, 5].map((val) => (
                <i
                  key={val}
                  className={`bi bi-star-fill ${rating >= val ? "active" : ""}`}
                  onClick={() => setRating(val)}
                ></i>
              ))}
            </div>
          </div>

          <div className="mb-4">
            <label className="form-label fw-semibold">Отзыв</label>
            <textarea
              className="form-control"
              rows="5"
              placeholder="Опишите ваш опыт взаимодействия с исполнителем"
              value={review}
              onChange={(e) => setReview(e.target.value)}
            ></textarea>
          </div>

          <div className="d-flex gap-3">
            <button
              className="btn btn-primary"
              onClick={async () => {
                const response = await sendReviewRequest(state.orderId, rating, review);
                if (response.status !== 200) {
                  navigate(`/error?code=${response.status}`);
                  return;
                }
                setMessage("отзыв отправлен");
              }}
            >Отправить отзыв</button>

            <button
              className="btn btn-outline-secondary"
              onClick={() => navigate(-1)}
            >Назад</button>
          </div>

          {message && (
            <div className="alert alert-success mt-4" role="alert">
              {message}
            </div>
          )}
        </div>
      </div>
    </main>
  );
}
