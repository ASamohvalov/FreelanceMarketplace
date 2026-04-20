import { sendAuthGet, sendAuthPost } from "../requestSender";

export async function sendOrderRequest(serviceId, deadline) {
  return await sendAuthPost("order/make", {
    serviceId: serviceId,
    deadlineDate: deadline,
  });
}

export async function sendOrderReportRequest(title, report, orderId) {
  return await sendAuthPost("order/report/send", {
    orderId: orderId,
    report: report,
    title: title
  });
}

export async function getOrderFreelancerRequest(){
  return await sendAuthGet("order/freelancer/get");
}

export async function getOrderCustomerRequest(){
  return await sendAuthGet("order/customer/get");
}
