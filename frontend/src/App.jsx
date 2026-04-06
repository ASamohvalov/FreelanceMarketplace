import { Route, Routes, useParams } from "react-router-dom";
import HomePage from "./views/pages/HomePage.jsx";
import SignInPage from "./views/pages/user/auth/SignInPage.jsx";
import SignUpPage from "./views/pages/user/auth/SignUpPage.jsx";
import ServicesPage from "./views/pages/services/ServicesPage.jsx";
import { BecomeFreelancerPage } from "./views/pages/user/BecomeFreelancerPage.jsx";
import PersonalAccountPage from "./views/pages/user/PersonalAccountPage.jsx";
import CreateServicePage from "./views/pages/services/CreateServicePage.jsx";
import ErrorPage from "./views/pages/error/ErrorPage.jsx";
import ServicePage from "./views/pages/services/ServicePage.jsx";
import MessagesPage from "./views/pages/message/MessagesPage.jsx";
import NotificationsPage from "./views/pages/message/NotificationsPage.jsx";
import PaymentPage from "./views/pages/order/PaymentPage.jsx";
import OrderSuccessPage from "./views/pages/order/OrderSuccessPage.jsx";
import Layout from "./views/pages/Layout.jsx";
import CreateServiceSuccessPage from "./views/pages/services/CreateServiceSuccessPage.jsx";
import MessagesComponent from "./views/components/messages/MessagesComponent.jsx";
import ConversationContextInfo from "./views/components/order/ConversationContextInfo.jsx";
import { ChatOutlet } from "./views/pages/message/ChatOutlet.jsx";
import OwnServicesPage from "./views/pages/order/OrdersPage.jsx";
import { getAllServicesRequest, getOwnServicesRequest } from "./logic/requests/service/serviceRequest.js";
import OrdersPage from "./views/pages/order/OrdersPage.jsx";

// base routing
export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<HomePage />} />
        <Route path="error" element={<ErrorPage />} />
        <Route path="sign-in" element={<SignInPage />} />
        <Route path="sign-up" element={<SignUpPage />} />

        <Route path="become-freelancer" element={<BecomeFreelancerPage />} />
        <Route path="personal-account" element={<PersonalAccountPage />} />

        <Route path="services" element={<ServicesPage func={getAllServicesRequest}/>} />
        <Route path="OwnServices" element={<ServicesPage func={getOwnServicesRequest}/>} />
        <Route path="MyOrders" element={<OrdersPage/>} />
        <Route path="create-service" element={<CreateServicePage />} />
        <Route path="create-service/success" element={<CreateServiceSuccessPage />} />
        <Route path="service/:id" element={<ServicePage />} />

              <Route path="messages" element={<MessagesPage />} >
                  <Route path=":conversationId" element={<ChatOutlet/>} />
              </Route>
        <Route path="notifications" element={<NotificationsPage />} />

        <Route path="pay/:serviceId" element={<PaymentPage />} />
        <Route path="order/success" element={<OrderSuccessPage />} />
      </Route>
    </Routes>
  );
}
