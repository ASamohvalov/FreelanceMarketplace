import { Link, useSearchParams } from "react-router-dom";
import HeaderComponent from "../../components/HeaderComponent";
import "./css/error_page.css";

export default function ErrorPage() {
  const [searchParams] = useSearchParams();

  const code = Number(searchParams.get("code")) || 404;

  return (
    <>
      <main id="error-page-main">
        <div className="error-card border">
          <div className="error-code">{code}</div>

          <div className="error-title">
            {(() => {
              switch (code) {
                case 403: return "вход запрещен";
                case 500: return "серверная ошибка";
                case 300: return "клиентская ошибка";
                default:  return "страница не найдена";
              }
            })()}
          </div>

          <p className="error-text">
            Страница, которую вы ищете, не существует или была перемещена.
          </p>

          <div className="d-flex justify-content-center gap-3">
            <Link to="/" className="btn btn-purple">
              На главную
            </Link>
            <Link
              to=".."
              className="btn btn-outline-secondary"
            >
              Вернуться обратно
            </Link>
          </div>
        </div>
      </main>
    </>
  );
}
