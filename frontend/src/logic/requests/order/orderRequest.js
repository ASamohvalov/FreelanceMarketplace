import { sendAuthGet, sendAuthPatch, sendAuthPostFormData, sendGet } from "../requestSender";

export async function sendOrderRequest(serviceId, deadlineDays, description, comment, files) {
  return await sendAuthPostFormData("order/make", {
    serviceId: serviceId,
    deadlineDays: deadlineDays,
    description: description,
    comment: comment,
    files,
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

export async function sendAcceptOrderRequest(id) {
  return await sendAuthPatch(`order/accept/${id}`);
}

export async function sendCancelOrderRequest(id) {
  return await sendAuthPatch(`order/cancel/${id}`);
}

export async function getOrderRequirementRequest(id) {
  return await sendAuthGet(`order/requirement/get/${id}`);
}
