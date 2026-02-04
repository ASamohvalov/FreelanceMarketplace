import { sendAuthPostFormData, sendGet } from "../requestSender";

/**
 * @param {File} image
 * @param {string} description
 * @param {int} price
 * @returns {map} { status: int, data: null }
 */
export async function createServiceRequest(title, image, description, price) {
  return await sendAuthPostFormData("service/create", {
    image: image,
    title: title,
    description: description,
    price: price,
  });
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
