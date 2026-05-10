import {
  useCallback,
  useEffect,
  useLayoutEffect,
  useRef,
  useState,
} from "react";
import { Outlet, useNavigate, useParams } from "react-router-dom";
import {
  getAllConversationsRequest,
  getConversationContextInfo,
} from "../../../logic/requests/message/messageRequest";
import ChatListComponent from "../../components/messages/ChatListComponent";
import "./css/messages_page.css";

export default function MessagesPage() {
  const navigate = useNavigate();
  const { width: widthRef } = document.body.getBoundingClientRect();

  const [shrink, setShrink] = useState(null);
  const [conversations, setConversations] = useState([]);
  const [contextInfo, setContextInfo] = useState(null);
  const [selectedConversation, setSelectedConversation] = useState(null);
  const [shown, setShown] = useState(false);

  const handleResize = useCallback(() => {
    const { width } = document.body.getBoundingClientRect();
    setShrink(width < 650);
  }, []);

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
  useLayoutEffect(() => {
    handleResize();
    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, [handleResize]);

  return (
    <div className="container mt-4">
      <div className="chat-container position-relative d-block d-md-flex">
        <ChatListComponent
          conversations={conversations}
          selectedConversation={selectedConversation}
          size={shrink}
          onSelect={async (c) => {
            setSelectedConversation(c);

            const contextResponse = await getConversationContextInfo(c.id);
            if (contextResponse.status !== 200) {
              navigate("/error");
              return;
            }
            setContextInfo(contextResponse.data);
          }}
        />
        <Outlet
          context={{
            conversation: selectedConversation,
            setSelectedConversations: setSelectedConversation,
            contextInfo: contextInfo,
            size: shrink,
            uShown: [shown, setShown],
          }}
        />
      </div>
    </div>
  );
}
