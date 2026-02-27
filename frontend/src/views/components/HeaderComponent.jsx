import { Link } from "react-router-dom";
import { getUserData, hasRole, isAuth, logout } from "../../logic/jwt";
import { logoutRequest } from "../../logic/requests/user/userRequest";
import "./css/header_component.css";

export default function HeaderComponent() {
  async function onLogoutClick(event) {
    event.preventDefault();
    await logoutRequest();
    logout();
    window.location.reload();
  }

  function authLinks() {
    if (isAuth()) {
      var user = getUserData();
      return (
        <>
          <li className="nav-item">
            <Link to="/personal-account" className="nav-link active">
              {user.sub}
            </Link>
          </li>

          <li className="nav-item">
            <a onClick={onLogoutClick} href="" className="nav-link active">
              logout
            </a>
          </li>
        </>
      );
    }

    return (
      <>
        <li className="nav-item">
          <Link className="nav-link active" to="/sign-in">
            Sign in
          </Link>
        </li>
        <li className="nav-item">
          <Link className="nav-link active" to="/sign-up">
            Sign up
          </Link>
        </li>
      </>
    );
  }

  return (
    <header>
      <nav className="navbar navbar-custom bg-main-color navbar-expand-lg navbar-dark rounded-bottom-3 fixed-top">
        <div className="container-fluid d-flex justify-content-between align-items-center">
          <ul className="navbar-nav flex-row me-3">
            <li className="nav-item">
              <Link className="nav-link active" to="/">
                Home
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link active" to="/services">
                Services
              </Link>
            </li>
            {isAuth() && (
              <>
                <li className="nav-item">
                  <Link className="nav-link active" to="/messages">
                    Messages
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link active" to="/notifications">
                    Notifications
                  </Link>
                </li>
              </>
            )}
            {(() => {
              if (hasRole("ROLE_FREELANCER")) {
                return (
                  <li className="nav-item">
                    <Link className="nav-link active" to="/create-service">
                      Create Service
                    </Link>
                  </li>
                );
              } else if (isAuth()) {
                return (
                  <li className="nav-item">
                    <Link className="nav-link active" to="/become-freelancer">
                      Become Freelancer
                    </Link>
                  </li>
                );
              }
            })()}
          </ul>

          <img src="/images/logo.png" alt="Logo" height="40" />

          <ul className="navbar-nav flex-row ms-3">{authLinks()}</ul>
        </div>
      </nav>
    </header>
  );
}
