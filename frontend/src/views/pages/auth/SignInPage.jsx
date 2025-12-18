import { useEffect } from "react";
import SignInForm from "../../forms/auth/SignInForm"
import HeaderComponent from "../../components/HeaderComponent";

export default function SignInPage() {
  useEffect(() => {
    document.title = "Sign in";
  });

  return (
    <>
      <HeaderComponent />
      <div className="flex items-center justify-center h-screen">
        <div className="p-6 bg-gray-900 text-white rounded shadow-lg w-120">
          <div className="text-center text-xl">Sign in</div>
          <SignInForm />
        </div>
      </div>
    </>
  );
}
