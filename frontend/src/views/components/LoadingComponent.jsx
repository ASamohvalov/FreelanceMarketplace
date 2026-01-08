export default function LoadingComponent() {
  return (
    <div className="d-flex justify-content-center align-items-center vh-100">
      <div className="spinner-border" role="status" style={{ height: "50px", width: "50px" }}>
        <span className="visually-hidden">Loading...</span>
      </div>
    </div>
  );
}
