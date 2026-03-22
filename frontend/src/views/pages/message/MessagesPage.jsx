import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getUserData } from "../../../logic/jwt";
import getMessages from "../../../logic/message";
import { getAllConversationsRequest, getConversationContextInfo } from "../../../logic/requests/message/messageRequest";
import HeaderComponent from "../../components/HeaderComponent";
import ChatListComponent from "../../components/messages/ChatListComponent";
import MessagesComponent from "../../components/messages/MessagesComponent";
import "./css/messages_page.css";
import ConversationContextInfo from "../../components/order/ConversationContextInfo";

export default function MessagesPage() {
  const navigate = useNavigate();

  const [conversations, setConversations] = useState([]);
  const [messages, setMessages] = useState([]);
  const [contextInfo, setContextInfo] = useState(null);
  const [selectedConversation, setSelectedConversation] = useState(null);

  const [interval, setInterval] = useState(null);

  useEffect(() => {
    (async () => {
      const response = await getAllConversationsRequest();
      if (response.status !== 200) {
        navigate("/error");
        return;
      }
      setConversations(response.data);
    })();

    return () => {
      clearInterval(interval);
    }
  }, [navigate, interval]);

  return (
    <>
      <HeaderComponent />
      <main>
        <div className="container mt-4">
          <div className="chat-container d-flex">
            <ChatListComponent
              conversations={conversations}
              onSelect={async (c) => {
                if (interval !== null) clearInterval(interval);
                setSelectedConversation(c);
                setInterval(
                  await getMessages(setMessages, c.id, () => {
                    navigate("/error");
                  }),
                );

                const contextResponse = await getConversationContextInfo(c.id);
                if (contextResponse.status !== 200) {
                  navigate("/error");
                  return;
                }
                setContextInfo(contextResponse.data);
              }}
            />
            <MessagesComponent
              messages={messages}
              conversation={selectedConversation}
              errorHandle={() => {
                navigate("/error");
              }}
              addNewMessageHandle={(message, conversationId) => {
                var mes = [...messages];
                mes.push({
                  id: null,
                  conversationId: conversationId,
                  text: message,
                  authorId: getUserData().id,
                  sendAt: new Date().getTime(),
                  read: false,
                });
                setMessages(mes);
              }}
            />
            {contextInfo !== null &&
              <ConversationContextInfo info={contextInfo} />
            }
          </div>
        </div>
      </main>
    </>
  );
}
