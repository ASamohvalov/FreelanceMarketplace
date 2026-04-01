import { useEffect, useState } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import {
    getAllConversationsRequest,
    getConversationContextInfo,
} from "../../../logic/requests/message/messageRequest";
import ChatListComponent from "../../components/messages/ChatListComponent";
import "./css/messages_page.css";

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
                            context={{ conversation: selectedConversation, contextInfo: contextInfo }}
                        />
                    </div>
                </div>
            </main>
        </>
    );
}
