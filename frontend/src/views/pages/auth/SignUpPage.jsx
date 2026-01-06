import { useRef, useState } from "react";
import { useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { signInRequest, signUpRequest } from "../../../logic/requests/user/authRequest";
import HeaderComponent from "../../components/HeaderComponent";
import { login } from "../../../logic/jwt";

export default function SignUpPage() {
  useEffect(() => {
    document.title = "Sign up";
  }, []);
  const navigate = useNavigate();

  const email = useRef(null);
  const password = useRef(null);
  const firstName = useRef(null);
  const lastName = useRef(null);

  const [isFreelancer, setIsFreelancer] = useState(false);
  const [error, setError] = useState(null);

  async function handleSubmit(event) {
    event.preventDefault();

    if (
      !email.current.value ||
      !password.current.value ||
      !firstName.current.value ||
      !lastName.current.value
    ) {
      setError("All fields are requerd");
      return;
    }

    const regResponse = await signUpRequest(
      email.current.value,
      password.current.value,
      firstName.current.value,
      lastName.current.value,
    );

    if (regResponse.status == 200) {
      const loginResponse = await signInRequest(
        email.current.value,
        password.current.value,
      );
      if (loginResponse.status != 200) {
        console.log("[ERROR] logic error");
        return;
      }
      login(loginResponse.data.accessToken, loginResponse.data.refreshToken);

      if (isFreelancer) {
        navigate("/become-freelancer");
        return;
      }

      navigate("/");
      return;
    }
    setError("this email already taken");
  }

  return (
    <>
      <HeaderComponent />
      <div className="mx-auto" style={{ width: "500px", marginTop: "130px" }}>
        <div
          className={`mb-2 bg-danger p-4 border border-danger rounded shadow ${error ? "visible" : "invisible"}`}
        >
          {error}
        </div>
        <div className="shadow w-100 bg-dark rounded p-4 text-light">
          <div className="text-center mb-3 h4">Sign up</div>
          <form onSubmit={handleSubmit} className="mb-4">
            <label htmlFor="email">Email</label>
            <input
              className="form-control mb-3"
              id="email"
              type="email"
              ref={ email }
            />

            <label htmlFor="password">Password</label>
            <input
              className="form-control mb-3"
              id="password"
              type="password"
              ref={ password }
            />

            <label htmlFor="firstName">First name</label>
            <input
              className="form-control mb-3"
              id="firstName"
              type="text"
              ref={ firstName }
            />

            <label htmlFor="firstName">Last name</label>
            <input
              className="form-control mb-3"
              id="lastName"
              type="text"
              ref={ lastName }
            />

            <div className="mb-3">
              <input
                type="radio"
                className="btn-check"
                name="options-base"
                id="buyer-input"
                checked={ !isFreelancer }
                onChange={ () => setIsFreelancer(false) }
              />
              <label className="btn text-light" htmlFor="buyer-input">
                Buyer
              </label>

              <input
                type="radio"
                className="btn-check"
                name="options-base"
                id="freelancer-input"
                checked={ isFreelancer }
                onChange={ () => setIsFreelancer(true) }
              />
              <label className="btn text-light" htmlFor="freelancer-input">
                Freelancer
              </label>
            </div>

            <button className="btn btn-primary" type="submit">
              Submit
            </button>
          </form>
          <span>
            Already have an account - <Link to="/sign-in">Login</Link>
          </span>
        </div>
      </div>
    </>
  );
}
