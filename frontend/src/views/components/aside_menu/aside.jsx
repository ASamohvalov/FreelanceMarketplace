import "./styles/aside.css";
import { HandPlatter, House, MessageCircle } from "lucide-react";
import { AsideComponent } from "./ui/aside_component";
import { getUserData, isAuth } from "../../../logic/jwt";

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
    icon: <HandPlatter size={32}></HandPlatter>,
    title: "Заказы",
    to: "/MyOrders",
  },
  {
    icon: <HandPlatter size={32}></HandPlatter>,
    title: "Мои услуги",
    to: "/OwnServices",
  },
  {
    icon: <MessageCircle size={32}></MessageCircle>,
    title: "Сообщения",
    to: "/messages",
  },
];

export const Aside = ({ state }) => {
  const isFreelancer = getUserData()?.roles.includes("ROLE_FREELANCER");

  const [isHidden] = state;
  return (
    <aside
      className={`aside overflow-hidden pt-5 z-3 text-white fw-semibold d-flex flex-column gap-4 position-fixed start-0 top-5 bg-orange ${isHidden ? "aside-visible" : "aside-hidden"}`}
    >
      {links.map((item, id) => {
        if (!isAuth() && item.title === "Сообщения") return;
        if (!isAuth() && item.title === "Заказы") return;
        if (!isFreelancer && item.title === "Мои услуги") return;
        return (
          <AsideComponent
            key={id}
            icon={item.icon}
            title={item.title}
            to={item.to}
          />
        );
      })}
    </aside>
  );
};
