import { Navigate, Outlet } from "react-router-dom";
import { getUserData, hasRole } from "./jwt";

export default function ProtectedRoute({ roleRequired=null, exitRole=null }) {
  if (Array.isArray(roleRequired)) {
    const currentRoles = getUserData()?.roles;
    if (currentRoles && roleRequired.find(role => currentRoles.includes(role))) {
      return <Outlet />;
    }

    return <Navigate to="/error?code=404" />;
  }

  if (roleRequired && hasRole(roleRequired)) {
    return <Outlet />;
  }

  if (exitRole && !hasRole(exitRole)) {
    return <Outlet />;
  }

  return <Navigate to="/error?code=404" />;
}
