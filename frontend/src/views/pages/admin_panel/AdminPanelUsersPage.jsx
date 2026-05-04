import { useState } from "react";
import { useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { getUsersRequest } from "../../../logic/requests/user/userRequest";
import { getRuRole } from "../../../logic/role";
import "./css/admin_pages.css";

export default function AdminPanelUsersPanel() {
  const navigate = useNavigate();
  const [users, setUsers] = useState([]);
  const [page, setPage] = useState(0);

  const allRoles = ["ROLE_ADMIN", "ROLE_USER", "ROLE_FREELANCER"];
  const paginateStep = 10;

  useEffect(() => {
    (async () => {
      const response = await getUsersRequest(0, paginateStep);
      if (response.status !== 200) {
        navigate(`/error?code=${response.status}`);
        return;
      }
      setUsers(response.data);
    })();
  }, [navigate])

  const changePage = async (pageNumber) => {
    const response = await getUsersRequest(pageNumber, paginateStep);
    if (response.status !== 200) {
      navigate(`/error?code=${response.status}`);
      return;
    }
    setUsers(response.data);

    setPage(pageNumber);
  }

  return (
    <main className="col-10 p-4">
      <div className="admin-tab">
        <div className="admin-card">
          <div className="d-flex justify-content-between mb-3">
            <h5>Пользователи</h5>
            <span>
              <button className="btn btn-primary mx-2">Сохранить</button>
              <button className="btn btn-outline-secondary">Вернуть обранто</button>
            </span>
          </div>

          <table className="table">
            <thead>
              <tr>
                <th>Email</th>
                <th>Имя</th>
                <th>Роль</th>
              </tr>
            </thead>

            <tbody>
              {users?.content?.map((user, idx) => (
                <tr key={idx}>
                  <td>{user.email}</td>
                  <td>{user.firstName + " " + user.lastName}</td>
                  <td>
                    <select className="form-select form-select-sm">
                      {allRoles.map((role, idx) => (
                        <option key={idx} defaultValue={role} selected={user.roles.includes(role)}>{getRuRole(role)}</option>
                      ))}
                    </select>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          <nav aria-label="Page navigation example">
            <ul className="pagination">
              <li className="page-item">
                <Link
                  className="page-link"
                  onClick={() => changePage(page - 1)}
                  aria-label="Previous">
                  <span aria-hidden="true">&laquo;</span>
                </Link>
              </li>
              {Array.from({ length: users.totalPages }, (_, i) => (
                <li key={i} className="page-item">
                  <Link
                    className={`page-link ${page === i ? "active" : ""}`}
                    onClick={() => changePage(i)}
                  >
                    {i + 1}
                  </Link>
                </li>
              ))}
              <li className="page-item">
                <Link
                  className="page-link"
                  onClick={() => changePage(page + 1)}
                  aria-label="Previous">
                  <span aria-hidden="true">&raquo;</span>
                </Link>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </main>
  );
}
