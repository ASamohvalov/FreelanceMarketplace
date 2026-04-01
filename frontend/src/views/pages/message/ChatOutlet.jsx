import { Outlet, useOutletContext } from "react-router-dom";
import MessagesComponent from "../../components/messages/MessagesComponent";
import ConversationContextInfo from "../../components/order/ConversationContextInfo";

export function ChatOutlet() {
    const params = useOutletContext();
    console.log(params);
  return (
    <>
      <MessagesComponent conversation={params.conversation} />
      <ConversationContextInfo />
    </>
  );
}