import { sendAuthPost } from "../requestSender";

/**
 * @param {string} phoneNumber
 * @param {string} jobTitleId
 *
 * @returns {map} { status: int, data: map }
 */
export async function becomeFreelancerRequest(phoneNumber, jobTitleId) {
  return await sendAuthPost("user/become_freelancer", {
    phoneNumber: phoneNumber,
    jobTitleId: jobTitleId,
  });
}
