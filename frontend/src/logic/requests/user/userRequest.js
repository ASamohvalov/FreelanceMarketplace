import { sendAuthPost } from "../requestSender";

export async function logoutRequest() {
  return await sendAuthPost("/user/logout");
}
