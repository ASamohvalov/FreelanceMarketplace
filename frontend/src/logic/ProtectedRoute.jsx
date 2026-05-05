import { Navigate, Outlet } from "react-router-dom";
import { hasRole } from "./jwt";

export default function ProtectedRoute({ roleRequired }) {
  if (hasRole(roleRequired)) {
    return <Outlet />
  }

  return <Navigate to="/error?code=404" />
}
