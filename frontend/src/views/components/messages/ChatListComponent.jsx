import "./css/chat_list_component.css";

export default function ChatListComponent({ conversations, onSelect }) {
  return (
    <div className="col-3 chat-sidebar">
      <div className="chat-header">Сообщения</div>
      {conversations.map((c) => (
        <div className="chat-sidebar-item" key={c.id}
          onClick={() => onSelect(c)}
        >
          <div className="fw-semibold">
            {c.member.firstName + " " + c.member.lastName}
          </div>
          <small className="text-muted">
            {conversations.type === "ORDER" ? "Название услуги..." : "Обсуждение усулги"}
          </small>
        </div>
      ))}
    </div>
  );
}
