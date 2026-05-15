export function isEndOrderStatus(status) {
  return status == "CANCELLED" ||
    status == "COMPLETED" ||
    status == "REJECTED";
}
