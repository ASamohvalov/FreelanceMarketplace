import { sendAuthGet, sendAuthPatch } from "../requestSender";

export async function getReceivedOrderReportsRequest() {
  return await sendAuthGet("order/report/received/get");
}

export async function getSentOrderReportsRequest() {
  return await sendAuthGet("order/report/sent/get");
}

export async function acceptOrderReportRequest(orderId, comment) {
  return await sendAuthPatch(`order/report/accept/${orderId}`, {
    comment: comment
  })
}

export async function rejectOrderReportRequest(orderId, comment) {
  return await sendAuthPatch(`order/report/reject/${orderId}`, {
    comment: comment
  })
}
