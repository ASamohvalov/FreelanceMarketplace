import { Outlet } from "react-router-dom";
import ModeratorAsideComponent from "../components/admin_panel/moderator/ModeratorAsideComponent";

export default function ModeratorLayout() {
  return (
    <>
      <div className="d-flex">
        <ModeratorAsideComponent />
        <Outlet />
      </div>
    </>
  );
}
