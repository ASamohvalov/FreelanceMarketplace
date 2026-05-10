import { Link, useNavigate, useParams } from "react-router-dom";
import "./css/personal_account_page.css";
import { useEffect } from "react";
import { useState } from "react";
import { getUserByIdRequest } from "../../../logic/requests/user/userRequest";
import { fromIsoDateToYear } from "../../../logic/time";
import { getAllPersonalServices } from "../../../logic/requests/service/serviceRequest";
import { getFreelancerReviewsRequest } from "../../../logic/requests/user/freelancerRequest";
import ServiceCardComponent from "../../components/service/ServiceCardComponent";
import ReviewCardComponent from "../../components/reviews/ReviewCardComponent";
import { getUserData, isAuth } from "../../../logic/jwt";
import Avatar from "../../components/elements/Avatar";
import { reviewToRu } from "../../../logic/lang";
import { checkConversationRequest } from "../../../logic/requests/message/messageRequest";

export default function ProfilePage() {
  const navigate = useNavigate();
  const { userId } = useParams();

  const [user, setUser] = useState({});
  const [personalServices, setPersonalServices] = useState([]);
  const [reviews, setReviews] = useState([]);
  const [checkConversation, setCheckConversation] = useState({});

  const isFreelancer = user?.roles?.find(r => r.name === "ROLE_FREELANCER");

  useEffect(() => {
    (async () => {
      const response = await getUserByIdRequest(userId);
      if (response.status !== 200) {
        navigate(`/error?code=${response.status}`);
        return;
      }

      setUser(response.data);

      if (response.data.roles.find(r => r.name === "ROLE_FREELANCER")) {
        const personalServicesResponse = await getAllPersonalServices(response.data.freelancer.freelancerId);
        if (personalServicesResponse.status !== 200) {
          navigate(`/error?code=${personalServicesResponse.status}`);
          return;
        }
        setPersonalServices(personalServicesResponse.data);

        const reviewResponse = await getFreelancerReviewsRequest(response.data.freelancer.freelancerId);
        if (reviewResponse.status !== 200) {
          navigate(`/error?code=${reviewResponse.status}`);
          return;
        }
        setReviews(reviewResponse.data);
      }

      if (isAuth()) {
        const checkConversationResponse = await checkConversationRequest(userId);
        if (checkConversationResponse.status !== 200) {
          navigate(`/error?code=${checkConversationResponse.status}`);
          return;
        }
        setCheckConversation(checkConversationResponse.data);
      }
    })();
  }, [navigate, userId])

  return (
    <div className="container mt-5 mb-5">
      <div className="profile-page_header mb-4">
        <div className="row align-items-center">
          <div className="col-lg-2 text-center">
            <Avatar className="profile-page_avatar mx-auto" userId={user?.id} />
          </div>

          <div className="col-lg-7">
            <div className="d-flex align-items-center gap-2 mb-2">
              <h3 className="mb-0">{ user?.firstName + " " + user?.lastName }</h3>

              {isFreelancer && (
                <div className="profile-page_role-badge">
                  <i className="bi bi-patch-check-fill"></i>
                  Freelancer
                </div>
              )}
            </div>

            {isFreelancer && (
              <div className="text-muted mb-3">
                {user?.freelancer?.jobTitle}
              </div>
            )}
          </div>

          <div className="col-lg-3 text-lg-end mt-4 mt-lg-0">
            {userId === getUserData()?.id ? (
              <Link
                className="btn btn-outline-secondary w-100"
                to="/personal-account"
              >
                Открыть личный кабинет
              </Link>
            ): (
              <>
                {checkConversation?.exists && (
                  <button className="btn btn-primary w-100 mb-2" onClick={() => navigate("/messages/" + checkConversation.id)}>
                    <i className="bi bi-chat-dots me-1"></i>
                    Написать
                  </button>
                )}
              </>
            )}
          </div>
        </div>
      </div>

      {isFreelancer && (
        <div className="row g-4">
          <div className="col-lg-8">
            <div className="profile-page_info-card mb-4">
              <h5 className="mb-3">О себе</h5>

              <p className="mb-0">
                {user?.freelancer?.aboutYourself}
              </p>
            </div>

            <div className="freelancer-only">
              <div className="d-flex justify-content-between align-items-center mb-3">
                <h5 className="mb-0">Отзывы</h5>

                <div className="text-muted">⭐ {reviewToRu(reviews.length)}</div>
              </div>

              <div className="row g-3">
                {reviews.map((review, idx) => (
                  <div className="col-md-6" key={idx}>
                    <ReviewCardComponent
                      author={review.author}
                      service={review.service}
                      rating={review.rating}
                      review={review.review}
                    />
                  </div>
                ))}
              </div>
            </div>
          </div>

          <div className="col-lg-4">
            <div className="profile-page_info-card mb-4 freelancer-only">
              <h5 className="mb-3">Статистика</h5>

              <div className="d-flex justify-content-between mb-3">
                <span className="text-muted">Выполнено заказов</span>
                <strong>{ user?.freelancer?.statistic?.orderAmount }</strong>
              </div>

              <div className="d-flex justify-content-between mb-3">
                <span className="text-muted">Средний рейтинг</span>
                <strong>{ user?.freelancer?.statistic?.rating }</strong>
              </div>

              <div className="d-flex justify-content-between">
                <span className="text-muted">На платформе с</span>
                <strong>{ fromIsoDateToYear(user?.freelancer?.statistic?.accountCreatedAt) }</strong>
              </div>
            </div>

            {personalServices.length !== 0 && (
              <div className="profile-page_info-card freelancer-only">
                <h5 className="mb-3">Услуги</h5>

                {personalServices?.map((service, idx) => (
                  <ServiceCardComponent
                    key={idx}
                    id={service.id}
                    hidden={service.hide}
                    title={service.title}
                    price={service.price}
                    freelancerName={
                      service?.freelancer?.firstName +
                      " " +
                      service?.freelancer?.lastName
                    }
                    image={service.imageURL || null}
                    from={location.pathname}
                  />
                ))}
             </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
}
