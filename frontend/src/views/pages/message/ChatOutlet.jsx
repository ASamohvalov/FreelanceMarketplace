import { Outlet, useOutletContext } from "react-router-dom";
import MessagesComponent from "../../components/messages/MessagesComponent";
import ConversationContextInfo from "../../components/order/ConversationContextInfo";

export function ChatOutlet() {
    const params = useOutletContext();
  return (
    <>
      <MessagesComponent conversation={params.conversation} setSelectedConversations={params.setSelectedConversations} size={params.size} uShown={params.uShown} />
      <ConversationContextInfo size={params.size} uShown={params.uShown}/>
    </>
  );
}