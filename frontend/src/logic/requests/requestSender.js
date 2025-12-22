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
export async function sendGet(path, data, config = []) {
  return await axios.get(BACKEND_URL + path, data, config);
}

/**
 * @param {string} path
 * @param {map} data
 *
 * @return {Promise}
 *
 * @example sendPost('/create', { name: 'alex', age: 21 })
 */
export async function sendPost(path, data, config = []) {
  return await axios.post(BACKEND_URL + path, data, config);
}
