import { feedbackTypes } from "../../../logic/feedback";
import "./css/admin_components.css";

export default function FeedbackTypeComponent({ type }) {

  return (
    <>
      {type === "CONFLICT" && (
        <span className="admin-panel_feedback-badge admin-panel_feedback-support">
          {feedbackTypes.CONFLICT}
        </span>
      )}
      {type === "BUG" && (
        <span className="admin-panel_feedback-badge admin-panel_feedback-bug">
          {feedbackTypes.BUG}
        </span>
      )}
      {type === "IDEAS_FOR_IMPROVEMENT" && (
        <span className="admin-panel_feedback-badge admin-panel_feedback-review">
          {feedbackTypes.IDEAS_FOR_IMPROVEMENT}
        </span>
      )}
      {type === "ANOTHER_PROBLEM" && (
        <span className="admin-panel_feedback-badge admin-panel_feedback-support">
          {feedbackTypes.ANOTHER_PROBLEM}
        </span>
      )}
    </>
  );
}
