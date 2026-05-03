import {
  getAllMessagesRequest,
  getMessageEventsRequest,
} from "./requests/message/messageRequest";

var lastEventId = 0;

export async function pool(exitRef, setMessages, conversationId, handleError) {
  await getMessages(setMessages, conversationId, handleError);

  while (exitRef.current) {
    await getMessageEvents(setMessages, conversationId, handleError);
    console.log("data received, new iteration");
  }
}

async function getMessages(setMessages, conversationId, handleError) {
  const response = await getAllMessagesRequest(conversationId);
  if (response.status !== 200) {
    handleError();
    return;
  }
  setMessages(response.data.messages);

  lastEventId = response.data.lastEventId;
}


// отправка запроса на получение событий
// сервер ждет 30 секунд для получения события
// при отправки события собеседником, сервер возвращает его
async function getMessageEvents(setMessages, conversationId, handleError) {
  const response = await getMessageEventsRequest(conversationId, lastEventId)
  if (response.status !== 200) {
    handleError();
    return;
  }
  lastEventId = response.data.length !== 0
    ? response.data[response.data.length - 1].id
    : lastEventId;

  setMessages((prevMessages) => {
    let messageList = [...prevMessages];

    response.data.forEach((event) => {
      switch (event.type) {
        case "NEW_MESSAGE":
          messageList.push(event.message);
          break;

        case "EDIT_MESSAGE":
          messageList = messageList.map((m) =>
            m.id === event.message.id ? event.message : m
          );
          break;

        case "DELETE_MESSAGE":
          messageList = messageList.filter((m) => m.id !== event.message.id);
          break;

        case "READ_MESSAGE":
          messageList = messageList.map((m) =>
            m.id === event.message.id ? event.message : m
          );
          break;
      }
    });

    return messageList;
  });
}
