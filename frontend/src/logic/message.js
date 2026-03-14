import { getAllMessagesAfterDateRequest, getAllMessagesRequest } from "./requests/message/messageRequest"

export default async function getMessages(setMessages, conversationId, handleError) {
  const response = await getAllMessagesRequest(conversationId);
  if (response.status !== 200) {
    handleError();
    return;
  }
  await setMessages(response.data);

  var lastSendTime = new Date(Date.now());
  return setInterval(async () => {
    const res = await getAllMessagesAfterDateRequest(conversationId, lastSendTime.toISOString());
    if (res.status === 200) {
      lastSendTime = new Date(Date.now());
      await setMessages(prev => [...prev, ...res.data]);
    }
  }, 5000) // every 5 seconds
}
