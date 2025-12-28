import { sendAuthPost } from "../requestSender";

export async function logoutRequest() {
  var refreshToken = localStorage.getItem("refreshToken");
  return await sendAuthPost("user/logout", { refreshToken: refreshToken });
}
