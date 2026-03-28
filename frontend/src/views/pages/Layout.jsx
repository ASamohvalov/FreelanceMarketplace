import { Outlet } from "react-router-dom";
import { Aside } from "../components/aside_menu/aside";
import { useEffect, useState } from "react";
import HeaderComponent from "../components/HeaderComponent";
import FooterComponent from "../components/FooterComponent";
import { userContext } from "../../logic/store/userContext";
import { getUserData } from "../../logic/jwt";


export default function Layout() {
    const [isHidden, setIsHidden] = useState(false);
    const [user, setUser] = useState(null);
    useEffect(() => {
        console.log(getUserData());
    }, [])
    return (
        <>
            <userContext.Provider value={[user, setUser]}>
            <HeaderComponent state={[isHidden, setIsHidden]}/>
            <Aside state={[isHidden, setIsHidden]} />
            <Outlet />
                <FooterComponent />
            </userContext.Provider>
        </>
    );
}