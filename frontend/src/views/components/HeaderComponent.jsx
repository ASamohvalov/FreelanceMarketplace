import { useNavigate } from "react-router-dom";
import { getUserData, isAuth, logout } from "../../logic/jwt";

export default function HeaderComponent() {
  var navigate = useNavigate();

  async function onLogoutClick(event) {
    event.preventDefault();
    await logout();
    window.location.reload();
  }

  function authLinks() {
    if (isAuth()) {
      var user = getUserData();
      return (
        <>
          <a onClick={ () => navigate('/sign-in') }
            href=""
            className="text-sm/6 font-semibold text-white mx-3">{ user.sub }
          </a>

          <a onClick={ onLogoutClick }
            href=""
            className="text-sm/6 font-semibold text-white">logout
          </a>
        </>
      );
    }

    return (
      <a onClick={ () => navigate('/sign-in') }
        href=""
        className="text-sm/6 font-semibold text-white">Log in<span aria-hidden="true">&rarr;</span>
      </a>
    )
  }

  return (
    <header className="bg-gray-900">
      <nav aria-label="Global" className="mx-auto flex max-w-7xl items-center justify-between p-6 lg:px-8">
        <div className="flex lg:flex-1">
          <a href="#" className="-m-1.5 p-1.5">
            <span className="sr-only">Your Company</span>
            <img src="https://tailwindcss.com/plus-assets/img/logos/mark.svg?color=indigo&shade=500" alt="" className="h-8 w-auto" />
          </a>
        </div>
        <div className="flex lg:hidden">
          <button type="button" command="show-modal" commandfor="mobile-menu" className="-m-2.5 inline-flex items-center justify-center rounded-md p-2.5 text-gray-400">
            <span className="sr-only">Open main menu</span>
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5" data-slot="icon" aria-hidden="true" className="size-6">
              <path d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" strokeLinecap="round" strokeLinejoin="round" />
            </svg>
          </button>
        </div>
        <el-popover-group className="hidden lg:flex lg:gap-x-12">
          <a onClick={ () => navigate('/') }
            href=""
            className="text-sm/6 font-semibold text-white">Home</a>
          <a href="#" className="text-sm/6 font-semibold text-white">Marketplace</a>
          <a href="#" className="text-sm/6 font-semibold text-white">Company</a>
        </el-popover-group>
        <div className="hidden lg:flex lg:flex-1 lg:justify-end">
          { authLinks() }
        </div>
      </nav>
    </header>
  );
}
