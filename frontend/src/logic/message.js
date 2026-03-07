import { getAllMessagesAfterDateRequest, getAllMessagesRequest } from "./requests/message/messageRequest"

export default async function getMessages(setMessages, conversationId, handleError) {
  const response = await getAllMessagesRequest(conversationId);
  if (response.status !== 200) {
    handleError();
    return;
  }
  setMessages(response.data);

  // var lastSendTime = Date.now();
  // console.log(lastSendTime);
  // const interval = setInterval(async () => {
  //   console.log(lastSendTime);
  //   const res = await getAllMessagesAfterDateRequest(conversationId, lastSendTime);
  //   if (res === 200) {
  //     lastSendTime = Date.now();
  //     setMessages(prev => [...prev, ...res.data]);

  //   }
  // }, 5000) // every 5 seconds
}
