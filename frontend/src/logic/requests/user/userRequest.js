import { sendPost } from "../requestSender";

export async function logoutRequest() {
  await sendPost();
}
