import { useEffect, useState } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import { getUserData } from "../../../logic/jwt";
import getMessages from "../../../logic/message";
import {
    getAllConversationsRequest,
    getConversationContextInfo,
} from "../../../logic/requests/message/messageRequest";
import HeaderComponent from "../../components/HeaderComponent";
import ChatListComponent from "../../components/messages/ChatListComponent";
import MessagesComponent from "../../components/messages/MessagesComponent";
import "./css/messages_page.css";
import ConversationContextInfo from "../../components/order/ConversationContextInfo";
import { useMemo } from "react";
import { useCallback } from "react";

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
            console.log(response.data);
            setConversations(response.data);
        })();

        return () => {
            clearInterval(interval);
        };
    }, [navigate, interval]);
    
    const addNewMessageHandle = useCallback((message, conversationId) => {
        setMessages((prev) => [...prev, {
            id: null,
            conversationId: conversationId,
            text: message,
            authorId: getUserData().id,
            sendAt: new Date().getTime(),
            read: false,
        }]);
    }, [setMessages]);

    const errorHandle = useCallback(() => {
        navigate("/error");
    }, [navigate]);
    const outletContext = useMemo(() => ({
      // messages,
      errorHandle,
      setMessages,
      addNewMessageHandle,
    }), [
      // messages,
      errorHandle,
      setMessages,
      addNewMessageHandle,
    ]);

    return (
        <>
            <main>
                <div className="container mt-4">
                    <div className="chat-container d-flex">
                        <ChatListComponent
                            conversations={conversations}
                            onSelect={async (c) => {
                                if (interval !== null) clearInterval(interval);
                                setSelectedConversation(c);
                                setInterval(
                                    await getMessages(setMessages, c.id, errorHandle),
                                );

                                const contextResponse =
                                    await getConversationContextInfo(c.id);
                                if (contextResponse.status !== 200) {
                                    navigate("/error");
                                    return;
                                }
                                setContextInfo(contextResponse.data);
                            }}
                        />
                        <Outlet
                            context={outletContext}
                        />
                        {contextInfo !== null && (
                            <ConversationContextInfo info={contextInfo} />
                        )}
                    </div>
                </div>
            </main>
        </>
    );
}
