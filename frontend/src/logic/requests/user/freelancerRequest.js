import { login } from "../../jwt";
import { sendAuthPost, sendUpdateTokensRequest } from "../requestSender";

/**
 * @param {string} phoneNumber
 * @param {string} jobTitleId
 *
 * @returns {map} { status: int, data: map }
 */
export async function becomeFreelancerRequest(phoneNumber, jobTitleId) {
  const response = await sendAuthPost("user/become_freelancer", {
    phoneNumber: phoneNumber,
    jobTitleId: jobTitleId,
  });
  const { accessToken, refreshToken } = (await sendUpdateTokensRequest()).data;
  login(accessToken, refreshToken);
  return response;
}
