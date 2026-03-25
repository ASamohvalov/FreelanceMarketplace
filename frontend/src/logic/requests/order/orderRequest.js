import { sendAuthPost } from "../requestSender";

export async function sendOrderRequest(serviceId, deadline) {
  return await sendAuthPost("order/make", {
    serviceId: serviceId,
    deadlineDate: deadline,
  });
}
