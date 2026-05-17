import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { getUserData, hasRole, isAuth } from "../../../logic/jwt";
import { getAllJobTitlesRequest } from "../../../logic/requests/jobTitle";
import {
  editFreelancerProfile,
  getFreelancerRequest,
} from "../../../logic/requests/user/freelancerRequest";
import "./css/personal_account_page.css";
import {
  convertPointsRequest,
  getBalanceRequest,
  getCurrentPointRateRequest,
  getExpenseTransfersRequest,
  getIncomeTransfersRequest,
} from "../../../logic/requests/payment/accountRequest";
import TransferCardComponent from "../../components/payment/TransferCardComponent";
import { fromIsoDate } from "../../../logic/time";
import {
  editProfileRequest,
  uploadAvatarRequest,
} from "../../../logic/requests/user/userRequest";
import { getAvatarUrl } from "../../../logic/image";
import Avatar from "../../components/elements/Avatar";
import { fromKopeck } from "../../../logic/lang";

export default function PersonalAccountPage() {
  const navigate = useNavigate();
  const [editMode, setEditMode] = useState(false);
  const [successMessage, setSuccessMessage] = useState(null);
  const [transferPoints, setTransferPoints] = useState(null);

  // personal data
  const [fullName, setFullName] = useState("");
  const [jobTitle, setJobTitle] = useState("");
  const [aboutYourself, setAboutYourself] = useState("");
  const [email, setEmail] = useState("");
  const [balance, setBalance] = useState(0);
  const [numberOfPoints, setNumberOfPoints] = useState(0);
  const [uploadAvatar, setUploadAvatar] = useState(null);

  // data
  const [jobTitles, setJobTitles] = useState([]);
  const [expenseTransfers, setExpenseTransfers] = useState([]);
  const [incomeTransfers, setIncomeTransfers] = useState([]);
  const [currentPointRate, setCurrentPointRate] = useState(null);

  // variables
  const isFreelancer = hasRole("ROLE_FREELANCER");

  useEffect(() => {
    if (!isAuth()) {
      navigate(-1);
      return;
    }

    document.title = "Личный кабинет";

    (async () => {
      const jobTitleResponse = await getAllJobTitlesRequest();
      if (jobTitleResponse.status !== 200) {
        navigate(`/error?code=${jobTitleResponse.status}`);
        return;
      }
      setJobTitles(jobTitleResponse.data);

      const expenseTransfersResponse = await getExpenseTransfersRequest();
      if (expenseTransfersResponse.status !== 200) {
        navigate(`/error?code=${expenseTransfersResponse.status}`);
        return;
      }
      setExpenseTransfers(expenseTransfersResponse.data);

      if (isFreelancer) {
        const personalDataResponse = await getFreelancerRequest();
        if (personalDataResponse.status !== 200) {
          navigate(`/error?code=${personalDataResponse.status}`);
          return;
        }
        setFullName(
          personalDataResponse.data.firstName +
            " " +
            personalDataResponse.data.lastName,
        );
        setJobTitle({
          id: personalDataResponse.data.jobTitleId,
          name: personalDataResponse.data.jobTitle,
        });
        setAboutYourself(personalDataResponse.data.aboutYourself);
        setEmail(personalDataResponse.data.email);

        const balanceResponse = await getBalanceRequest();
        if (balanceResponse.status !== 200) {
          navigate(`/error?code=${balanceResponse.status}`);
          return;
        }
        setBalance(balanceResponse.data.balance);
        setNumberOfPoints(balanceResponse.data.numberOfPoints);

        const incomeTransfersResponse = await getIncomeTransfersRequest();
        if (incomeTransfersResponse.status !== 200) {
          navigate(`/error?code=${incomeTransfersResponse.status}`);
          return;
        }
        setIncomeTransfers(incomeTransfersResponse.data);
      } else {
        const userData = getUserData();
        setFullName(userData.firstName + " " + userData.lastName);
        setEmail(userData.sub);
      }
    })();
  }, [navigate, isFreelancer]);

  const transferPointsModeChange = async () => {
    if (currentPointRate === null) {
      const response = await getCurrentPointRateRequest();
      if (response.status !== 200) {
        navigate("/error");
        return;
      }
      setCurrentPointRate(response.data.rate)
    }

    setTransferPoints(transferPoints !== null ? null : numberOfPoints);
  }

  const convertPoints = async () => {
    if (transferPoints !== 0 && transferPoints <= numberOfPoints) {
      const response = await convertPointsRequest(transferPoints);
      if (response.status !== 200) {
        navigate("/error");
        return;
      }

      setSuccessMessage("Деньги начисленны");
    }
  };

  return (
    <div className="container mt-5 mb-5">
      <div className="row g-4">
        <div className="col-lg-3">
          <div className="personal-account-page_profile-card">
            <i
              className="bi bi-pencil personal-account-page_edit-btn"
              onClick={() => { setEditMode(!editMode); setTransferPoints(null) }}
            ></i>

            <Avatar
              className="personal-account-page_avatar"
              userId={getUserData().id}
            />

            <div className="text-center">
              <label
                htmlFor="inputFile"
                className={`btn btn-outline-secondary w-100 mb-2 ${editMode ? "" : "d-none"}`}
              >
                Изменить аватар
              </label>
              <input
                type="file"
                className="form-control mb-2 d-none"
                onChange={(e) => setUploadAvatar(e.target.files[0])}
                id="inputFile"
              />

              <h5 className={editMode ? "d-none" : ""}>{fullName}</h5>

              <input
                className={`form-control mb-2 ${editMode ? "" : "d-none"}`}
                value={fullName}
                onChange={(e) => {
                  const value = e.target.value;
                  if (value === " ") return;
                  const spaceCount = (value.match(/ /g) || []).length;
                  if (spaceCount > 1) return;
                  setFullName(value);
                }}
              />

              <div className="text-muted small">{email}</div>
            </div>

            {isFreelancer && (
              <div className="personal-account-page_freelancer-only mt-3">
                <hr />

                <div
                  className={`text-center fw-semibold ${editMode ? "d-none" : ""}`}
                >
                  {jobTitle.name}
                </div>
                <select
                  className={`form-select mb-2 ${editMode ? "" : "d-none"}`}
                  value={jobTitle.id}
                  onChange={(e) => setJobTitle(e.target.value)}
                >
                  {jobTitles.map((jt) => (
                    <option key={jt.id} value={jt.id}>
                      {jt.name}
                    </option>
                  ))}
                </select>

                <div
                  id="aboutText"
                  className={`text-muted small mt-1 ${editMode ? "d-none" : ""}`}
                >
                  {aboutYourself}
                </div>
                <textarea
                  rows={6}
                  value={aboutYourself}
                  className={`form-control mt-2 ${editMode ? "" : "d-none"}`}
                  onChange={(e) => setAboutYourself(e.target.value)}
                ></textarea>
              </div>
            )}

            <button
              className={`btn btn-primary w-100 mt-3 ${editMode ? "" : "d-none"}`}
              onClick={async () => {
                const [firstName, lastName] = fullName.trim().split(/\s+/);
                if (isFreelancer) {
                  const response = await editFreelancerProfile(
                    firstName,
                    lastName,
                    jobTitle.id || jobTitle,
                    aboutYourself,
                  );
                  if (response.status !== 200) {
                    navigate(`/error?code=${response.status}`);
                    return;
                  }
                } else {
                  const response = await editProfileRequest(
                    firstName,
                    lastName,
                  );
                  if (response.status !== 200) {
                    navigate(`/error?code=${response.status}`);
                    return;
                  }
                }

                if (uploadAvatar !== null) {
                  const response = await uploadAvatarRequest(uploadAvatar);
                  if (response.status !== 200) {
                    navigate(`/error?code=${response.status}`);
                    return;
                  }

                  setUploadAvatar(getAvatarUrl(getUserData().id));
                }

                setSuccessMessage("Профиль успешно обновлен");
              }}
            >
              Сохранить
            </button>

            {!editMode && (
              <>
                <button
                  className="btn btn-outline-primary w-100 mt-3"
                  onClick={() => {
                    navigate("/profile/" + getUserData().id);
                  }}
                >
                  Посмотреть профиль
                </button>

                {isFreelancer ? (
                  <button
                    className="btn btn-outline-secondary w-100 mt-3"
                    onClick={transferPointsModeChange}
                  >
                    Списать баллы
                  </button>
                ) : (
                  <Link
                    className="btn btn-outline-secondary w-100 mt-3"
                    to="/become-freelancer"
                  >
                    Стать фрилансером
                  </Link>
                )}
              </>
            )}

            {transferPoints !== null && (
              <div className="mt-4 border rounded p-3">
                <label htmlFor="transferPointsInput">Количество баллов</label>
                <input
                  id="transferPointsInput"
                  className="form-control"
                  type="number"
                  value={transferPoints}
                  min="0"
                  max={numberOfPoints}
                  onChange={(e) => {
                    if (e.target.value <= numberOfPoints) {
                      setTransferPoints(e.target.value)
                    }
                  }}
                />
                <p className="mt-2">Денег будет получено: { fromKopeck(currentPointRate * transferPoints) }</p>
                <button
                  className="btn btn-primary w-100"
                  onClick={convertPoints}
                >Списать</button>
              </div>
            )}

            {successMessage && (
              <div
                className={`mt-3 alert alert-success ${(editMode || transferPoints) ? "" : "d-none"}`}
                role="alert"
              >
                {successMessage}
              </div>
            )}
          </div>
        </div>

        <div className="col-lg-9">
          {isFreelancer && (
            <div className="row">
              <div className="col-7 mx-1 personal-account-page_balance-card mb-4">
                <div className="d-flex justify-content-between">
                  <div>
                    <div className="small">Баланс</div>
                    <div className="fs-4 fw-semibold">
                      {fromKopeck(balance)}
                    </div>
                  </div>
                  <i className="bi bi-wallet2 fs-1"></i>
                </div>
              </div>
              <div className="col-3 mx-1 personal-account-page_point-card mb-4">
                <div className="d-flex justify-content-between">
                  <div>
                    <div className="small">Баллы</div>
                    <div className="fs-4 fw-semibold">{numberOfPoints}</div>
                  </div>
                  <i
                    className="bi bi-p-circle-fill"
                    style={{ fontSize: 40 }}
                  ></i>
                </div>
              </div>
            </div>
          )}

          <h6 className="mb-3">Начисления (оплата заказов)</h6>

          <div className="row g-3 mb-4">
            {expenseTransfers.map((transfer, idx) => (
              <TransferCardComponent
                serviceTitle={transfer.serviceTitle}
                price={transfer.amount}
                date={fromIsoDate(transfer.createdAt)}
                isIncome={false}
                key={idx}
                status={transfer.status}
              />
            ))}
          </div>

          {isFreelancer && (
            <div className="personal-account-page_freelancer-only">
              <h6 className="mb-3">Поступления (доход)</h6>

              <div className="row g-3">
                {incomeTransfers.map((transfer, idx) => (
                  <TransferCardComponent
                    serviceTitle={transfer.serviceTitle}
                    price={transfer.amount}
                    date={fromIsoDate(transfer.createdAt)}
                    key={idx}
                  />
                ))}
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
