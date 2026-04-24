import { login } from "../../jwt";
import { sendAuthPost, sendUpdateTokensRequest } from "../requestSender";

/**
 * @param {string} phoneNumber
 * @param {string} jobTitleId
 *
 * @returns {map} { status: int, data: map }
 */
export async function becomeFreelancerRequest(aboutMe, jobTitleId) {
  const response = await sendAuthPost("user/become_freelancer", {
    aboutYourself: aboutMe,
    jobTitleId: jobTitleId,
  });
  const { accessToken, refreshToken } = (await sendUpdateTokensRequest()).data;
  login(accessToken, refreshToken);
  return response;
}
