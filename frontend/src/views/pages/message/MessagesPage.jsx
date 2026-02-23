import HeaderComponent from "../../components/HeaderComponent";
import ChatListComponent from "../../components/messages/ChatListComponent";
import MessagesComponent from "../../components/messages/MessagesComponent";
import './css/messages_page.css';

export default function MessagesPage() {
  return (
    <>
      <HeaderComponent />
      <main>
        <div className="container-fluid chat-container">
          <div className="row h-100">
            <ChatListComponent />
            <MessagesComponent />
          </div>
        </div>
      </main>
    </>
  );
}
