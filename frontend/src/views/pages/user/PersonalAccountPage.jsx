import { useEffect } from "react";
import ServiceCardComponent from "../../components/service/ServiceCardComponent";
import { getUserData, isAuth, hasRole } from "../../../logic/jwt";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { getInfoRequest } from "../../../logic/requests/user/userRequest";
import './css/personal_account_page.css';
import { userContext } from "../../../logic/store/userContext";
import { useContext } from "react";

export default function PersonalAccountPage() {
  const [user, setUser] = useContext(userContext);
  useEffect(() => {
    document.title = "Личный кабинет";
  }, [])
      useEffect(() => {
          setUser({ hasRole: hasRole("ROLE_FREELANCER"), isAuth: isAuth() });
      }, [setUser]);

  const navigate = useNavigate();
  const [loading, setLoading] = useState(true);
  const [userData, setUserData] = useState({});

  useEffect(() => {
    // if user dont have tokens in localstorage
    if (!isAuth()) {
      navigate(-1); // go back
      return;
    }

    (async () => {
      const response = await getInfoRequest();
      if (response.status !== 200) {
        console.log("logic error");
        navigate(-1);
        return;
      }
      setUserData(response.data);
      setLoading(false);
    })();
  }, [navigate])


  if (loading) {
    return (
      <>
      </>
    )
  }

  const localUserData = getUserData();

  return (
    <>

      <main style={{minHeight:"80vh", marginLeft: 80}}>
        <div className="container">
          <div className="profile-card mb-4 mt-4">
            <div className="d-flex align-items-center gap-4">
              <div className="avatar-lg">CJ</div>
              <div>
                <h4 className="mb-1">
                  {
                    (() => {
                      const usdata = userData ? userData : localUserData;
                      return usdata.firstName + " " + usdata.lastName;
                    })()
                  }
                </h4>
                <div className="text-muted">Full-Stack Web Developer</div>
                <div className="mt-2">
                  <span className="badge badge-purple">Оценка</span>
                  <span className="ms-2 text-warning">★★★★★</span>
                  <span className="ms-1">4.9 (128 отзывов)</span>
                </div>
              </div>
            </div>

            <hr />

            <p>
              {
                userData?.aboutYourself
              }
            </p>

            <div className="row text-center mt-3">
              <div className="col-md-4">
                <div className="fw-bold">Качество выполнения</div>
                <div>98%</div>
              </div>
              <div className="col-md-4">
                <div className="fw-bold">Завершенные заказы</div>
                <div>156</div>
              </div>
              <div className="col-md-4">
                <div className="fw-bold">Дата регистрации</div>
                <div>2022</div>
              </div>
            </div>
          </div>

          <div className="row mb-4">
            <div className="col-md-4">
              <div className="stat-card">
                <h5>$12,450</h5>
                <div className="text-muted">Общий доход</div>
              </div>
            </div>
            <div className="col-md-4">
              <div className="stat-card">
                <h5>8</h5>
                <div className="text-muted">Активные заказы</div>
              </div>
            </div>
            <div className="col-md-4">
              <div className="stat-card">
                <h5>3</h5>
                <div className="text-muted">Завершенные заказы</div>
              </div>
            </div>
          </div>
          {
            userData.services && userData.services.lenght > 0 && (
              <>
                <h5 className="mb-3">Мои услуги</h5>

                <div className="row g-4">
                  {
                    userData.services.map((service) => {
                      return (
                        <ServiceCardComponent id={service.id} title={service.title} price={service.price} key={service.id} />
                      );
                    })
                  }
                </div>
              </>
            )
          }
        </div>
      </main>
    </>
  );
}
