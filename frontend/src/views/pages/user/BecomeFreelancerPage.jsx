import { useContext, useEffect, useRef, useState } from "react";
import "./css/become_freelancer_page.css"
import { getAllJobTitlesRequest } from "../../../logic/requests/jobTitle";
import { becomeFreelancerRequest } from "../../../logic/requests/user/freelancerRequest";
import { useNavigate } from "react-router-dom";
import LoadingComponent from "../../components/LoadingComponent";
import { hasRole, isAuth } from "../../../logic/jwt";

export function BecomeFreelancerPage() {
  const navigate = useNavigate();

  const phoneNumber = useRef(null);

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
        await getAllJobTitlesRequest()
          .then((res) => {
            setLoading(false);
            setSelectedJobTitleId(res.data?.at(0).id);
            return res.data;
          })
      );
    })();
  }, [navigate]);


  if (loading) {
    return (
      <>
        <LoadingComponent />
      </>
    )
  }

  if (!jobTitles) return null;

  async function handleSubmit(event) {
    event.preventDefault();

    if (!aboutMe) {
      setError("All fields are required");
      return;
    }

    if (jobTitles == []) {
      return;
    }

    if (selectedJobTitleId == "") {
      setSelectedJobTitleId(jobTitles[0].id)
    }

    const response = await becomeFreelancerRequest(aboutMe, selectedJobTitleId);
    if (response.status == 200) {
      navigate("/");
      return;
    }
    console.log("[ERROR] logic error");
  }

  return (
    <>

      <div className="mx-auto" style={{ width: "500px", marginTop: "10vh", marginBottom: "20vh"}}>
        <div
          className={`mb-2 bg-danger text-white text-center p-4 border border-danger rounded shadow ${error ? "visible" : "invisible"}`}
        >
          { error }
        </div>
        <div className="shadow w-100 form rounded-2 p-4 text-light">
          <div className="text-center form_title mb-3 h4">Стать фрилансером</div>
          <form onSubmit={ handleSubmit } className="mb-4">
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
            <div className="mb-3">
              <label htmlFor="about-u" className="text-secondary"> О себе</label>
              <textarea
                id="about-u"
                rows={3}
                value={aboutMe}
                onChange={(e) => setAboutMe(e.target.value)}
                className="form-control"
              />
            </div>
            <div className="d-flex justify-content-center">
              <button className="btn btn-main p-2 form_submit mx-auto" type="submit">
                Стать фрилансером
              </button>
            </div>
          </form>
        </div>
      </div>
    </>
  );
}
