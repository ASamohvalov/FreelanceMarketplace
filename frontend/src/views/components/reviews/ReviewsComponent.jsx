export default function ReviewsComponent() {

  return (
    <div class="card p-4 mb-4">
      <h4 class="mb-3">Reviews</h4>

      <div class="review">
        <div class="d-flex justify-content-between">
          <strong>Alex</strong>
          <span class="text-warning">★★★★★</span>
        </div>
        <p class="mt-2">
          Amazing experience. The website looks clean and works perfectly.
        </p>
      </div>

      <div class="review">
        <div class="d-flex justify-content-between">
          <strong>Maria</strong>
          <span class="text-warning">★★★★☆</span>
        </div>
        <p class="mt-2">
          Fast delivery and good communication. Would order again.
        </p>
      </div>
    </div>
  );
}
