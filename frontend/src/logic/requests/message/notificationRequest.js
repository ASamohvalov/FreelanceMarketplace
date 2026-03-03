import { sendAuthGet, sendAuthPut } from "../requestSender";

export async function getAllPersonalNotificationsRequest() {
  return await sendAuthGet("notification/get_all_personal");
}

export async function getAllPersonalNotificationsWithHiddenRequest() {
  return await sendAuthGet("notification/get_all_personal_with_hide");
}

export async function hideNotificationRequest(id) {
  return await sendAuthPut(`notification/hide/${id}`);
}
