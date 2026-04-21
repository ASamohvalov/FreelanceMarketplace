import { sendAuthGet, sendAuthPost } from "../requestSender";

export async function sendCanReviewRequest(serviceId) {
  return await sendAuthGet(`service/${serviceId}/review_check`);
}

export async function sendReviewRequest(orderId, rating, review) {
  return await sendAuthPost("review/send", {
    orderId: orderId,
    rating: rating,
    review: review,
  });
}

export async function getReviewsByServiceRequest(serviceId) {
  return await sendAuthGet(`review/get/by_service/${serviceId}`);
}
