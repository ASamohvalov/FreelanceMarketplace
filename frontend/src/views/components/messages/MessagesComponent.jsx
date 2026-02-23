import './css/messages_component.css'

export default function MessagesComponent() {
  return (
    <div className="col-md-8 col-lg-9 p-0 chat-window">
      <div className="chat-header d-flex align-items-center gap-3">
        <div className="avatar">CJ</div>
        <div>
          <strong>Cris James</strong>
          <div className="text-success small">Online</div>
        </div>
      </div>

      <div className="chat-messages">
        <div className="message received">
          Hi! I’m interested in your WordPress service.
        </div>

        <div className="message sent">
          Hello! Great 🙂 Could you tell me more about your project?
        </div>

        <div className="message received">
          I need a landing page for my startup.
        </div>

        <div className="message sent">
          Perfect. I can deliver it within 5 days.
        </div>
      </div>

      <div className="chat-input">
        <div className="input-group">
          <input
            type="text"
            className="form-control"
            placeholder="Type a message..."
          />
          <button className="btn btn-primary">Send</button>
        </div>
      </div>
    </div>
  );
}
