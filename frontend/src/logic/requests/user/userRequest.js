import { login } from "../../jwt";
import { sendAuthGet, sendAuthPost, sendAuthPostFormData, sendAuthPut, sendUpdateTokensRequest } from "../requestSender";

export async function logoutRequest() {
  const refreshToken = localStorage.getItem("refreshToken");
  return await sendAuthPost("user/logout", { refreshToken: refreshToken });
}

export async function getInfoRequest() {
  return await sendAuthGet("user/get_info");
}

export async function editProfileRequest(firstName, lastName) {
  const response = await sendAuthPut("user/profile/edit", {
    firstName: firstName,
    lastName: lastName,
  });

  const { accessToken, refreshToken } = (await sendUpdateTokensRequest()).data;
  login(accessToken, refreshToken);
  return response;
}

export async function uploadAvatarRequest(file) {
  return await sendAuthPostFormData("user/avatar/upload", {
    avatar: file,
  });
}
