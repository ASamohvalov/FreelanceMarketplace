import "./styles/aside.css";
import { BookText, CalendarArrowUp, HandPlatter, House, MessageCircle, UserRound, UserRoundKey } from "lucide-react";
import { AsideComponent } from "./ui/aside_component";
import { getUserData, hasRole, isAuth } from "../../../logic/jwt";

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
    icon: <CalendarArrowUp size={32}></CalendarArrowUp>,
    title: "Заказы",
    to: "/MyOrders",
  },
  {
    icon: <HandPlatter size={32}></HandPlatter>,
    title: "Мои услуги",
    to: "/OwnServices",
  },
  {
    icon: <BookText size={32}></BookText>,
    title: "Отчёты",
    to: "/order/reports",
  },
  {
    icon: <MessageCircle size={32}></MessageCircle>,
    title: "Сообщения",
    to: "/messages",
  },
  {
    icon: <UserRoundKey size={32}></UserRoundKey>,
    title: "Административная панель",
    to: "/admin",
  },
  {
    icon: <UserRoundKey size={32}></UserRoundKey>,
    title: "Личный кабинет",
    to: "/personal-account",
  },
];

export const Aside = ({ state }) => {
  const isFreelancer = getUserData()?.roles.includes("ROLE_FREELANCER");

  const [isHidden] = state;
  return (
    <aside
      className={`aside overflow-hidden pt-5 text-white fw-semibold d-flex flex-column gap-4 position-fixed start-0 top-5 bg-orange ${isHidden ? "aside-visible" : "aside-hidden"}`}
    >
      {links.map((item, id) => {
        if (!isAuth() && item.title === "Сообщения") return;
        if (!isAuth() && item.title === "Отчёты") return;
        if (!isAuth() && item.title === "Заказы") return;
        if (!isFreelancer && item.title === "Мои услуги") return;
        if (!hasRole("ROLE_ADMIN") && item.title === "Административная панель") return;
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
