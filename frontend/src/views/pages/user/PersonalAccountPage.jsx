import { useEffect } from "react";
import HeaderComponent from "../../components/HeaderComponent";
import ServiceCardComponent from "../../components/ServiceCardComponent";
import { isAuth } from "../../../logic/jwt";
import { useNavigate } from "react-router-dom";

export default function PersonalAccountPage() {
  useEffect(() => {
    document.title = "Personal Account";
  }, [])

  const navigate = useNavigate();

  // if user dont have tokens in localstorage
  if (!isAuth()) {
    navigate("/");
    return;
  }

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
                  <div className="h4 mx-2">Ivan James</div>
                  <span className="h6">Programmer</span>
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
