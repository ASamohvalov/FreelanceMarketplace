import { BACKEND_URL } from "../env";

export function getServiceImageUrl(id) {
  return BACKEND_URL + `service/image/${id}`;
}
