import { BACKEND_URL } from "../../env";
import axios from "axios";

/**
 * @param {string} path, required without / in begin
 * @param {map} params
 *
 * @return {int} status
 *
 * @example sendGet('/hello', { message: 'hello world' })
 */
export async function sendGet(path, data, config = []) {
  var status;
  var resData;
  await axios.get(BACKEND_URL + path, data, config)
    .then((response) => {
      console.log("API response data ->", response);
      status = response.status;
      resData = response.data;
    }).catch((error) => {
      console.log("API response error ->", error);
      status = error.response.status;
      resData = error.response.data;
    })
  return { status: status, data: resData };
}

/**
 * @param {string} path required without / in begin
 * @param {map} params
 * @param {map} params
 *
 * @return {map} { status: '', data: '' }
 *
 * @example sendGet('/hello', { message: 'hello world' })
 */
export async function sendPost(path, data, config = []) {
  var status;
  var resData;
  await axios.post(BACKEND_URL + path, data, config)
    .then((response) => {
      console.log("API response data ->", response);
      status = response.status;
      resData = response.data;
    }).catch((error) => {
      console.log("API response error ->", error);
      status = error.response.status;
      resData = error.response.data;
    })
  return { status: status, data: resData };
}
