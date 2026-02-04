import { sendAuthGet, sendAuthPost } from "../requestSender";

export async function logoutRequest() {
  const refreshToken = localStorage.getItem("refreshToken");
  return await sendAuthPost("user/logout", { refreshToken: refreshToken });
}

export async function getInfoRequest() {
  return await sendAuthGet("user/get_info");
}
