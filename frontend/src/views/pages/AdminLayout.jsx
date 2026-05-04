import { Outlet } from "react-router-dom";
import AdminAsideComponent from "../components/admin_panel/AdminAsideComponent";

export default function AdminLayout() {
  return (
    <>
      <div className="d-flex">
        <AdminAsideComponent />
        <Outlet />
      </div>
    </>
  );
}
