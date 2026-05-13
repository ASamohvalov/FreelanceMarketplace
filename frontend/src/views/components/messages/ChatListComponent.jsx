import { Link, useParams } from "react-router-dom";
import "./css/chat_list_component.css";

export default function ChatListComponent({
  conversations,
  size,
  onSelect,
}) {
  const { conversationId } = useParams();
  if (!size) {
    return (
      <div className={`col-3 chat-sidebar`}>
        <div className="chat-header">Сообщения</div>
        {conversations.map((c) => (
          <Link
            style={{ textDecoration: "none" }}
            to={`/messages/${c.id}`}
            key={c.id}
          >
            <div className="chat-sidebar-item" onClick={() => onSelect(c)}>
              <div className="fw-semibold">
                {c.member.firstName + " " + c.member.lastName}
              </div>
              <small className="text-muted">
                {c.type === "ORDER" && "Обсуждение заказа"}
                {c.type === "DISCUSSION" && "Обсуждение услуги"}
                {c.type === "FEEDBACK" && "Обратная связь"}
              </small>
            </div>
          </Link>
        ))}
      </div>
    );
  } else {
    return (
      <>
        {!conversationId && (
          <div className={`col-12 chat-sidebar`}>
            <div className="chat-header">Сообщения</div>
            {conversations.map((c) => (
              <Link
                style={{ textDecoration: "none" }}
                to={`/messages/${c.id}`}
                key={c.id}
              >
                <div className="chat-sidebar-item" onClick={() => onSelect(c)}>
                  <div className="fw-semibold">
                    {c.member.firstName + " " + c.member.lastName}
                  </div>
                  <small className="text-muted">
                    {c.type === "ORDER" && "Обсуждение заказа"}
                    {c.type === "DISCUSSION" && "Обсуждение услуги"}
                    {c.type === "FEEDBACK" && "Обратная связь с модератором"}
                  </small>
                </div>
              </Link>
            ))}
          </div>
        )}
      </>
    );
  }
}
