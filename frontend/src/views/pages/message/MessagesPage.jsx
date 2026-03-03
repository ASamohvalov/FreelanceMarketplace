import { useState } from "react";
import HeaderComponent from "../../components/HeaderComponent";
import ChatListComponent from "../../components/messages/ChatListComponent";
import MessagesComponent from "../../components/messages/MessagesComponent";
import { useEffect } from "react";
import { getAllConversationsRequest, getAllMessagesRequest } from "../../../logic/requests/message/messageRequest";
import "./css/messages_page.css";
import { useNavigate } from "react-router-dom";

export default function MessagesPage() {
  const navigate = useNavigate();
  const [conversations, setConversations] = useState([]);
  const [messages, setMessages] = useState([]);
  const [selectedConversation, setSelectedConversation] = useState(null);

  useEffect(() => {
    (async () => {
      const response = await getAllConversationsRequest();
      if (response.status !== 200) {
        navigate("/error");
        return;
      }
      setConversations(response.data);
    })();
  }, [navigate]);

  return (
    <>
      <HeaderComponent />
      <main>
        <div className="container mt-4">
          <div className="chat-container border d-flex">
            <ChatListComponent
              conversations={conversations}
              onSelect={async (c) => {
                setSelectedConversation(c)
                const response = await getAllMessagesRequest(c.id);
                if (response.status !== 200) {
                  navigate("/error");
                  return;
                }
                setMessages(response.data);
              }}
            />
            <MessagesComponent
              messages={messages}
              conversation={selectedConversation}
              errorHandle={() => {
                navigate("/error")
              }}
            />
          </div>
        </div>
      </main>
    </>
  );
}
