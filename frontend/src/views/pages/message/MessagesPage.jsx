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

export default function MessagesPage() {
    const navigate = useNavigate();

    const [conversations, setConversations] = useState([]);
    const [contextInfo, setContextInfo] = useState(null);
    const [selectedConversation, setSelectedConversation] = useState(null);

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
    }, [navigate]);
    
    

    

    return (
        <>
            <main>
                <div className="container mt-4">
                    <div className="chat-container d-flex">
                        <ChatListComponent
                            conversations={conversations}
                            onSelect={async (c) => {
                                setSelectedConversation(c);

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
                            context={{ conversation: selectedConversation }}
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
