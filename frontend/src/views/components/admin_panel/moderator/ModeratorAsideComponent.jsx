import { NavLink, useNavigate } from "react-router-dom";
import "../css/admin_components.css";

export default function ModeratorAsideComponent() {
  const navigate = useNavigate();

  return (
    <aside className="col-2 admin-panel-aside_sidebar">
      <h5 className="mb-4">Moderator panel</h5>
      <button className="btn btn-dark w-100 mb-3" onClick={() => navigate("/")}>Выйти</button>

      <NavLink
        to="/moderator/feedback"
        className={({ isActive }) => (isActive ? "active" : "")}
      >
        <i className="bi bi-megaphone-fill" /> Обратная связь
      </NavLink>

    </aside>
  );
}
