import { useEffect, useState } from "react";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";
import {
  getPersonalReviewByServiceRequest,
  sendEditReviewRequest,
  sendReviewRequest,
} from "../../../logic/requests/review/reviewRequest";
import "./css/send_review_page.css";

export default function SendReviewPage() {
  // { orderId, serviceId, serviceTitle, freelancer }
  const { state } = useLocation();
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  const [rating, setRating] = useState(5);
  const [review, setReview] = useState("");
  const [reviewId, setReviewId] = useState(null);

  const [message, setMessage] = useState(null);

  const [editMode, setEditMode] = useState(false);

  useEffect(() => {
    if (!state) {
      navigate("/error");
      return;
    }

    if (searchParams.get("edit")) {
      (async () => {
        const response = await getPersonalReviewByServiceRequest(state.serviceId);
        if (response.status !== 200) {
          navigate(`/error?code=${response.status}`);
          return;
        }

        if (response.data.exists) {
          setRating(response.data.rating);
          setReview(response.data.review);
          setReviewId(response.data.id);
          setEditMode(true);
        }
      })();
    }
  }, [navigate, state, searchParams, editMode]);

  return (
      <div className="container mt-5 mb-5">
        <h3 className="mb-4 fw-semibold">
          {editMode ? "Редактирование отзыва" : "Оставить отзыв"}
        </h3>

        <div
          className="send-review-page_card mx-auto"
          style={{ maxWidth: "800px" }}
        >
          <div className="order-preview mb-4">
            <div className="fw-semibold">{state.serviceTitle}</div>
            <div className="text-muted small">
              Исполнитель:{" "}
              {state.freelancer.firstName + " " + state.freelancer.lastName}
            </div>
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
                if (editMode) {
                  const response = await sendEditReviewRequest(
                    reviewId,
                    rating,
                    review,
                  );
                  if (response.status !== 200) {
                    navigate(`/error?code=${response.status}`);
                    return;
                  }
                  setMessage("Отзыв отредактирован");
                } else {
                  const response = await sendReviewRequest(
                    state.orderId,
                    rating,
                    review,
                  );
                  if (response.status !== 200) {
                    navigate(`/error?code=${response.status}`);
                    return;
                  }
                  setMessage("Отзыв отправлен");
                }
              }}
            >
              Отправить отзыв
            </button>

            <button
              className="btn btn-outline-secondary"
              onClick={() => navigate(-1)}
            >
              Назад
            </button>
          </div>

          {message && (
            <div className="alert alert-success mt-4" role="alert">
              {message}
            </div>
          )}
        </div>
      </div>
  );
}
