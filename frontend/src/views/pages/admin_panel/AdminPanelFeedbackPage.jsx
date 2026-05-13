import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { feedbackTypes } from "../../../logic/feedback";
import { getFeedbacksRequest } from "../../../logic/requests/feedbackRequest";
import AdminFeedbackCard from "../../components/admin_panel/AdminFeedbackCard";
import "./css/admin_pages.css";
import AdminFeedbackInfoComponent from "../../components/admin_panel/AdminFeedbackInfoComponent";

export default function AdminPanelFeedbackPage() {
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useSearchParams();

  const [selectedFeedbackType, setSelectedFeedbackType] = useState("all");
  const [selectedStatus, setSelectedStatus] = useState("notAccepted");
  const [feedbackShowMode, setFeedbackShowMode] = useState(null);
  const [feedbacks, setFeedbacks] = useState([]);

  useEffect(() => {
    (async () => {
      const response = await getFeedbacksRequest();
      if (response.status !== 200) {
        navigate("/error?code=" + response.status);
        return;
      }
      setFeedbacks(response.data);

      const id = searchParams.get("id");
      if (id) {
        setFeedbackShowMode(response.data.find(f => f.id === id));
      }
    })();
  }, [navigate, searchParams]);

  const handleCardClick = (id) => {
    if (feedbackShowMode) {
      setSearchParams({});
      setFeedbackShowMode(null);
    } else {
      setSearchParams({ id: id });
      setFeedbackShowMode(feedbacks.find(f => f.id === id));
    }
  };

  const handleFeedbackInfoClose = () => {
    setSearchParams({});
    setFeedbackShowMode(null);
  };

  return (
    <main className="col-10 p-4 admin_main-flex">
      <div className="admin-card mb-4">
        <h5 className="mb-3">Обратная связь</h5>

        <div className="d-flex justify-content-between">
          <select
            className="form-select mx-1"
            value={ selectedFeedbackType }
            onChange={ (e) => setSelectedFeedbackType(e.target.value) }
          >
            <option value={"all"}>Все виды обращений</option>
            {
              Object.keys(feedbackTypes).map(key => (
                <option key={ key } value={ key }>{ feedbackTypes[key] }</option>
              ))
            }
          </select>

          <select
            className="form-select mx-1"
            value={ selectedStatus }
            onChange={ (e) => setSelectedStatus(e.target.value) }
          >
            <option value={"notAccepted"}>Непринятые</option>
            <option value={"all"}>Все</option>
            <option value={"accepted"}>Принятые</option>
          </select>
        </div>

        {feedbackShowMode && (
          <div className="p-1">
            <AdminFeedbackInfoComponent
              id={feedbackShowMode.id}
              title={feedbackShowMode.title}
              text={feedbackShowMode.text}
              sender={feedbackShowMode.sender}
              type={feedbackShowMode.type}
              createdAt={feedbackShowMode.createdAt}
              accepted={feedbackShowMode.accepted}
              onClose={handleFeedbackInfoClose}
            />
          </div>
        )}
      </div>

      <div className="admin-card mb-4 admin_div-scrolling">
        <h5 className="mb-3">
          {selectedStatus === "all" && "Все обращения пользователей"}
          {selectedStatus === "accepted" && "Принятые обращения"}
          {selectedStatus === "notAccepted" && "Непринятые обращения"}
        </h5>

        <div className="row g-4 mt-3">
          {feedbacks.map(feedback => (
            <div className="col-md-4" key={feedback.id}>
              <AdminFeedbackCard
                title={feedback.title}
                id={feedback.id}
                type={feedback.type}
                text={feedback.text}
                createdAt={feedback.createdAt}
                handleClick={handleCardClick}
              />
            </div>
          ))}
        </div>
      </div>
    </main>
  );
}
