import { Outlet } from "react-router-dom";
import { Aside } from "../components/aside_menu/aside";
import { useState } from "react";
import HeaderComponent from "../components/HeaderComponent";
import FooterComponent from "../components/FooterComponent";
import { userContext } from "../../logic/store/userContext";

export default function Layout() {
  const [isHidden, setIsHidden] = useState(false);
  const [user, setUser] = useState(null);

  return (
    <>
      <userContext.Provider value={[user, setUser]}>
        <HeaderComponent state={[isHidden, setIsHidden]} />
        <Aside state={[isHidden, setIsHidden]} />
        <Outlet />
        <FooterComponent />
      </userContext.Provider>
    </>
  );
}
