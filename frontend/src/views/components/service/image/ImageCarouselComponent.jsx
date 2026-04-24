export default function ImageCarouselComponent({ imageUrls }) {
  return (
    <div
      id="carouselExampleIndicators"
      className="carousel slide collage-main rounded-4"
      data-bs-ride="carousel"
      data-bs-interval="5000"
    >
      {imageUrls && (
        <>
          <div className="carousel-indicators">
            {imageUrls?.map((_, idx) => (
              <button
                key={idx}
                type="button"
                data-bs-target="#carouselExampleIndicators"
                data-bs-slide-to={idx}
                className={idx === 0 ? "active" : ""}
                aria-current={idx === 0 ? "true" : undefined}
                aria-label={`Slide ${idx + 1}`}
              />
            ))}
          </div>

          <div className="carousel-inner">
            {imageUrls?.map((image, idx) => (
              <div
                key={idx}
                className={`carousel-item ${idx === 0 ? "active" : ""}`}
              >
                <img
                  src={image}
                  className="d-block w-100 rounded-4 border-1"
                  style={{ height: "400px", objectFit: "cover" }}
                  alt={`slide-${idx}`}
                />
              </div>
            ))}
          </div>

          <button
            className="carousel-control-prev"
            type="button"
            data-bs-target="#carouselExampleIndicators"
            data-bs-slide="prev"
          >
            <span className="carousel-control-prev-icon" />
          </button>

          <button
            className="carousel-control-next"
            type="button"
            data-bs-target="#carouselExampleIndicators"
            data-bs-slide="next"
          >
            <span className="carousel-control-next-icon" />
          </button>
        </>
      )}
    </div>
  );
}
