import { Route, Routes } from "react-router-dom";
import HomePage from "./views/pages/HomePage.jsx";
import SignInPage from "./views/pages/auth/SignInPage.jsx";
import SignUpPage from "./views/pages/auth/SignUpPage.jsx";

// base routing
export default function App() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/sign-in" element={<SignInPage />} />
      <Route path="/sign-up" element={<SignUpPage />} />
    </Routes>
  );
}
