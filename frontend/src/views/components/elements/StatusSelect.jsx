import React from "react";

const STATUSES = [
  { name: "Все", value: "all" },
  { name: "Ожидает проверки", value: "PENDING" },
  { name: "Принят", value: "ACCEPTED" },
  { name: "Отклонён", value: "REJECTED" },
];

export const StatusSelect = ({ handleStatusesSelect, statuses }) => {
  return (
    <select
      className="form-select"
      style={{ width: "35%" }}
      onChange={handleStatusesSelect}
    >
      {STATUSES.map((status, idx) => (
        <option value={statuses ? statuses[idx] : status.value} key={idx}>
          {status.name}
        </option>
      ))}
    </select>
  );
};
