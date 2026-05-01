import { sendAuthGet, sendAuthPatch, sendAuthPost, sendAuthPostFormData } from "../requestSender";

export async function sendOrderRequest(serviceId, deadline) {
  return await sendAuthPost("order/make", {
    serviceId: serviceId,
    deadlineDate: deadline,
  });
}

export async function sendOrderReportRequest(title, report, orderId, files) {
  return await sendAuthPostFormData("order/report/send", {
    orderId: orderId,
    report: report,
    title: title,
    files: files,
  });
}

export async function getOrderFreelancerRequest(){
  return await sendAuthGet("order/freelancer/get");
}

export async function getOrderCustomerRequest(){
  return await sendAuthGet("order/customer/get");
}

export async function getOrderByIdRequest(id){
  return await sendAuthGet(`order/get/${id}`);
}

export async function sendRejectOrderRequest(id) {
  return await sendAuthPatch(`order/reject/${id}`);
}
