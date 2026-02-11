import { BACKEND_URL } from "../../env";
import axios, { AxiosError } from "axios";
import { getAccessToken, getRefreshToken, isTokenExpired, login } from "../jwt";

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
 * @param {map} data
 *
 * @returns {map} { status: int?, data: map? }
 * can return data from url "auth/update_tokens"
 *
 * @example sendAuthPost('/hello', { message: 'hello world' })
 */
export async function sendAuthPost(path, data = {}) {
  return await sendAuth(path, true, data);
}

/**
 * @param {string} path required without / in begin
 * @param {map} params
 *
 * @returns {map} { status: int?, data: map? }
 * can return data from url "auth/update_tokens"
 *
 * @example sendAuthGet('/hello', { message: 'hello world' })
 */
export async function sendAuthGet(path, params = {}) {
  return await sendAuth(path, false, params);
}

/**
 * @param {string} path required without / in begin
 * @param {map} data
 *
 * @returns {map} { status: int?, data: map? }
 * can return data from url "auth/update_tokens"
 *
 * @example sendAuthPost('/hello', { message: 'hello world' })
 */
export async function sendAuthPostFormData(path, data = {}) {
  return await sendAuth(path, true, data, "multipart/form-data");
}

/**
 * @returns {map} { status: int, data: { accessToken: string, refreshToken: string } }
 *          {map} { status: int?, data: string? } - if error
 */
export async function sendUpdateTokensRequest() {
  return axios
    .post(BACKEND_URL + "auth/update_tokens", { refreshToken: getRefreshToken() })
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
 * @param {string} path required without / in begin
 * @param {bool} isPost
 * @param {map} data
 *
 * @returns {map} { status: int?, data: map? }
 * can return data from url "auth/update_tokens"
 *
 * @example sendAuth('/hello', true, { message: 'hello world' })
 */
async function sendAuth(path, isPost, data = {}, contentType = "application/json") {
  if (isTokenExpired()) {
    // call update token
    console.log("token is expired - call update");
    const response = await sendUpdateTokensRequest();
    if (response.status >= 400) {
      return response;
    }
    login(response.data.accessToken, response.data.refreshToken);
  }

  var token = getAccessToken();
  try {
    const response = isPost
      ? await axios.post(BACKEND_URL + path, data, {
        headers: {
          "Content-Type": contentType,
          Authorization: "Bearer " + token
        },
        })
      : await axios.get(BACKEND_URL + path, {
          headers: { Authorization: "Bearer " + token },
          params: data,
        });

    console.log("API response data ->", response);
    return { status: response.status, data: response.data };
  } catch (error) {
    console.log("API response error ->", error);
    const result = proccessErrors(error);
    return { status: result.status, data: result.data };
  }
}

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
