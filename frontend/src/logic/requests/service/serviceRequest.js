import { sendAuthPostFormData, sendGet } from "../requestSender";

/**
 * @returns {map} { status: int, data: null }
 */
export async function createServiceRequest(args) {
  return await sendAuthPostFormData("service/create", args);
}

/**
 * @returns {map} { status: int, data: [{ ... }, ...] }
 */
export async function getAllServicesRequest() {
  return await sendGet("service/get_all");
}

/**
 * @param {string} id
 * @returns {map} { status: int, data: array<byte> }
 */
export async function getImageByServiceId(id) {
  return await sendGet("service/image/" + id);
}
