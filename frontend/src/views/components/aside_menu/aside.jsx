import React from "react";
import "./styles/aside.css"
import { Bell, HandPlatter, House, MessageCircle } from "lucide-react";
import { AsideComponent } from "./ui/aside_component";
import { isAuth } from "../../../logic/jwt";

const links = [
    {
        icon: <House size={32}></House>,
        title: "Домашняя",
        to: "/",
    },
    {
        icon: <HandPlatter size={32}></HandPlatter>,
        title: "Услуги",
        to: "/services",
    },
    {
        icon: <MessageCircle size={32}></MessageCircle>,
        title: "Сообщения",
        to: "/messages",
    }
]

export const Aside = ({ state }) => {
  const [isHidden,] = state;
  return <aside className={`aside overflow-hidden pt-5 z-3 text-white fw-semibold d-flex flex-column gap-4 position-fixed start-0 top-5 bg-orange ${isHidden ? "aside-visible" : "aside-hidden"}`}>
      {
          links.map((item, id) => {
              if(!isAuth() && item.title === "Сообщения") return;
              return <AsideComponent key={id} icon={item.icon} title={item.title} to={item.to} />
          })
    }
    </aside>;
}