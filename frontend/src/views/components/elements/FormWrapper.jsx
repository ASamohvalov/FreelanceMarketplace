export function FormWrapper({ children, Title, error }) {
  return (
    <div
      style={{ display: "flex", alignItems: "center", height: "90vh" }}
    >
      <div
        className="mx-auto position-relative"
        style={{ width: "min(500px, 50%)" }}
      >
        <div className="shadow w-100 bg-white p-4 text-light sign-form_wrapper">
          <div
            style={{ width: "80%", top: "-10vh", right: "10%" }}
            className={`mb-2 fs-5 text-center text-white fw-bolder bg-danger p-4 border border-danger rounded-4 shadow ${error ? "position-absolute" : "d-none"}`}
          >
            {error}
          </div>
          <div className="text-center mb-3 h4 sign-form_title">{Title}</div>
          {children}
        </div>
      </div>
    </div>
  );
}
