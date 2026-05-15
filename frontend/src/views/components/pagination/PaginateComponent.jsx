import { useState } from "react";
import { Link } from "react-router-dom";

export default function PaginateComponent({ requestFunction, setData, data, onError, paginateStep=10 }) {
  const [page, setPage] = useState(0);

  const changePage = async (pageNumber) => {
    const response = await requestFunction(pageNumber, paginateStep);
    if (response.status !== 200) {
      onError(response.status);
      return;
    }
    setData(response.data);

    setPage(pageNumber);
  }

  return (
    <nav aria-label="Page navigation">
      <ul className="pagination">
        <li className="page-item">
          <Link
            className="page-link"
            onClick={() => changePage(page - 1)}
            aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
          </Link>
        </li>
        {Array.from({ length: data.totalPages }, (_, i) => (
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
  );
}
