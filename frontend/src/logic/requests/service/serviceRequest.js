import { isAuth } from "../../jwt";
import { sendAuthGet, sendAuthPostFormData, sendGet } from "../requestSender";

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
  return await sendGet("service/get");
}

/**
 * @param {string} id
 * @returns {map} { status: int, data: array<byte> }
 */
export async function getImageByServiceIdRequest(id) {
  return await sendGet("service/image/" + id);
}

/**
 * @param {string} id
 * @returns {map} { status: int, data: any }
 */
export async function getServiceByIdRequest(id) {
  if (isAuth()) {
    return await sendAuthGet(`service/get/${id}`);
  }
  return await sendGet(`service/get/${id}`);
}

/**
 * @returns {map} { status: int, data: map }
 */
export async function getAllPersonalServices(id) {
  return await sendAuthGet(`service/freelancer/${id}`);
}

/**
 * @returns {map} { status: int, data: map }
 */
export default async function getPaymentInfoRequest(serviceId) {
  return await sendGet(`service/payment/info/${serviceId}`);
}
