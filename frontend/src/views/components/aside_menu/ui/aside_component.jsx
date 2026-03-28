import React from "react";
import { NavLink } from "react-router-dom";
import "../styles/aside.css"

export const AsideComponent = ({ icon, title, to }) => {
    return <NavLink className={"d-flex mx-3 rounded-4 text-decoration-none gap-5 fs-5 p-2 items-center a text-white" + (({isActive}) => isActive ? "bg-white" : "")} to={to}>
            <span className="text-white">{icon}</span>
            <span className="text-white text-decoration-none">{title}</span>
    </NavLink>;
};