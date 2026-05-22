import { useContext, useEffect } from "react";
import { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { signInRequest } from "../../../../logic/requests/user/authRequest";
import { hasRole, isAuth, login } from "../../../../logic/jwt";
import { useRef } from "react";
import { FormWrapper } from "../../../components/elements/FormWrapper";
import "./css/sign_page.css";
import { userContext } from "../../../../logic/store/userContext";

export default function SignInPage() {
  const navigate = useNavigate();
  const email = useRef(null);
  const password = useRef(null);
  const [error, setError] = useState(null);

  const [_, setUser] = useContext(userContext);
  const [submitBtnDisabled, setSubmitBtnDisabled] = useState(false);

  useEffect(() => {
    document.title = "Авторизация";
  });

  async function handleSubmit(event) {
    event.preventDefault();

    if (!email.current.value || !password.current.value) {
      setError("Все поля обязательны");
      return;
    }

    setSubmitBtnDisabled(true);

    var response = await signInRequest(
      email.current.value,
      password.current.value,
    );
    if (response.status == 200) {
      login(response.data.accessToken, response.data.refreshToken);
      setUser({
        isFreelancer: hasRole("ROLE_FREELANCER"),
        isAuth: isAuth(),
        isAdmin: hasRole("ROLE_ADMIN"),
        isModerator: hasRole("ROLE_MODERATOR"),
      });
      navigate("/");
      return;
    } else if (response.status == 400) {
      setError("Неверный email или пароль");
    }
    setSubmitBtnDisabled(false);
  }

  return (
    <>
      <FormWrapper Title="Авторизация" error={error}>
        <form onSubmit={handleSubmit} className="mb-4 sign-form">
          <label htmlFor="email">Почта</label>
          <input
            className="form-control mb-3"
            id="email"
            type="email"
            ref={email}
          />
          <label htmlFor="password">Пароль</label>
          <input
            className="form-control mb-4"
            id="password"
            type="password"
            ref={password}
          />
          <div className="d-flex gap-3 justify-content-center">
            <button
              className="w-100 btn btn-main sign-form_submit"
              type="submit"
              disabled={submitBtnDisabled}
            >
              Войти
            </button>
            <NavLink to="/sign-up" className="w-100 btn btn-outline-secondary">
              Зарегистрироваться
            </NavLink>
          </div>
        </form>
      </FormWrapper>
    </>
  );
}
