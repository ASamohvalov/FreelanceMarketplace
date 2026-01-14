import { Route, Routes } from "react-router-dom";
import HomePage from "./views/pages/HomePage.jsx";
import SignInPage from "./views/pages/user/SignInPage.jsx";
import SignUpPage from "./views/pages/user/SignUpPage.jsx";
import ServicesPage from "./views/pages/services/ServicesPage.jsx";
import { BecomeFreelancerPage } from "./views/pages/user/BecomeFreelancerPage.jsx";
import PersonalAccountPage from "./views/pages/user/PersonalAccountPage.jsx";
import CreateServicePage from "./views/pages/services/CreateServicePage.jsx";

// base routing
export default function App() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/sign-in" element={<SignInPage />} />
      <Route path="/sign-up" element={<SignUpPage />} />

      <Route path="/become-freelancer" element={<BecomeFreelancerPage />} />
      <Route path="/personal-account" element={<PersonalAccountPage />} />

      <Route path="/services" element={<ServicesPage />} />
      <Route path="/service/create" element={<CreateServicePage />} />
    </Routes>
  );
}
