import { LucideHotel } from "lucide-react";
import { sendAuthDelete, sendAuthGet, sendAuthPatch, sendAuthPost, sendAuthPut } from "../requestSender";

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
export async function sendMessageUpdateRequest(messageId, message) {
  return await sendAuthPut("messaging/message/edit", {
    id: messageId,
    message: message
  });
}

export async function sendReadMessageRequest(messageIds) {
  return await sendAuthPatch("messaging/message/read", messageIds);
}

export async function getConversationContextInfo(conversationId) {
  return await sendAuthGet(`messaging/conversation/${conversationId}/context`);
}

export async function sendDeleteMessageRequest(messageId) {
  return await sendAuthDelete(`messaging/message/delete/${messageId}`);
}

export async function sendEditMessageRequest(messageId, text) {
  return await sendAuthPut("messaging/message/edit", {
    id: messageId,
    message: text
  });
}

export async function getMessageEventsRequest(conversationId, lastEventId=0) {
  return await sendAuthGet("messaging/event/get", {
    conversationId: conversationId,
    lastEventId: lastEventId,
  });
}
