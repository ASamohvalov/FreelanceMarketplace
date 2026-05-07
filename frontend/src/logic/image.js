import { BACKEND_URL } from "../env";

export function getServiceImageUrl(id) {
  return BACKEND_URL + `file/get/service/image/${id}`;
}

export function getReportFileUrl(id) {
  return BACKEND_URL + `file/get/report/file/${id}`;
}

export function getRequirementFileUrl(id) {
  return BACKEND_URL + `file/get/requirement/file/${id}`;
}

export function checkIsImage(url) {
  return new Promise((resolve) => {
    const img = new Image();
    img.onload = () => resolve(true);
    img.onerror = () => resolve(false);
    img.src = url;
  });
}

export function getAvatarUrl(userId) {
  return BACKEND_URL + `user/avatar/${userId}`;
}
