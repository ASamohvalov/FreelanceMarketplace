import { getAllMessagesAfterDateRequest, getAllMessagesRequest } from "./requests/message/messageRequest"

export default async function getMessages(setMessages, conversationId, handleError) {
  const response = await getAllMessagesRequest(conversationId);
  if (response.status !== 200) {
    handleError();
    return;
  }
  setMessages(response.data);

  var lastSendTime = new Date(Date.now());
  return setInterval(async () => {
    console.log(lastSendTime);
    const res = await getAllMessagesAfterDateRequest(conversationId, lastSendTime.toISOString());
    if (res.status === 200) {
      lastSendTime = new Date(Date.now());
      setMessages(prev => [...prev, ...res.data]); // todo is read message
    }
  }, 5000) // every 5 seconds
}
