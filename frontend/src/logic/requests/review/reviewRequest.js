import { sendAuthGet, sendAuthPost, sendAuthPut } from "../requestSender";

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

export async function sendEditReviewRequest(reviewId, rating, review) {
  return await sendAuthPut("review/edit", {
    reviewId: reviewId,
    rating: rating,
    review: review,
  });
}

export async function getReviewsByServiceRequest(serviceId) {
  return await sendAuthGet(`review/get/by_service/${serviceId}`);
}

export async function getReviewByOrder(orderId) {
  return await sendAuthGet(`review/get/by_order/${orderId}`);
}

export async function getPersonalReviewByServiceRequest(serviceId) {
  return await sendAuthGet(`review/get/personal/by_service/${serviceId}`);
}
