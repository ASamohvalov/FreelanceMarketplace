export default function FooterComponent() {
  return (
    <footer className="" style={{display: "absolute", bottom: 0}}>
      <div className="container">
        <div className="row">
          <div className="col-md-4">
            <h5>Фриланс Маркет</h5>
            <p className="small mt-3">
              Соединяем фрилансеров и компании по всему миру.
            </p>
          </div>

          <div className="col-md-4">
            <h6>Компания</h6>
            <ul className="list-unstyled small mt-3">
              <li>
                <a href="#">О нас</a>
              </li>
              <li>
                <a href="#">Карьера</a>
              </li>
              <li>
                <a href="#">Поддержка</a>
              </li>
            </ul>
          </div>

          <div className="col-md-4">
            <h6>Правовая информация</h6>
            <ul className="list-unstyled small mt-3">
              <li>
                <a href="#">Правила</a>
              </li>
              <li>
                <a href="#">Политика конфиденциальности</a>
              </li>
            </ul>
          </div>
        </div>

        <hr className="bg-secondary mt-4" />
        <div className="text-center small">
          © 2026 Фриланс Маркет. Все права защищены.
        </div>
      </div>
    </footer>
  );
}
