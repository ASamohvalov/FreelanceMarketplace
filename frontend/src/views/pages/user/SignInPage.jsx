import { useEffect } from "react";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import HeaderComponent from "../../components/HeaderComponent";
import { signInRequest } from "../../../logic/requests/user/authRequest";
import { login } from "../../../logic/jwt";
import { useRef } from "react";

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

    var response = await signInRequest(email.current.value, password.current.value);
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
      <div className="mx-auto" style={{ width: "500px", marginTop: "200px" }}>
        <div
          className={`mb-2 bg-danger p-4 border border-danger rounded shadow ${error ? "visible" : "invisible"}`}
        >
          { error }
        </div>
        <div className="shadow w-100 bg-dark rounded p-4 text-light">
          <div className="text-center mb-3 h4">Sign in</div>
          <form onSubmit={ handleSubmit } className="mb-4">
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
            <button className="btn btn-primary" type="submit">
              Submit
            </button>
          </form>
          <span>
            Don't have an account - <Link to="/sign-up">Register</Link>
          </span>
        </div>
      </div>
    </>
  );
}
