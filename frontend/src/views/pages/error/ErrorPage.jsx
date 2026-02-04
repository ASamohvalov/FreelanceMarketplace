import { Link } from "react-router-dom";
import HeaderComponent from "../../components/HeaderComponent";
import "./css/error_page.css";

export default function ErrorPage({ code, message }) {

  return (
    <>
      <HeaderComponent />
      <main id="error-page-main">
        <div className="error-card border">
          <div className="error-emoji">😕</div>
          <div className="error-code">404</div>

          <div className="error-title">
            Page not found
          </div>

          <p className="error-text">
            The page you are looking for doesn’t exist or has been moved.
            Don’t worry — let’s get you back on track.
          </p>

          <div className="d-flex justify-content-center gap-3">
            <Link to="/" className="btn btn-purple">
              Go to homepage
            </Link>
            <a href="javascript:history.back()" className="btn btn-outline-secondary">
              Go back
            </a>
          </div>
        </div>
      </main>
    </>
  );
}
