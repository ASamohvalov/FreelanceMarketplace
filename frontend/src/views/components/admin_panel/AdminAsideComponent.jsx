import { NavLink, useNavigate } from "react-router-dom";
import "./css/admin_components.css";

export default function AdminAsideComponent() {
  const navigate = useNavigate();

  return (
    <aside className="col-2 admin-panel-aside_sidebar">
      <h5 className="mb-4">Admin</h5>
      <button className="btn btn-dark w-100 mb-3" onClick={() => navigate("/")}>Выйти</button>

      <NavLink
        to="/admin"
        end
        className={({ isActive }) => (isActive ? "active" : "")}
      >
        <i className="bi bi-person-fill" /> Пользователи
      </NavLink>

      <NavLink
        to="/admin/services"
        className={({ isActive }) => (isActive ? "active" : "")}
      >
        <i className="bi bi-briefcase-fill" /> Услуги
      </NavLink>

      <NavLink
        to="/admin/categories"
        className={({ isActive }) => (isActive ? "active" : "")}
      >
        <i className="bi bi-folder-fill" /> Категории
      </NavLink>
    </aside>
  );
}
