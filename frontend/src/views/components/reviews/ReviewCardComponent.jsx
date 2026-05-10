import Avatar from "../elements/Avatar";
import "./css/review_card_component.css";
import ReviewStarsComponent from "./ReviewStarsComponent";

export default function ReviewCardComponent({
  author,
  service,
  rating,
  review,
}) {
  return (
    <div className="review-card-component">
      <div className="d-flex align-items-start gap-3">
        <Avatar className="review-card-component_user" userId={author.id} />

        <div className="flex-grow-1">
          <div className="d-flex justify-content-between align-items-center mb-1">
            <strong>{ author?.firstName + " " + author?.lastName }</strong>

            <ReviewStarsComponent rating={rating} />
          </div>

          <div className="text-muted small mb-2">
            {service?.title}
          </div>

          <p className="mb-0 small">
            {review}
          </p>
        </div>
      </div>
    </div>
  );
}
