import { useState } from "react";
import HeaderComponent from "../../components/HeaderComponent";
import ChatListComponent from "../../components/messages/ChatListComponent";
import MessagesComponent from "../../components/messages/MessagesComponent";
import { useEffect } from "react";
import { getAllConversationsRequest } from "../../../logic/requests/message/messageRequest";
import "./css/messages_page.css";
import { useNavigate } from "react-router-dom";
import { getUserData } from "../../../logic/jwt";
import getMessages from "../../../logic/message";

export default function MessagesPage() {
  const navigate = useNavigate();
  const [conversations, setConversations] = useState([]);
  const [messages, setMessages] = useState([]);
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
          <div className="chat-container border d-flex">
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
          </div>
        </div>
      </main>
    </>
  );
}
