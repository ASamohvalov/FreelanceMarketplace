import { useEffect } from "react";
import { useState } from "react";
import { Link, NavLink, useNavigate } from "react-router-dom";
import HeaderComponent from "../../components/HeaderComponent";
import { signInRequest } from "../../../logic/requests/user/authRequest";
import { login } from "../../../logic/jwt";
import { useRef } from "react";
import "./css/sign_page.css";
import { FormWrapper } from "../../components/elements/FormWrapper";

export default function SignInPage() {
  useEffect(() => {
    document.title = "Sign in";
  });
  const navigate = useNavigate();

  const email = useRef(null);
  const password = useRef(null);

  const [error, setError] = useState(null);

  async function handleSubmit(event) {
    event.preventDefault();

    if (!email.current.value || !password.current.value) {
      setError("All fields are requerd");
      return;
    }

    var response = await signInRequest(
      email.current.value,
      password.current.value,
    );
    if (response.status == 200) {
      console.log(response);
      login(response.data.accessToken, response.data.refreshToken);
      navigate("/");
      return;
    } else if (response.status == 400) {
      setError("invalid email or password");
    }
  }

  return (
    <>
      <HeaderComponent />
      <FormWrapper Title="Sign in" error={error}>
        <form onSubmit={handleSubmit} className="mb-4 sign-form">
          <label htmlFor="email sign-form_input-label">Email</label>
          <input
            className="form-control mb-3 sign-form_input"
            id="email"
            type="email"
            ref={email}
          />
          <label htmlFor="password sign-form_input-label">Password</label>
          <input
            className="form-control mb-3 sign-form_input"
            id="password"
            type="password"
            ref={password}
          />
          <div className="d-flex gap-3 justify-content-center">
            <button className="btn btn-primary sign-form_submit" type="submit">
              Submit
            </button>
            <NavLink to="/sign-up" className="btn btn-outline-secondary">
              Register
            </NavLink>
          </div>
        </form>
      </FormWrapper>
    </>
  );
}
