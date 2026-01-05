import { BACKEND_URL } from "../../env";
import axios, { AxiosError } from "axios";
import { isTokenExpired } from "../jwt";

/**
 * @param {string} path, required without / in begin
 * @param {map} params
 *
 * @returns {map} { status: int?, data: map? }
 *
 * @example sendGet('/hello', { message: 'hello world' })
 */
export async function sendGet(path, data = {}, config = []) {
  var status;
  var resData;
  await axios
    .get(BACKEND_URL + path, data, config)
    .then((response) => {
      console.log("API response data ->", response);
      status = response.status;
      resData = response.data;
    })
    .catch((error) => {
      console.log("API response error ->", error);
      const result = proccessErrors(error);
      status = result.status;
      resData = result.data;
    });
  return { status: status, data: resData };
}

/**
 * @param {string} path required without / in begin
 * @param {map} params
 * @param {map} params
 *
 * @returns {map} { status: int?, data: map? }
 *
 * @example sendGet('/hello', { message: 'hello world' })
 */
export async function sendPost(path, data = {}, config = []) {
  var status;
  var resData;
  await axios
    .post(BACKEND_URL + path, data, config)
    .then((response) => {
      console.log("API response data ->", response);
      status = response.status;
      resData = response.data;
    })
    .catch((error) => {
      console.log("API response error ->", error);
      const result = proccessErrors(error);
      status = result.status;
      resData = result.data;
    });
  return { status: status, data: resData };
}

/**
 * @param {string} path required without / in begin
 * @param {map} params
 * @param {map} params
 *
 * @returns {map} { status: int?, data: map? }
 * can return data from url "auth/update_tokens"
 *
 * @example sendGet('/hello', { message: 'hello world' })
 */
export async function sendAuthPost(path, data = {}, config = []) {
  if (isTokenExpired()) {
    // call update token
    console.log("token is expired - call update");
    const response = await sendUpdateTokensRequest();
    if (response.status >= 400) {
      return response;
    }
  }

  var token = localStorage.getItem("accessToken");
  var status;
  var resData;

  await axios
    .post(
      BACKEND_URL + path,
      data,
      {
        headers: { Authorization: "Bearer " + token },
      },
      ...config,
    )
    .then((response) => {
      console.log("API response data ->", response);
      status = response.status;
      resData = response.data;
    })
    .catch((error) => {
      console.log("API response error ->", error);
      const result = proccessErrors(error);
      status = result.status;
      resData = result.data;
    });

  return { status: status, data: resData };
}

/**
 * @returns {map} { status: int, data: { accessToken: string, refreshToken: string } }
 *          {map} { status: int?, data?: string } - if error
 */
export async function sendUpdateTokensRequest() {
  var refreshToken = localStorage.getItem("refreshToken");
  // call update token
  console.log("token is expired - call update");
  return await axios
    .post(BACKEND_URL + "auth/update_tokens", { refreshToken: refreshToken })
    .then((response) => {
      console.log("API response data ->", response);
      return {
        status: response.status,
        data: {
          accessToken: response.data.accessToken,
          refreshToken: response.data.refreshToken,
        },
      };
    })
    .catch((error) => {
      console.log("API response error ->", error);
      return proccessErrors(error);
    });
}

// local functions

/**
 * @returns {map} { status: int?, data: map? }
 *
 * @param {AxiosError} error
 */
function proccessErrors(error) {
  if (error.response) {
    return {
      status: error.response.status,
      data: error.response.data,
    };
  }
  // maybe connection error
  return {
    status: null,
    data: null,
  };
}
