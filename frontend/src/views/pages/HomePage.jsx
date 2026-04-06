import { useContext } from 'react';
import './css/home_page.css';
import { userContext } from '../../logic/store/userContext';
import { useEffect } from 'react';
import { hasRole, isAuth } from '../../logic/jwt';
import { useState } from 'react';
import { NavLink } from 'react-router-dom';

export default function HomePage() {
    const [user, setUser] = useContext(userContext);
    const [search, setSearch] = useState('');
    useEffect(() => {
        setUser({ hasRole: hasRole("ROLE_FREELANCER"), isAuth: isAuth() });
    }, [setUser]);
  return (
    <div style={{marginLeft: "80px"}}>
          <section className="hero text-center">
        <div className="container">
          <h1 className="display-5 fw-bold">Найди подходящего фрилансера для своего проекта</h1>
          <p className="lead mt-3">Веб-разработка, дизайн, маркетинг и многое другое — все в одном месте.</p>

          <div className="hero-search input-group">
            <input type="text" className="form-control form-control-lg" placeholder="Найдите услуги..." value={search} onChange={(e) => setSearch(e.target.value)} />
            <NavLink to={`/services?search=${search}`} className="btn btn-light btn-lg">Поиск</NavLink>
          </div>
        </div>
      </section>

      <section className="py-5">
        <div className="container">
          <h2 className="text-center mb-5">Популярные категории</h2>

          <div className="row g-4">

            <div className="col-md-3">
              <div className="category-card">
                <h5>Development & IT</h5>
                <small className="text-muted">Много услуг</small>
              </div>
            </div>

            <div className="col-md-3">
              <div className="category-card">
                <h5>Design & Creative</h5>
                <small className="text-muted">Много услуг</small>
              </div>
            </div>

            <div className="col-md-3">
              <div className="category-card">
                <h5>Marketing & Sales</h5>
                <small className="text-muted">Много услуг</small>
              </div>
            </div>

            <div className="col-md-3">
              <div className="category-card">
                <h5>Writing & Content</h5>
                <small className="text-muted">Много услуг</small>
              </div>
            </div>

          </div>
        </div>
      </section>

      <section className="py-5 bg-light">
        <div className="container">
          <h2 className="text-center mb-5">Особые услуги</h2>

          <div className="row g-4">

            <div className="col-md-4">
              <div className="service-card">
                <div className="service-img"></div>
                <div className="p-4">
                  <strong>Я сверстаю ваш сайт на WordPress</strong>
                  <div className="text-muted small mt-2">Cris James</div>
                  <div className="mt-3 fw-bold">От 1999 ₽</div>
                </div>
              </div>
            </div>

            <div className="col-md-4">
              <div className="service-card">
                <div className="service-img"></div>
                <div className="p-4">
                  <strong>Я сделаю красивый логотип вашей компании!</strong>
                  <div className="text-muted small mt-2">Anna Lee</div>
                  <div className="mt-3 fw-bold">От 999 ₽</div>
                </div>
              </div>
            </div>

            <div className="col-md-4">
              <div className="service-card">
                <div className="service-img"></div>
                <div className="p-4">
                  <strong>Я продумаю стратегию маркетинга</strong>
                  <div className="text-muted small mt-2">Michael Scott</div>
                  <div className="mt-3 fw-bold">От 2499 ₽</div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </section>

      <section className="py-5">
        <div className="container">
          <h2 className="text-center mb-5">Как это работает</h2>

          <div className="row g-4">

            <div className="col-md-4">
              <div className="step-card">
                <h4>1️⃣ Поиск</h4>
                <p className="text-muted mt-3">Исследуйте тысячи услуг и найдите то, что вам нужно.</p>
              </div>
            </div>

            <div className="col-md-4">
              <div className="step-card">
                <h4>2️⃣ Нанять</h4>
                <p className="text-muted mt-3">Выберите лучшего фрилансера и закажите работу.</p>
              </div>
            </div>

            <div className="col-md-4">
              <div className="step-card">
                <h4>3️⃣ Получить результаты</h4>
                <p className="text-muted mt-3">Получите высококачественную работу в срок.</p>
              </div>
            </div>

          </div>
        </div>
      </section>

      <section className="cta">
        <div className="container">
          <h2>Начните продавать свои услуги прямо сейчас</h2>
          <p className="mt-3">Присоединяйтесь к тысячам фрилансеров, получающих доход онлайн.</p>
          <a href="#" className="btn btn-light btn-lg mt-3">Стать фрилансером</a>
        </div>
      </section>

    </div>
  );
}
