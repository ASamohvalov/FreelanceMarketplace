import { sendAuthGet, sendAuthPost, sendAuthPut } from "../requestSender";

export async function getAllConversationsRequest() {
  return await sendAuthGet("messaging/conversation/personal/get_all");
}

export async function getAllMessagesRequest(conversationId) {
  return await sendAuthGet(`messaging/message/get/${conversationId}`);
}

export async function getAllMessagesAfterDateRequest(conversationId, date) {
  return await sendAuthGet(`messaging/message/get/${conversationId}`, {
    after: date
  });
}

export async function sendMessageRequest(conversationId, message) {
  return await sendAuthPost("messaging/send", {
    conversationId: conversationId,
    message: message,
  });
}

export async function sendReadMessageRequest(messageIds) {
  return await sendAuthPut("messaging/message/read", messageIds);
}

export async function getConversationContextInfo(conversationId) {
  return await sendAuthGet(`messaging/conversation/${conversationId}/context`);
}
