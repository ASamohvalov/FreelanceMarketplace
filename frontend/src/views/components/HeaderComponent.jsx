import { Link } from "react-router-dom";
import { getUserData, hasRole, isAuth, logout } from "../../logic/jwt";
import { logoutRequest } from "../../logic/requests/user/userRequest";

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
            <Link
              to="/personal-account"
              className="nav-link active">{ user.sub }
            </Link>
          </li>

          <li className="nav-item">
            <a onClick={ onLogoutClick }
              href=""
              className="nav-link active">logout
            </a>
          </li>
        </>
      );
    }

    return (
      <>
        <li className="nav-item">
          <Link className="nav-link active" to="/sign-in">Sign in</Link>
        </li>
        <li className="nav-item">
          <Link className="nav-link active" to="/sign-up">Sign up</Link>
        </li>
      </>
    )
  }

  return (
    <header>
      <nav className="navbar navbar-expand-lg bg-dark navbar-dark">
        <div className="container-fluid">
          <a className="navbar-brand" href="#">FreelanceMarketplace</a>
          <button className="navbar-toggler" type="button">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav">
              <li className="nav-item">
                <Link className="nav-link active" to="/">Home</Link>
              </li>
              {
                (() => {
                  if (isAuth() && !hasRole("ROLE_FREELANCER")) {
                    return (
                      <li className="nav-item">
                        <Link className="nav-link active" to="/become-freelancer">Become Freelancer</Link>
                      </li>
                    );
                  }
                })()
              }
            </ul>

            <div className="ms-auto">
              <ul className="navbar-nav">
                { authLinks() }
              </ul>
            </div>
          </div>
        </div>
      </nav>
    </header>
  );
}
