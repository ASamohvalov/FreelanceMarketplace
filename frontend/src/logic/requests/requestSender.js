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
  return await sendAuth(path, "POST", data);
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
  return await sendAuth(path, "GET", params);
}

/**
 * @param {string} path required without / in begin
 * @param {map} params
 *
 * @returns {map} { status: int?, data: map? }
 * can return data from url "auth/update_tokens"
 *
 * @example sendAuthPut('/hello', { message: 'hello world' })
 */
export async function sendAuthPut(path, params = {}) {
  return await sendAuth(path, "PUT", params);
}

/**
 * @param {string} path required without / in begin
 * @param {map} params
 *
 * @returns {map} { status: int?, data: map? }
 * can return data from url "auth/update_tokens"
 *
 * @example sendAuthPut('/hello', { message: 'hello world' })
 */
export async function sendAuthPatch(path, params = {}) {
  return await sendAuth(path, "PATCH", params);
}

/**
 * @param {string} path required without / in begin
 * @param {map} params
 *
 * @returns {map} { status: int?, data: map? }
 * can return data from url "auth/update_tokens"
 *
 * @example sendAuthPut('/hello', { message: 'hello world' })
 */
export async function sendAuthDelete(path, params = {}) {
  return await sendAuth(path, "DELETE", params);
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
  return await sendAuth(path, "POST", data, "multipart/form-data");
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
export async function sendAuthPutFormData(path, data = {}) {
  return await sendAuth(path, "PUT", data, "multipart/form-data");
}

/**
 * @returns {map} { status: int, data: { accessToken: string, refreshToken: string } }
 *          {map} { status: int?, data: string? } - if error
 */
export async function sendUpdateTokensRequest() {
  return axios
    .post(BACKEND_URL + "auth/update_tokens", {
      refreshToken: getRefreshToken(),
    })
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
 * @param {string} requestType
 * @param {map} data
 *
 * @returns {map} { status: int?, data: map? }
 * can return data from url "auth/update_tokens"
 *
 * @example sendAuth('/hello', true, { message: 'hello world' })
 */
async function sendAuth(
  path,
  requestType,
  data = {},
  contentType = "application/json",
) {
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
    var response;
    if (requestType === "POST") {
      response = await axios.post(BACKEND_URL + path, data, {
        headers: {
          "Content-Type": contentType,
          Authorization: "Bearer " + token,
        },
      });
    } else if (requestType === "GET") {
      response = await axios.get(BACKEND_URL + path, {
        headers: { Authorization: "Bearer " + token },
        params: data,
      });
    } else if (requestType === "PUT") {
      response = await axios.put(BACKEND_URL + path, data, {
        headers: {
          "Content-Type": contentType,
          Authorization: "Bearer " + token,
        },
      });
    } else if (requestType === "PATCH") {
      response = await axios.patch(BACKEND_URL + path, data, {
        headers: {
          "Content-Type": contentType,
          Authorization: "Bearer " + token,
        },
      });
    } else if (requestType === "DELETE") {
      response = await axios.delete(BACKEND_URL + path, {
        headers: {
          "Content-Type": contentType,
          Authorization: "Bearer " + token,
        },
        data: data
      });
    } else {
      console.log("logic ERROR");
      return;
    }

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
