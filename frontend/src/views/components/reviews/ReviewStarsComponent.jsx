import "./css/review_component.css";

export default function ReviewStarsComponent({ rating }) {

  return (
    <div className="review-star-component_star">
      {[1, 2, 3, 4, 5].map((val) => (
        <i
          key={val}
          className={`bi bi-star-fill ${rating >= val ? "active" : ""}`}
        ></i>
      ))}
    </div>
  )
}
