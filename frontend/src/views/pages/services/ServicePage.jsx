import { Link, useNavigate, useParams } from "react-router-dom";
import { useEffect } from "react";
import { useState } from "react";
import {
  getAllPersonalServices,
  getServiceByIdRequest,
} from "../../../logic/requests/service/serviceRequest";
import ProposalModalWindow from "../../components/modal_windows/ProposalModalWindow";
import NavLocation from "../../components/elements/NavLocation";
import ReactMarkdown from "react-markdown";
import "./css/service_page.css";
import ServiceCardComponent from "../../components/service/ServiceCardComponent";
import OrderModalWindow from "../../components/modal_windows/OrderModalWindow";
import { getUserData, isAuth } from "../../../logic/jwt";
import {
  getReviewsByServiceRequest,
  sendCanReviewRequest,
} from "../../../logic/requests/review/reviewRequest";
import ReviewStarsComponent from "../../components/reviews/ReviewStarsComponent";
import ImageCarouselComponent from "../../components/service/image/ImageCarouselComponent";
import { getServiceImageUrl } from "../../../logic/image";

export default function ServicePage() {
  const { id } = useParams();

  const navigate = useNavigate();

  const [serviceData, setServiceData] = useState([]);
  const [isProposalVisible, setIsProposalVisible] = useState(false);
  const [isOrderVisible, setIsOrderVisible] = useState(false);
  const [isProposalBeenSent, setIsProposalBeenSent] = useState(false);
  const [reviews, setReviews] = useState([]);

  const [personalServices, setPersonalServices] = useState([]);
  const [reviewCheckInfo, setReviewCheckInfo] = useState(false);
  const [serviceReviewRating, setServiceReviewRating] = useState(0);

  useEffect(() => {
    (async () => {
      const response = await getServiceByIdRequest(id);
      if (response.status !== 200) {
        navigate("/error");
        return;
      }
      setServiceData(response.data);
      setIsProposalBeenSent(response.data.proposalBeenSent);

      // get all personal services
      const getPersonalResponse = await getAllPersonalServices(
        response.data.freelancer.id,
      );
      if (getPersonalResponse.status !== 200) {
        navigate(`/error?code=${getPersonalResponse.status}`);
        return;
      }
      setPersonalServices(getPersonalResponse.data);

      const reviewResponse = await getReviewsByServiceRequest(id);
      if (reviewResponse.status !== 200) {
        navigate(`/error?code=${reviewResponse.status}`);
        return;
      }
      setReviews(reviewResponse.data);
      const reviews = reviewResponse.data;
      const average = (reviews.reduce((acc, curr) => acc + curr.rating, 0) / reviews.length) || 0;

      setServiceReviewRating(average);


      if (isAuth()) {
        const reviewCheckResponse = await sendCanReviewRequest(id);
        if (reviewCheckResponse.status !== 200) {
          navigate(`/error?code=${reviewCheckResponse.status}`);
          return;
        }
        setReviewCheckInfo(reviewCheckResponse.data);
      }
    })();
  }, [navigate, id]);

  return (
    <main style={{ minHeight: "80vh" }}>
      <ProposalModalWindow
        id={id}
        isVisible={isProposalVisible}
        onClose={() => setIsProposalVisible(false)}
        onSubmit={() => setIsProposalBeenSent(true)}
      />

      <OrderModalWindow
        id={id}
        isVisible={isOrderVisible}
        onClose={() => setIsOrderVisible(false)}
      />

      <div className="container my-5">
        <NavLocation>
          <Link to="/services" className="text-decoration-none">
            Услуги{" "}
          </Link>
          / {serviceData.category} / {serviceData.subcategory}
        </NavLocation>

        <h2 className="fw-bold mb-2">{serviceData.title}</h2>

        <div className="d-flex align-items-center gap-3 mb-4">
          <ReviewStarsComponent rating={serviceReviewRating} />
          <span>{serviceReviewRating} ({reviews.length} отзывов)</span>
          <span className="badge bg-light text-dark">
            {serviceData.category}
          </span>
          <span className="badge bg-light text-dark">
            {serviceData.subcategory}
          </span>
        </div>

        <div className="row">
          <div className="col-lg-8">
            <div className="mb-3">
              <ImageCarouselComponent
                imageUrls={serviceData?.imageIds?.map((i) =>
                  getServiceImageUrl(i),
                )}
              />
            </div>

            <div className="card p-4 mb-4 rounded-4">
              <ReactMarkdown>{serviceData.description}</ReactMarkdown>
            </div>

            {(reviews.length > 0 || reviewCheckInfo.canReview) && (
              <div className="card p-4 mb-4 rounded-4">
                <h4 className="mb-3">Отзывы</h4>

                {reviews.map((review, idx) => (
                  <div className="review" key={idx}>
                    <div className="d-flex justify-content-between">
                      <strong>
                        {review.author.firstName + " " + review.author.lastName}
                      </strong>
                      <ReviewStarsComponent rating={review.rating} />
                    </div>
                    <p className="mt-2">{review.review}</p>
                  </div>
                ))}

                {reviewCheckInfo.canReview && (
                  <div className="mt-2">
                    <button
                      onClick={() => {
                        navigate(
                          reviewCheckInfo.action === "EDIT"
                            ? "/review/send?edit=1"
                            : "/review/send",
                          {
                            state: {
                              serviceId: serviceData.id,
                              orderId: reviewCheckInfo.orderId,
                              serviceTitle: serviceData.title,
                              freelancer: serviceData.freelancer,
                            },
                          },
                        );
                      }}
                      className="w-100 btn btn-primary"
                    >
                      {reviewCheckInfo.action === "CREATE" && "Оставить отзыв"}
                      {reviewCheckInfo.action === "EDIT" &&
                        "Редактировать отзыв"}
                    </button>
                  </div>
                )}
              </div>
            )}
          </div>

          <div className="col-lg-4">
            <div className="card p-4 service-sidebar rounded-4">
              <div className="price mb-3 d-flex justify-content-between">
                <span>{serviceData.price} ₽</span>

                {serviceData?.freelancer?.userId === getUserData().id && (
                  <Link
                    className="fs-5"
                    to={`/update-service?id=${serviceData.id}`}
                  >
                    <i className="bi bi-pencil-fill border p-2 rounded" />
                  </Link>
                )}
              </div>

              <button
                className="btn btn-primary w-100 mb-3"
                onClick={() => setIsOrderVisible(true)}
                disabled={!getUserData()?.roles || serviceData?.freelancer?.userId === getUserData().id}
              >
                Оформить заказ
              </button>

              {isProposalBeenSent ? (
                <button className="btn btn-success w-100 mb-3" disabled>
                  Отклик успешно отправлен
                </button>
              ) : (
                <button
                  className="btn btn-primary w-100 mb-3"
                  disabled={!getUserData()?.roles || serviceData?.freelancer?.userId === getUserData().id}
                  onClick={() => setIsProposalVisible(true)}
                >
                  Оставить отклик на обсуждение
                </button>
              )}
              <hr />

              <div className="d-flex align-items-center gap-3">
                <div className="avatar">CJ</div>
                <div>
                  <strong>
                    {serviceData.freelancer?.firstName +
                      " " +
                      serviceData.freelancer?.lastName}
                  </strong>
                  <div className="text-muted small">Веб-разработчик</div>
                </div>
              </div>

              <ul className="list-unstyled mt-3 small">
                <li>✔ Время ответа: 1 час</li>
                <li>✔ Доставка: 3 дня</li>
                <li>✔ Редактирование: 2</li>
              </ul>
            </div>
          </div>
        </div>

        {personalServices.length > 1 && (
          <>
            <h4 className="mt-5 mb-4">Еще услуги у фрилансера</h4>

            <div className="row g-4">
              {personalServices.map((s, idx) => {
                if (s.id !== serviceData.id) {
                  return (
                    <div className="col-md-4 col-lg-3" key={idx}>
                      <ServiceCardComponent
                        id={s.id}
                        title={s.title}
                        price={s.price}
                        freelancerName={
                          s.freelancer.firstName + " " + s.freelancer.lastName
                        }
                        image={s.imageURL || null}
                      />
                    </div>
                  );
                }
              })}
            </div>
          </>
        )}
      </div>
    </main>
  );
}
