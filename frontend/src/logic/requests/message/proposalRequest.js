import { sendAuthPost } from "../requestSender";

/**
 * @param {string} serviceId
 * @param {string} description
 * @returns {map} { status: int }
 */
export async function sendProposalRequest(serviceId, description) {
  return await sendAuthPost("proposal/send", {
    serviceId: serviceId,
    description: description,
  });
}
