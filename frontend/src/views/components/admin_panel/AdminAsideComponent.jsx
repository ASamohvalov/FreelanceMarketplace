import { Link } from "react-router-dom";
import "./css/admin_components.css";

export default function AdminAsideComponent() {
  return (
    <aside className="col-2 admin-panel-aside_sidebar">
      <h5 className="mb-4">Admin</h5>

      <button className="btn btn-dark w-100 mb-3">Выйти</button>

      <Link to="/" className="active">
        <i className="bi bi-person-fill" /> Пользователи
      </Link>

      <Link to="/">
        <i className="bi bi-briefcase-fill" /> Услуги
      </Link>
      <Link to="/">
        <i className="bi bi-folder-fill" /> Категории
      </Link>
    </aside>
  );
}
