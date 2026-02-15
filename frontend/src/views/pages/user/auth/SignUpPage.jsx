import { useRef, useState } from "react";
import { useEffect } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import {
  signInRequest,
  signUpRequest,
} from "../../../../logic/requests/user/authRequest";
import HeaderComponent from "../../../components/HeaderComponent";
import { login } from "../../../../logic/jwt";
import { FormWrapper } from "../../../components/elements/FormWrapper";
import "./css/sign_page.css";

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
      <FormWrapper Title="Sign up" error={error}>
        <form onSubmit={handleSubmit} className="mb-4 sign-form">
          <label htmlFor="email">Email</label>
          <input
            className="form-control mb-3"
            id="email"
            type="email"
            ref={email}
          />

          <label htmlFor="password">Password</label>
          <input
            className="form-control mb-3"
            id="password"
            type="password"
            ref={password}
          />

          <label htmlFor="firstName">First name</label>
          <input
            className="form-control mb-3"
            id="firstName"
            type="text"
            ref={firstName}
          />

          <label htmlFor="firstName ">Last name</label>
          <input
            className="form-control mb-3"
            id="lastName"
            type="text"
            ref={lastName}
          />

          <div className="mb-3 w-100 px-3 flex gap-3">
            <input
              type="radio"
              className="btn-check"
              name="options-base"
              id="buyer-input"
              checked={!isFreelancer}
              onChange={() => setIsFreelancer(false)}
            />
            <label className="btn text-dark w-100" htmlFor="buyer-input">
              Buyer
            </label>

            <input
              type="radio"
              className="btn-check"
              name="options-base"
              id="freelancer-input"
              checked={isFreelancer}
              onChange={() => setIsFreelancer(true)}
            />
            <label className="btn text-dark w-100" htmlFor="freelancer-input">
              Freelancer
            </label>
          </div>

          <div className="d-flex gap-3 justify-content-center">
            <button className="btn btn-primary sign-form_submit" type="submit">
              Submit
            </button>
            <NavLink to="/sign-in" className="btn btn-outline-secondary">
              Login
            </NavLink>
          </div>
        </form>
      </FormWrapper>
    </>
  );
}
