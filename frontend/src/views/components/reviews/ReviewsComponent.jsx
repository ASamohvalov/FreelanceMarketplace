export default function ReviewsComponent(reviews) {

  return (
    <div class="card p-4 mb-4">
      <h4 class="mb-3">Отзывы</h4>

      {reviews.map((review) => (
        <div class="review-component_review">
          <div class="d-flex justify-content-between">
            <strong>{review.author.firstName + " " + review.author.lastName}</strong>
            <span class="text-warning">★★★★★</span>
          </div>
          <p class="mt-2">
            {review.review}
          </p>
        </div>
      ))}
    </div>
  );
}
