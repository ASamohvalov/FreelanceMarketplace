import { Route, Routes } from "react-router-dom";
import HomePage from "./views/pages/HomePage.jsx";
import SignInPage from "./views/pages/auth/SignInPage.jsx";
import SignUpPage from "./views/pages/auth/SignUpPage.jsx";
import ServicesPage from "./views/pages/services/ServicesPage.jsx";
import { BecomeFreelancer } from "./views/pages/auth/BecomeFreelancer.jsx";

// base routing
export default function App() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/sign-in" element={<SignInPage />} />
      <Route path="/sign-up" element={<SignUpPage />} />
      <Route path="/become-freelancer" element={<BecomeFreelancer />} />

      <Route path="/services" element={<ServicesPage />} />
    </Routes>
  );
}
