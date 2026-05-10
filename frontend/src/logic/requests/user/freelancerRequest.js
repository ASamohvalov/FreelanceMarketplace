import { getUserData, login } from "../../jwt";
import { sendAuthPost, sendAuthPut, sendGet, sendUpdateTokensRequest } from "../requestSender";

export async function becomeFreelancerRequest(aboutMe, jobTitleId) {
  const response = await sendAuthPost("user/become_freelancer", {
    aboutYourself: aboutMe,
    jobTitleId: jobTitleId,
  });
  const { accessToken, refreshToken } = (await sendUpdateTokensRequest()).data;
  login(accessToken, refreshToken);
  return response;
}

export async function getFreelancerRequest() {
  const { id } = getUserData();
  return await sendGet(`freelancer/get/by_user/${id}`);
}

export async function editFreelancerProfile(
  firstName,
  lastName,
  jobTitleId,
  aboutYourself
) {
  const response = await sendAuthPut("freelancer/profile/edit", {
    firstName: firstName,
    lastName: lastName,
    jobTitleId: jobTitleId,
    aboutYourself: aboutYourself
  });

  const { accessToken, refreshToken } = (await sendUpdateTokensRequest()).data;
  login(accessToken, refreshToken);
  return response;
}

export async function getFreelancerReviewsRequest(id) {
  return await sendGet(`freelancer/get/${id}/reviews`);
}
