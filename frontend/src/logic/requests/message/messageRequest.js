import { sendAuthGet, sendAuthPost } from "../requestSender";

export async function getAllConversationsRequest() {
  return await sendAuthGet("messaging/conversation/get_all");
}

export async function getAllMessagesRequest(conversationId) {
  return await sendAuthGet(`messaging/get_messages/${conversationId}`);
}

export async function sendMessageRequest(conversationId, message) {
  return await sendAuthPost("messaging/send", {
    conversationId: conversationId,
    message: message,
  });
}
