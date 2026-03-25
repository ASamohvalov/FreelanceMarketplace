import "./css/modal_window.css";

export default function ModalWindowComponent({
  isVisible,
  onClose,
  onSubmit,
  error,
  title,
  description,
  buttonText,
  children,
}) {
  return (
    <div
      className={`proposal-modal-window p-4 rounded-4 shadow ${!isVisible && "d-none"}`}
      style={{ width: "min(500px, 50%)" }}
    >
      <div className="d-flex gap-3 justify-content-center">
        <form onSubmit={onSubmit}>
          <div className="text-center h3">{title}</div>
          {children}
          <div className="p-1 mb-1 text-danger">
            <span>{error}</span>
          </div>
          <div className="p-1 mb-2">
            <small>{description}</small>
          </div>
          <div className="text-center">
            <button className="btn btn-main mx-2" type="submit">
              {buttonText}
            </button>
            <a className="btn btn-outline-secondary mx-2" onClick={onClose}>
              Закрыть
            </a>
          </div>
        </form>
      </div>
    </div>
  );
}
