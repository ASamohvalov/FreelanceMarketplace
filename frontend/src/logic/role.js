export function getRuRole(role) {
  switch (role) {
    case "ROLE_USER":
      return "Пользователь";

    case "ROLE_FREELANCER":
      return "Фрилансер";

    case "ROLE_ADMIN":
      return "Администратор";

    case "ROLE_MODERATOR":
      return "Модератор";
  }
}
