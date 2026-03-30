import { sendAuthPatch, sendAuthPost } from "../requestSender";

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

export async function sendProposalReplyRequest(proposalId) {
  return await sendAuthPatch(`proposal/reply/${proposalId}`);
}
