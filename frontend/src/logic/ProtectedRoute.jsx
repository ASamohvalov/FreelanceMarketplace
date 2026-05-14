import { Navigate, Outlet } from "react-router-dom";
import { getUserData, hasRole } from "./jwt";

export default function ProtectedRoute({ roleRequired }) {
  if (Array.isArray(roleRequired)) {
    const currentRoles = getUserData()?.roles;
    if (currentRoles && roleRequired.find(role => currentRoles.includes(role))) {
      return <Outlet />;
    }

    return <Navigate to="/error?code=404" />;
  }

  if (hasRole(roleRequired)) {
    return <Outlet />;
  }

  return <Navigate to="/error?code=404" />;
}
