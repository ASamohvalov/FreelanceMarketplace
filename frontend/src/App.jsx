import { Route, Routes } from "react-router-dom";
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
import AdminLayout from "./views/pages/AdminLayout.jsx";
import CreateServiceSuccessPage from "./views/pages/services/CreateServiceSuccessPage.jsx";
import { ChatOutlet } from "./views/pages/message/ChatOutlet.jsx";
import {
  getAllServicesRequest,
  getOwnServicesRequest,
} from "./logic/requests/service/serviceRequest.js";
import OrdersPage from "./views/pages/order/OrdersPage.jsx";
import SendOrderReportPage from "./views/pages/order/SendOrderReportPage.jsx";
import SendOrderReportSuccessPage from "./views/pages/order/SendOrderReportSuccessPage.jsx";
import OrderReportPage from "./views/pages/order/OrderReportPage.jsx";
import SendReviewPage from "./views/pages/review/SendReviewPage.jsx";
import OrderPage from "./views/pages/order/OrderPage.jsx";
import ProtectedRoute from "./logic/ProtectedRoute.jsx";
import AdminPanelUsersPage from "./views/pages/admin_panel/AdminPanelUsersPage.jsx";
import AdminPanelCategoriesPage from "./views/pages/admin_panel/AdminPanelCategoriesPage.jsx";
import MakeOrderPage from "./views/pages/order/MakeOrderPage.jsx";
import OrderRequirementPage from "./views/pages/order/OrderRequirementPage.jsx";
import ProfilePage from "./views/pages/user/ProfilePage.jsx";
import SendFeedbackPage from "./views/pages/feedback/SendFeedbackPage.jsx";
import AdminPanelJobTitlePage from "./views/pages/admin_panel/AdminPanelJobTitlesPage.jsx";
import AdminPanelFeedbackPage from "./views/pages/admin_panel/AdminPanelFeedbackPage.jsx";
import ModeratorLayout from "./views/pages/ModeratorLayout.jsx";
import AdminPanelOrdersPage from "./views/pages/admin_panel/AdminPanelOrdersPage.jsx";

// base routing
export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<HomePage />} />
        <Route path="error" element={<ErrorPage />} />
        <Route path="sign-in" element={<SignInPage />} />
        <Route path="sign-up" element={<SignUpPage />} />

        <Route element={<ProtectedRoute exitRole={"ROLE_FREELANCER"} />}>
          <Route path="become-freelancer" element={<BecomeFreelancerPage />} />
        </Route>
        <Route path="personal-account" element={<PersonalAccountPage />} />
        <Route path="profile/:userId" element={<ProfilePage />} />

        <Route
          path="services"
          element={<ServicesPage func={getAllServicesRequest} />}
        />
        <Route
          path="OwnServices"
          element={<ServicesPage func={getOwnServicesRequest} />}
        />
        <Route path="MyOrders" element={<OrdersPage />} />
        <Route path="create-service" element={<CreateServicePage />} />
        <Route
          path="update-service"
          element={<CreateServicePage isEdit={true} />}
        />
        <Route
          path="create-service/success"
          element={<CreateServiceSuccessPage />}
        />
        <Route
          path="update-service/success"
          element={<CreateServiceSuccessPage isEdit={true} />}
        />
        <Route path="service/:id" element={<ServicePage />} />

        <Route path="messages" element={<MessagesPage />}>
          <Route path=":conversationId" element={<ChatOutlet />} />
        </Route>
        <Route path="notifications" element={<NotificationsPage />} />

        <Route path="order/make/:serviceId" element={<MakeOrderPage />} />
        <Route path="pay/:serviceId" element={<PaymentPage />} />
        <Route path="order/success" element={<OrderSuccessPage />} />
        <Route path="order/report/send" element={<SendOrderReportPage />} />
        <Route
          path="order/report/send/success"
          element={<SendOrderReportSuccessPage />}
        />
        <Route path="order/reports" element={<OrderReportPage />} />
        <Route path="order/info/:id" element={<OrderPage />} />

        <Route path="review/send" element={<SendReviewPage />} />

        <Route
          path="order/requirement/:orderId"
          element={<OrderRequirementPage />}
        />

        <Route path="feedback/send" element={<SendFeedbackPage />} />
      </Route>

      <Route element={<ProtectedRoute roleRequired={"ROLE_ADMIN"} />}>
        <Route path="/admin" element={<AdminLayout />}>
          <Route index element={<AdminPanelUsersPage />} />
          <Route path="users" element={<AdminPanelUsersPage />} />
          <Route path="categories" element={<AdminPanelCategoriesPage />} />
          <Route path="jobTitles" element={<AdminPanelJobTitlePage />} />
          <Route path="feedback" element={<AdminPanelFeedbackPage />} />
          <Route path="orders" element={<AdminPanelOrdersPage />} />
        </Route>
      </Route>

      <Route element={<ProtectedRoute roleRequired={"ROLE_MODERATOR"} />}>
        <Route path="/moderator" element={<ModeratorLayout />}>
          <Route index element={<AdminPanelFeedbackPage />} />
          <Route path="feedback" element={<AdminPanelFeedbackPage />} />
          <Route path="orders" element={<AdminPanelOrdersPage />} />
        </Route>
      </Route>
    </Routes>
  );
}
