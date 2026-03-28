import { Link } from "react-router-dom";
import { getUserData, hasRole, isAuth, logout } from "../../logic/jwt";
import { logoutRequest } from "../../logic/requests/user/userRequest";
import "./css/header_component.css";
import { Badge, BadgePlus, Bell, BriefcaseBusiness, CirclePlus, Menu } from 'lucide-react';
import { useContext } from "react";
import { userContext } from "../../logic/store/userContext";
import { useState } from "react";

export default function HeaderComponent({ state }) {
    const [isHidden, setIsHidden] = state;
    const [user, setUser] = useContext(userContext);
    
    async function onLogoutClick(event) {
        event.preventDefault();
        await logoutRequest();
        logout();
        window.location.reload();
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
        <header>
            <nav className="navbar navbar-custom bg-main-color navbar-expand-lg navbar-dark fixed-top">
                <div className="container-fluid d-flex justify-content-between align-items-center">
                    <ul className="navbar-nav w-50 flex-row gap-4 me-3">
                        <li className="nav-item ms-2 me-3 flex justify-content-center align-items-center" style={{color: "white"}}>
                                    <Menu onClick={()=>setIsHidden(!isHidden)} size={36}/>
                                </li>
                        {user?.hasRole && (
                            <>
                                <li className="nav-item d-flex gap-2 text-white align-items-center">
                                    <Bell size={24} />
                                    <Link
                                        className="nav-link active"
                                        to="/notifications"
                                    >
                                        Уведомления
                                    </Link>
                                </li>
                                
                            </>
                        )}
                        {(() => {
                            if (user?.hasRole) {
                                return (
                                    <li className="nav-item d-flex gap-2 text-white align-items-center">
                                        <BadgePlus size={24} />
                                        <Link
                                            className="nav-link active"
                                            to="/create-service"
                                        >
                                            Создать услугу
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
                                            Стать фрилансером
                                        </Link>
                                    </li>
                                );
                            }
                        })()}
                    </ul>

                    <div className="flex-grow-1 d-flex justify-content-start ">
                    
                        <BriefcaseBusiness size={40} color="white"></BriefcaseBusiness>
                    </div>

                    <ul className="navbar-nav flex-fill gap-4 flex-row justify-content-end ms-3">{authLinks()}</ul>
                </div>
            </nav>
        </header>
    );
}
