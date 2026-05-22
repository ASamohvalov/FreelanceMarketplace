import { useContext, useEffect } from "react";
import { useState } from "react";
import { getPopularServices } from "../../logic/requests/service/serviceRequest";
import "./css/home_page.css";
import { Link, useNavigate } from "react-router-dom";
import ServiceCardComponent from "../components/service/ServiceCardComponent";
import { userContext } from "../../logic/store/userContext";
import { getAllCategories } from "../../logic/requests/service/categoryRequest";
import homePhoto from '/images/home_page_photo.jpg';

export default function HomePage() {
  const navigate = useNavigate();
  const [popularServices, setPopularServices] = useState([]);
  const [user, _] = useContext(userContext);

  const [categories, setCategories] = useState([]);

  useEffect(() => {
    (async () => {
      const response = await getPopularServices();
      if (response.status !== 200) {
        navigate(`/error?code=${response.status}`);
        return;
      }
      setPopularServices(response.data);

      const categoryResponse = await getAllCategories();
      if (categoryResponse.status !== 200) {
        navigate(`/error?code=${categoryResponse.status}`);
        return;
      }
      setCategories(categoryResponse.data);
    })();
  }, [navigate]);

  return (
    <div className="container">
      <section className="hero">
        <div className="row align-items-center">
          <div className="col-lg-6">
            <h1>Найди исполнителя для любой задачи</h1>
            <p className="mt-3">Быстро, безопасно и с гарантией результата</p>

            <div className="mt-4 d-flex gap-2 mb-2">
              <Link className="btn btn-primary" to="/services">Найти услугу</Link>
              {user?.isAuth && !user?.isFreelancer && (
                <Link className="btn btn-outline-secondary" to="/become-freelancer">
                  Стать исполнителем
                </Link>
              )}
            </div>
          </div>

          <div className="col-lg-6">
            <div className="home-page_picture" style={{ backgroundImage: `url(${homePhoto})` }} />
          </div>
        </div>
      </section>

      <section className="mb-5">
        <h4 className="mb-3">Категории</h4>

        <div className="row g-3">
          {categories.map(category => (
            <div className="col-md-3 mx-auto" key={category.id}>
              <div className="category-card">{category.name}</div>
            </div>
          ))}
        </div>
      </section>

      <section className="mb-5">
        <h4 className="mb-3">Популярные услуги</h4>

        <div className="row g-4">
          {popularServices?.content?.map((service, idx) => (
            <div className="col-md-4" key={idx}>
              <ServiceCardComponent
                id={service.id}
                hidden={service.hide}
                title={service.title}
                price={service.price}
                freelancerName={
                  service?.freelancer?.firstName + " " + service?.freelancer?.lastName
                }
                image={service.imageURL || null}
                from={location.pathname}
              />
            </div>
          ))}
        </div>
      </section>

      <section className="mb-5">
        <h4 className="mb-4">Как это работает</h4>

        <div className="row">
          <div className="col-md-4 step">
            <i className="bi bi-search fs-1"></i>
            <h6 className="mt-2">Найдите услугу</h6>
          </div>

          <div className="col-md-4 step">
            <i className="bi bi-chat fs-1"></i>
            <h6 className="mt-2">Обсудите детали</h6>
          </div>

          <div className="col-md-4 step">
            <i className="bi bi-check-circle fs-1"></i>
            <h6 className="mt-2">Получите результат</h6>
          </div>
        </div>
      </section>

      <section className="mb-5">
        <h4 className="mb-3">Почему мы</h4>

        <div className="row g-3">
          <div className="col-md-4">
            <div className="category-card">
              <i className="bi bi-lightning-charge-fill" />
              {" "}Быстрое выполнение
            </div>
          </div>

          <div className="col-md-4">
            <div className="category-card">
              <i className="bi bi-shield-fill" />
              {" "}Безопасные сделки
            </div>
          </div>

          <div className="col-md-4">
            <div className="category-card">
              <i className="bi bi-star-fill"></i>
              {" "}Проверенные исполнители
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}
