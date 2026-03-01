import { sendAuthGet } from "../requestSender";

export async function getAllPersonalNotificationsRequest() {
  return await sendAuthGet("notification/get_all_personal");
}
