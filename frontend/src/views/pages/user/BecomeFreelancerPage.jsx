import { useContext, useEffect, useRef, useState } from "react";
import "./css/become_freelancer_page.css";
import { getAllJobTitlesRequest } from "../../../logic/requests/jobTitle";
import { becomeFreelancerRequest } from "../../../logic/requests/user/freelancerRequest";
import { Link, useNavigate } from "react-router-dom";
import LoadingComponent from "../../components/LoadingComponent";
import { hasRole, isAuth } from "../../../logic/jwt";

export function BecomeFreelancerPage() {
  const navigate = useNavigate();

  const [error, setError] = useState(null);
  const [jobTitles, setJobTitles] = useState([]);
  const [aboutMe, setAboutMe] = useState("");
  const [selectedJobTitleId, setSelectedJobTitleId] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!isAuth() || hasRole("ROLE_FREELANCER")) {
      navigate(-1);
      return;
    }
    document.title = "Стать фрилансером";

    (async () => {
      setJobTitles(
        await getAllJobTitlesRequest().then((res) => {
          setLoading(false);
          setSelectedJobTitleId(res.data?.at(0).id);
          return res.data;
        }),
      );
    })();
  }, [navigate]);

  if (loading) {
    return (
      <>
        <LoadingComponent />
      </>
    );
  }

  if (!jobTitles) return null;

  async function handleSubmit() {
    if (!aboutMe) {
      setError("Все поля обязятельны к заполнению");
      return;
    }

    if (jobTitles == []) {
      return;
    }

    if (selectedJobTitleId == "") {
      setSelectedJobTitleId(jobTitles[0].id);
    }

    const response = await becomeFreelancerRequest(aboutMe, selectedJobTitleId);
    if (response.status == 200) {
      navigate("/");
      return;
    }
    console.log("[ERROR] logic error");
  }


  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-xl-10">
          <div className="become-freelancer_freelancer-card">
            <div className="row g-5 align-items-center">
              <div className="col-lg-5">
                <div className="become-freelancer_preview">
                  <div className="become-freelancer_preview-icon">
                    <i className="bi bi-stars"></i>
                  </div>

                  <h2 className="fw-semibold mb-3">Станьте фрилансером</h2>

                  <p className="mb-4">
                    Расскажите о себе и начните принимать заказы уже сегодня.
                  </p>

                  <div className="d-flex flex-column gap-3">
                    <div>
                      <i className="bi bi-check-circle-fill me-2"></i>
                      Создавайте услуги
                    </div>

                    <div>
                      <i className="bi bi-check-circle-fill me-2"></i>
                      Получайте заказы
                    </div>

                    <div>
                      <i className="bi bi-check-circle-fill me-2"></i>
                      Общайтесь с клиентами
                    </div>
                  </div>
                </div>
              </div>

              <div className="col-lg-7">
                <h3 className="fw-semibold mb-4">Информация о профиле</h3>

                {error && (
                  <div className="alert alert-danger">
                    {error}
                  </div>
                )}

                <div className="mb-4">
                  <label className="form-label fw-semibold">Должность</label>

                  <div className="form-floating mb-3">
                    <select
                      className="form-select"
                      id="jobTitle-select"
                      value={ selectedJobTitleId }
                      onChange={ (e) => setSelectedJobTitleId(e.target.value) }
                    >
                      {
                        jobTitles.map((jobTitle) => {
                          return <option key={ jobTitle.id } value={ jobTitle.id }>{ jobTitle.name }</option>
                        })
                      }
                    </select>
                    <label htmlFor="jobTitle-select">Ваша должность</label>
                  </div>
                </div>

                <div className="mb-4">
                  <label className="form-label fw-semibold">О себе</label>

                  <textarea
                    value={aboutMe}
                    onChange={(e) => setAboutMe(e.target.value)}
                    rows="6"
                    className="form-control"
                    placeholder="Расскажите о своём опыте, навыках и специализации..."
                  ></textarea>
                </div>

                <div className="tip-box mb-4">
                  <i className="bi bi-lightbulb me-2"></i>
                  Чем подробнее заполнен профиль — тем выше вероятность получить
                  заказ.
                </div>

                <div className="d-flex gap-3">
                  <button className="btn btn-primary px-4" onClick={handleSubmit}>
                    Стать фрилансером
                  </button>

                  <Link className="btn btn-outline-secondary" to={-1}>Отмена</Link>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
