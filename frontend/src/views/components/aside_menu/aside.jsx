import "./styles/aside.css";
import { BookText, CalendarArrowUp, HandPlatter, House, Megaphone, MessageCircle, UserRound, UserRoundKey } from "lucide-react";
import { AsideComponent } from "./ui/aside_component";
import { getUserData, hasRole, isAuth } from "../../../logic/jwt";
import { useContext } from "react";
import { userContext } from "../../../logic/store/userContext";
import { useEffect } from "react";

const links = [
  {
    icon: <UserRound size={32}></UserRound>,
    title: "Личный кабинет",
    to: "/personal-account",
  },
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
    icon: <Megaphone size={32}></Megaphone>,
    title: "Обратная связь",
    to: "/feedback/send",
  },
  {
    icon: <UserRoundKey size={32}></UserRoundKey>,
    title: "Административная панель",
    to: "/admin",
  },
  {
    icon: <UserRoundKey size={32}></UserRoundKey>,
    title: "Модеративная панель",
    to: "/moderator",
  },
];

export const Aside = ({ state }) => {
  const [user, setUser] = useContext(userContext);

  useEffect(() => {
    setUser({
      isFreelancer: hasRole("ROLE_FREELANCER"),
      isAuth: isAuth(),
      isAdmin: hasRole("ROLE_ADMIN"),
      isModerator: hasRole("ROLE_MODERATOR"),
    });
  }, [setUser]);

  const [isHidden] = state;
  return (
    <aside
      className={`aside overflow-hidden pt-5 text-white fw-semibold d-flex flex-column gap-4 position-fixed start-0 top-5 bg-orange ${isHidden ? "aside-visible" : "aside-hidden"}`}
    >
      {links.map((item, id) => {
        if (!user?.isAuth && item.title === "Сообщения") return;
        if (!user?.isAuth && item.title === "Отчёты") return;
        if (!user?.isAuth && item.title === "Заказы") return;
        if (!user?.isAuth && item.title === "Личный кабинет") return;
        if (item.title === "Обратная связь" && (user?.isModerator || user?.isAdmin || !user?.isAuth)) return;
        if (!user?.isFreelancer && item.title === "Мои услуги") return;
        if (!user?.isAdmin && item.title === "Административная панель") return;
        if (!user?.isModerator && item.title === "Модеративная панель") return;
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
