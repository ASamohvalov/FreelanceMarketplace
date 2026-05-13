import { sendAuthGet, sendAuthPatch, sendAuthPost } from "./requestSender";

export async function sendFeedbackRequest(text, type, title) {
  return await sendAuthPost("feedback/send", {
    text: text,
    type: type,
    title: title,
  })
}

export async function getFeedbacksRequest() {
  return await sendAuthGet("feedback/get");
}

export async function getFeedbackByIdRequest(id) {
  return await sendAuthGet("feedback/get/" + id);
}

export async function acceptFeedbackRequest(id) {
  return await sendAuthPatch("feedback/accept/" + id);
}
