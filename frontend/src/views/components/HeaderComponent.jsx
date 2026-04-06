import { Link, useNavigate } from "react-router-dom";
import { getUserData, hasRole, isAuth, logout } from "../../logic/jwt";
import { logoutRequest } from "../../logic/requests/user/userRequest";
import "./css/header_component.css";
import { BadgePlus, Bell, BriefcaseBusiness, CirclePlus, Menu } from 'lucide-react';
import { useContext, useLayoutEffect, useRef, useState } from "react";
import { userContext } from "../../logic/store/userContext";
import { useEffect } from "react";

export default function HeaderComponent({ state }) {
    const [isHidden, setIsHidden] = state;
    const [user, setUser] = useContext(userContext);
    const [shrink, setShrink] = useState();
    const widthRef = useRef(null);
    const navigate = useNavigate();
    
    console.log(getUserData());
    
    useEffect(() => {
        setUser({ hasRole: hasRole("ROLE_FREELANCER"), isAuth: isAuth() });
    }, [setUser]);

    useLayoutEffect(()=>{
        const {width} = widthRef.current.getBoundingClientRect();
        setShrink(width < 650);
    }, [setShrink])
    
    async function onLogoutClick(event) {
        event.preventDefault();
        await logoutRequest();
        logout();
        navigate("/");
    }

    function authLinks() {
        if (isAuth()) {
            var user = getUserData();
            return (
                <>
                    <li className="nav-item">
                        <Link
                            to="/personal-account"
                            className="nav-link active"
                        >
                            {user.sub}
                        </Link>
                    </li>

                    <li className="nav-item">
                        <a
                            onClick={onLogoutClick}
                            href=""
                            className="nav-link active"
                        >
                            Выйти
                        </a>
                    </li>
                </>
            );
        }

        return (
            <>
                <li className="nav-item">
                    <Link className="nav-link active" to="/sign-in">
                        Войти
                    </Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link active" to="/sign-up">
                        Зарегистрироваться
                    </Link>
                </li>
            </>
        );
    }

    return (
        <header ref={widthRef}>
            <nav className="navbar navbar-custom bg-main-color navbar-expand-lg navbar-dark fixed-top">
                <div className="container-fluid d-flex justify-content-between align-items-center">
                    <ul className="navbar-nav flex-grow-1 flex-row gap-4 me-3">
                        <li className="nav-item ms-2 me-3 flex justify-content-center align-items-center" style={{color: "white"}}>
                                    <Menu onClick={()=>setIsHidden(!isHidden)} size={36}/>
                                </li>
                        {user && user?.hasRole && (
                            <>
                                <li className="nav-item d-flex gap-2 text-white align-items-center">
                                    <Bell size={24} />
                                    <Link
                                        className="nav-link active"
                                        to="/notifications"
                                    >
                                        {!shrink && "Уведомления"}
                                    </Link>
                                </li>
                                
                            </>
                        )}
                        {(() => {
                            if (getUserData()?.roles.includes("ROLE_FREELANCER")) {
                                return (
                                    <li className="nav-item d-flex gap-2 text-white align-items-center">
                                        <BadgePlus size={24} />
                                        <Link
                                            className="nav-link active"
                                            to="/create-service"
                                        >
                                            {!shrink && "Создать услугу"}
                                        </Link>
                                    </li>
                                );
                            } else if (user?.isAuth) {
                                return (
                                    <li className="nav-item d-flex gap-2 text-white align-items-center">
                                        <CirclePlus size={24} />
                                        <Link
                                            className="nav-link active"
                                            to="/become-freelancer"
                                        >
                                            {!shrink && "Стать фрилансером"}
                                        </Link>
                                    </li>
                                );
                            }
                        })()}
                    </ul>

                    <div className="flex-fill d-flex justify-content-end ">
                    
                        <BriefcaseBusiness size={40} color="white"></BriefcaseBusiness>
                    </div>

                    <ul className="navbar-nav flex-grow-1 gap-4 flex-row justify-content-end ms-3">{authLinks()}</ul>
                </div>
            </nav>
        </header>
    );
}
