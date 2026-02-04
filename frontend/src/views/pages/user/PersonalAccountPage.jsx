import { useEffect } from "react";
import HeaderComponent from "../../components/HeaderComponent";
import ServiceCardComponent from "../../components/service/ServiceCardComponent";
import { getUserData, isAuth } from "../../../logic/jwt";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { getInfoRequest } from "../../../logic/requests/user/userRequest";

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
          <div className="rounded border border-secondary p-4 m-4">
            <div className="row mb-3">
              <div className="col-4">
                <div className="text-center">
                  <img className="rounded border border-subtle mb-3" style={{ height: "150px", width: "150px" }} />
                  <div className="h4 mx-2">
                    {
                      (() => {
                        const usdata = loading ? localUserData : userData;
                        return usdata.firstName + " " + usdata.lastName;
                      })()
                    }
                  </div>
                  <span className="h6">{ loading ? "..." : "Programmer" }</span>
                </div>
              </div>

              <div className="col-6">
                User don't write about yourself
              </div>
            </div>
          </div>

          <div className="m-4">
            <div className="h5">User services</div>
            <div className="row row-cols-1 row-cols-md-3 g-4">
              <ServiceCardComponent name={ 123 } price={ 1000 } />
              <ServiceCardComponent name={ 123 } price={ 1000 } />
              <ServiceCardComponent name={ 123 } price={ 1000 } />
              <ServiceCardComponent name={ 123 } price={ 1000 } />
            </div>
          </div>

          <div className="m-4">
            <div className="h5">Reviews about user</div>
            ...
          </div>
        </div>
      </main>
    </>
  );
}
