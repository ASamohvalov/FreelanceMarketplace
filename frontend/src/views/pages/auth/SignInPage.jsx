import { useEffect } from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import HeaderComponent from "../../components/HeaderComponent";
import { signIn } from "../../../logic/requests/authRequest";

export default function SignInPage() {
  useEffect(() => {
    document.title = "Sign in";
  });
  var navigate = useNavigate();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);

  async function handleSubmit(event) {
    event.preventDefault();

    if (!email || !password) {
      setError('All fields are requerd');
      return;
    }

    var response = await signIn(email, password);
    if (response.ok) {
      navigate('/');
      return;
    }
    setError(response.data);
  }

  return (
    <>
      <HeaderComponent />
      <div className="flex flex-col items-center m-45">
        <div className={`bg-red-100 w-120 rounded shadow-lg p-6 mb-2 ${ error ? 'visible' : 'invisible' }`} >
          { error }
        </div>
        <div className="p-6 bg-gray-900 text-white rounded shadow-lg w-120">
          <div className="text-center text-xl">Sign in</div>
          <form onSubmit={ handleSubmit }>
            <div className="mb-2">
              <label htmlFor="email" className="block text-sm/6 font-medium text-white">Email</label>
              <div className="mt-2">
                <div className="flex items-center rounded-md bg-white/5 pl-3 outline-1 -outline-offset-1 outline-white/10 focus-within:outline-2 focus-within:-outline-offset-2 focus-within:outline-indigo-500">
                  <input id="email"
                    type="text"
                    name="email"
                    value={ email }
                    onChange={ (e) => setEmail(e.target.value) }
                    className="block min-w-0 grow bg-transparent py-1.5 pr-3 pl-1 text-base text-white placeholder:text-gray-500 focus:outline-none sm:text-sm/6" />
                </div>
              </div>
            </div>

            <div className="mb-2">
              <label htmlFor="password" className="block text-sm/6 font-medium text-white">Password</label>
              <div className="mt-2">
                <div className="flex items-center rounded-md bg-white/5 pl-3 outline-1 -outline-offset-1 outline-white/10 focus-within:outline-2 focus-within:-outline-offset-2 focus-within:outline-indigo-500">
                  <input id="password"
                    type="password"
                    name="password"
                    value={ password }
                    onChange={ (e) => setPassword(e.target.value) }
                    className="block min-w-0 grow bg-transparent py-1.5 pr-3 pl-1 text-base text-white placeholder:text-gray-500 focus:outline-none sm:text-sm/6" />
                </div>
              </div>
            </div>

            <div className="mt-6 flex items-center justify-end gap-x-6">
              <button type="button" className="text-sm/6 font-semibold text-white">
                Sign up
              </button>
              <button
                type="submit"
                className="rounded-md bg-indigo-500 px-3 py-2 text-sm font-semibold text-white focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-500"
              >
                Save
              </button>
            </div>
          </form>
        </div>
      </div>
    </>
  );
}
