import { useEffect, useRef, useState } from "react";
import HeaderComponent from "../../components/HeaderComponent";
import { getAllJobTitlesRequest } from "../../../logic/requests/jobTitle";
import { becomeFreelancerRequest } from "../../../logic/requests/user/freelancerRequest";
import { useNavigate } from "react-router-dom";

export function BecomeFreelancer() {
  useEffect(() => {
    document.title = "Become Freelancer";
  }, []);

  const navigate = useNavigate();

  const phoneNumber = useRef(null);

  const [error, setError] = useState(null);
  const [jobTitles, setJobTitles] = useState([]);
  const [selectedJobTitleId, setSelectedJobTitleId] = useState("");
  const [loaging, setLoading] = useState(true);

  useEffect(() => {
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
  }, []);


  if (loaging) {
    return (
      <>
        <HeaderComponent />
        <div className="d-flex justify-content-center align-items-center vh-100">
          <div className="spinner-border" role="status" style={{ height: "50px", width: "50px" }}>
            <span className="visually-hidden">Loading...</span>
          </div>
        </div>
      </>
    )
  }

  async function handleSubmit(event) {
    event.preventDefault();

    if (!phoneNumber.current.value) {
      setError("All fields are required");
    }

    if (jobTitles == []) {
      return;
    }

    if (selectedJobTitleId == "") {
      setSelectedJobTitleId(jobTitles[0].id)
      console.log(selectedJobTitleId);
      console.log(jobTitles);
    }
    console.log(selectedJobTitleId);

    const response = await becomeFreelancerRequest(phoneNumber.current.value, selectedJobTitleId);
    if (response.status == 200) {
      navigate("/");
      return;
    }
    console.log("[ERROR] logic error");
  }

  return (
    <>
      <HeaderComponent />

      <div className="mx-auto" style={{ width: "500px", marginTop: "200px" }}>
        <div
          className={`mb-2 bg-danger p-4 border border-danger rounded shadow ${error ? "visible" : "invisible"}`}
        >
          { error }
        </div>
        <div className="shadow w-100 bg-dark rounded p-4 text-light">
          <div className="text-center mb-3 h4">Become Freelancer</div>
          <form onSubmit={ handleSubmit } className="mb-4">
            <label htmlFor="phoneNumber-input">Phone number</label>
            <input
              className="form-control mb-3"
              id="phoneNumber-input"
              type="number"
              ref={ phoneNumber }
            />

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
              <label htmlFor="jobTitle-select">Your position</label>
            </div>
            <div className="mb-3">
              <a
                className="btn btn-light"
                onClick={ () => null }
              >+</a>
            </div>

            <button className="btn btn-primary" type="submit">
              Submit
            </button>
          </form>
        </div>
      </div>
    </>
  );
}
