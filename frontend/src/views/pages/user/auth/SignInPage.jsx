import { useEffect } from "react";
import { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import HeaderComponent from "../../../components/HeaderComponent";
import { signInRequest } from "../../../../logic/requests/user/authRequest";
import { login } from "../../../../logic/jwt";
import { useRef } from "react";
import { FormWrapper } from "../../../components/elements/FormWrapper";
import "./css/sign_page.css";

export default function SignInPage() {
  const navigate = useNavigate();
  const email = useRef(null);
  const password = useRef(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    document.title = "Авторизация";
  });

  async function handleSubmit(event) {
    event.preventDefault();

    if (!email.current.value || !password.current.value) {
      setError("Все поля обязательны");
      return;
    }

    var response = await signInRequest(
      email.current.value,
      password.current.value,
    );
    if (response.status == 200) {
      login(response.data.accessToken, response.data.refreshToken);
      navigate("/");
      return;
    } else if (response.status == 400) {
      setError("Неверный email или пароль");
    }
  }

  return (
    <>
      <FormWrapper Title="Авторизация" error={error}>
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
          <div className="d-flex gap-3 justify-content-center">
            <button className="btn btn-main sign-form_submit" type="submit">
              Войти
            </button>
            <NavLink to="/sign-up" className="btn btn-outline-secondary">
              Зарегистрироваться
            </NavLink>
          </div>
        </form>
      </FormWrapper>
    </>
  );
}
