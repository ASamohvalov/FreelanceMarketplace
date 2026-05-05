import { isAuth } from "../../jwt";
import { sendAuthGet, sendAuthPatch, sendAuthPostFormData, sendAuthPutFormData, sendGet } from "../requestSender";

/**
 * @returns {map} { status: int, data: null }
 */
export async function createServiceRequest(args) {
  return await sendAuthPostFormData("service/create", args);
}

export async function editServiceRequest(id, args) {
  return await sendAuthPutFormData("service/update/" + id, args);
}

/**
 * @returns {map} { status: int, data: [{ ... }, ...] }
 */
export async function getAllServicesRequest() {
  return await sendGet("service/get");
}

export async function getOwnServicesRequest(){
  return await sendAuthGet("service/get/own");
}

export async function showOwnServicesRequest(id){
  return await sendAuthPatch(`service/show/${id}`);
}

export async function hideOwnServicesRequest(id){
  return await sendAuthPatch(`service/hide/${id}`);
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
export async function getPaymentInfoRequest(serviceId) {
  return await sendGet(`service/payment/info/${serviceId}`);
}


export async function getPopularServices(size=3) {
  return await sendGet("service/get/most_popular", {
    size: size,
  });
}
