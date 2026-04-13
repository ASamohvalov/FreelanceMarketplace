import { sendAuthPost } from "../requestSender";

export async function sendOrderRequest(serviceId, deadline) {
  return await sendAuthPost("order/make", {
    serviceId: serviceId,
    deadlineDate: deadline,
  });
}

export async function sendOrderReportRequest(report, orderId) {
  return await sendAuthPost("order/report/send", {
    orderId: orderId,
    report: report
  });
}
