import { useState } from "react";
import { useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { signInRequest, signUpRequest } from "../../../logic/requests/user/authRequest";
import HeaderComponent from "../../components/HeaderComponent";
import { login } from "../../../logic/jwt";

export default function SignUpPage() {
  useEffect(() => {
    document.title = "Sign up";
  });
  var navigate = useNavigate();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [error, setError] = useState(null);

  async function handleSubmit(event) {
    event.preventDefault();

    if (!email || !password) {
      setError("All fields are requerd");
      return;
    }

    var regResponse = await signUpRequest(email, password, firstName, lastName);
    if (regResponse.status == 200) {
      var loginResponse = await signInRequest(email, password);
      if (loginResponse.status != 200) {
        console.log("[ERROR] logic error");
        return;
      }
      login(loginResponse.data.accessToken, loginResponse.data.refreshToken);
      navigate('/');
      return;
    }
    setError("incorrect email or password");
  }

  return (
    <>
      <HeaderComponent />
      <div className="mx-auto" style={{ width: "500px", marginTop: "200px" }}>
        <div className={`mb-2 bg-danger p-4 border border-danger rounded shadow ${ error ? 'visible' : 'invisible' }`}>
          { error }
        </div>
        <div className="shadow w-100 bg-dark rounded p-4 text-light">
          <div className="text-center mb-3 h4">Sign up</div>
          <form onSubmit={ handleSubmit } className="mb-4">
            <label htmlFor="email">Email</label>
            <input className="form-control mb-3" id="email" type="email" value={ email } onChange={ (e) => setEmail(e.target.value) } />

            <label htmlFor="password">Password</label>
            <input className="form-control mb-3" id="password" type="password" value={ password } onChange={ (e) => setPassword(e.target.value) } />

            <label htmlFor="firstName">First name</label>
            <input className="form-control mb-3" id="firstName" type="text" value={ firstName } onChange={ (e) => setFirstName(e.target.value) } />

            <label htmlFor="firstName">Last name</label>
            <input className="form-control mb-3" id="lastName" type="text" value={ lastName } onChange={ (e) => setLastName(e.target.value) } />

            <button className="btn btn-primary" type="submit">Submit</button>
          </form>
          <span>Already have an account - <Link to="/sign-in">Login</Link></span>
        </div>
      </div>
    </>
  );
}
