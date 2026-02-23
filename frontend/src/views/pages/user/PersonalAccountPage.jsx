import { useEffect } from "react";
import HeaderComponent from "../../components/HeaderComponent";
import ServiceCardComponent from "../../components/service/ServiceCardComponent";
import { getUserData, isAuth } from "../../../logic/jwt";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { getInfoRequest } from "../../../logic/requests/user/userRequest";
import './css/personal_account_page.css';
import FooterComponent from "../../components/FooterComponent";

export default function PersonalAccountPage() {
  useEffect(() => {
    document.title = "Personal Account";
  }, [])

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
        <HeaderComponent />
      </>
    )
  }

  const localUserData = getUserData();

  return (
    <>
      <HeaderComponent />

      <main>
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
                  <span className="badge badge-purple">Top Rated</span>
                  <span className="ms-2 text-warning">★★★★★</span>
                  <span className="ms-1">4.9 (128 reviews)</span>
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
                <div className="fw-bold">Response Rate</div>
                <div>98%</div>
              </div>
              <div className="col-md-4">
                <div className="fw-bold">Completed Orders</div>
                <div>156</div>
              </div>
              <div className="col-md-4">
                <div className="fw-bold">Member Since</div>
                <div>2022</div>
              </div>
            </div>
          </div>

          <div className="row mb-4">
            <div className="col-md-4">
              <div className="stat-card">
                <h5>$12,450</h5>
                <div className="text-muted">Total Earnings</div>
              </div>
            </div>
            <div className="col-md-4">
              <div className="stat-card">
                <h5>8</h5>
                <div className="text-muted">Active Orders</div>
              </div>
            </div>
            <div className="col-md-4">
              <div className="stat-card">
                <h5>3</h5>
                <div className="text-muted">Pending Requests</div>
              </div>
            </div>
          </div>
          {
            userData.services && userData.services.lenght > 0 && (
              <>
                <h5 className="mb-3">My Services</h5>

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
