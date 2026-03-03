import "./css/chat_list_component.css";

export default function ChatListComponent({ conversations, onSelect }) {
  return (
    <div className="col-4 chat-sidebar">
      <div className="p-3 fw-bold border-bottom">Сообщения</div>
      {conversations.map((c) => (
        <div className="chat-sidebar-item" key={c.id}
          onClick={() => onSelect(c)}
        >
          <div className="fw-semibold">
            {c.member.firstName + " " + c.member.lastName}
          </div>
          <small className="text-muted">Последнее сообщение...</small>
        </div>
      ))}
    </div>
  );
}
