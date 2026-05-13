import FeedbackTypeComponent from "./FeedbackTypeComponent";
import "./css/admin_components.css";
import { fromIsoDate } from "../../../logic/time";

export default function AdminFeedbackCard({ id, title, type, text, createdAt, handleClick }) {
  const maxSize = 50;
  const sliceText =
    text.length > maxSize ? text.slice(0, maxSize) + "..." : text;

  return (
    <div
      className="admin-panel_feedback-item"
      onClick={() => handleClick(id)}
    >
      <div className="d-flex justify-content-between align-items-start mb-2">
        <FeedbackTypeComponent type={type} />

        <small className="text-muted">{ fromIsoDate(createdAt) }</small>
      </div>

      <div className="fw-semibold mb-1">{ title }</div>

      <div className="text-muted small">
        {sliceText}
      </div>
    </div>
  );
}
