import { BACKEND_URL } from "../../env";
import axios from "axios";

/**
 * @param {string} path
 * @param {map} params
 *
 * @return {Promise}
 *
 * @example sendGet('/hello', { message: 'hello world' })
 */
export async function sendGet(path, params) {
  return await axios.get(BACKEND_URL + path, params);
}

/**
 * @param {string} path
 * @param {map} params
 *
 * @return {Promise}
 *
 * @example sendPost('/create', { name: 'alex', age: 21 })
 */
export async function sendPost(path, params) {
  return await axios.get(BACKEND_URL + path, params);
}
