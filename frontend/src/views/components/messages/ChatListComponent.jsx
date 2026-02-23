import './css/chat_list_component.css';

export default function ChatListComponent() {
  return (
    <div className="col-md-4 col-lg-3 p-0 chat-list">
      <div className="p-3">
        <input
          type="text"
          className="form-control"
          placeholder="Search chats..."
        />
      </div>

      <div className="chat-item active d-flex align-items-center gap-3">
        <div className="avatar">CJ</div>
        <div>
          <strong>Cris James</strong>
          <div className="text-muted small">Last message preview...</div>
        </div>
      </div>

      <div className="chat-item d-flex align-items-center gap-3">
        <div className="avatar">AL</div>
        <div>
          <strong>Anna Lee</strong>
          <div className="text-muted small">Thanks for your order!</div>
        </div>
      </div>

      <div className="chat-item d-flex align-items-center gap-3">
        <div className="avatar">MS</div>
        <div>
          <strong>Michael Scott</strong>
          <div className="text-muted small">Let’s discuss the details.</div>
        </div>
      </div>
    </div>
  );
}
