import { useEffect } from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { createJobTitleRequest, deleteJobTitleRequest, getAllJobTitlesRequest } from "../../../logic/requests/jobTitle";
import "./css/admin_pages.css";

export default function AdminPanelJobTitlePage() {
  const navigate = useNavigate();
  const [error, setError] = useState("");

  const [newJobTitle, setNewJobTitle] = useState("");
  const [jobTitles, setJobTitles] = useState([]);

  useEffect(() => {
    (async () => {
      const response = await getAllJobTitlesRequest();
      if (response.status !== 200) {
        navigate(`/error?code=${response.status}`);
        return;
      }
      setJobTitles(response.data);
    })();
  }, [navigate]);

  const createJobTitle = async () => {
    const response = await createJobTitleRequest(newJobTitle);
    if (response.status === 400) {
      setError("Данная должность уже существует")
      return;
    }
    if (response.status !== 200) {
      navigate(`/error?code=${response.status}`);
      return;
    }
    setJobTitles([
      ...jobTitles,
      {
        name: newJobTitle,
        id: response.data.id,
      },
    ]);
  };

  const deleteJobTitle = async (id) => {
    const response = await deleteJobTitleRequest(id);

    if (response.status === 400) {
      setError("Должность используется в фрилансерами, удаление недоступно");
      return;
    }
    if (response.status !== 200) {
      navigate(`/error?code=${response.status}`);
      return;
    }
    setJobTitles(prev => prev.filter(jt => jt.id !== id));
  };

  return (
    <main className="col-10 p-4 admin_main-flex">
      {error && (
        <div className="alert alert-danger" role="alert">
          {error}
        </div>
      )}
      <div className="admin-card mb-4">
        <h5 className="mb-3">Добавить должность</h5>

        <input
          className="form-control mb-2"
          placeholder="Название должности"
          value={newJobTitle}
          onChange={(e) => setNewJobTitle(e.target.value)}
        />

        <button className="btn btn-primary" onClick={createJobTitle}>
          Добавить
        </button>
      </div>

      <div className="admin-card admin_div-scrolling">
        <h5 className="mb-3">Список должностей</h5>

        <div className="accordion">
          {jobTitles.map(jobTitle => (
            <div className="p-3 border rounded mb-3" key={jobTitle.id}>
              <div className="d-flex justify-content-between">
                <span>{jobTitle.name}</span>
                <button className="btn btn-sm btn-dark" onClick={() => deleteJobTitle(jobTitle.id)}>
                  <i className="bi bi-trash3-fill"></i>
                </button>
              </div>
            </div>
          ))}
        </div>
      </div>
    </main>
  );
}
