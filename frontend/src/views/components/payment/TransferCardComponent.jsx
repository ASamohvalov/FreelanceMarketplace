import { fromKopeck } from "../../../logic/lang";
import "./css/transfer_card_component.css";

export default function TransferCardComponent({
  serviceTitle,
  price,
  date,
  status,
  isIncome = true,
}) {
  return (
    <div className="col-md-6">
      <div className="personal-account-card-component_transfer-card">
        <div className="d-flex justify-content-between">
          <strong>{serviceTitle}</strong>
          <span
            className={`personal-account-card-component_${status === "CANCELLED" ? "canseled" : isIncome ? "income" : "expense"}`}
          >
            {status === "CANCELLED" ? (
              <i className="bi bi-arrow-counterclockwise" />
            ) : isIncome ? (
              "+"
            ) : (
              "-"
            )}
            {fromKopeck(price)}
          </span>
        </div>
        <small className="text-muted">{date}</small>
      </div>
    </div>
  );
}
