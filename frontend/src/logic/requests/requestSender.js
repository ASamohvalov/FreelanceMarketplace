import { BACKEND_URL } from "../../env";
import axios from "axios";
import { isTokenExpired } from "../jwt";

/**
 * @param {string} path, required without / in begin
 * @param {map} params
 *
 * @return {int} status
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
      status = error.response.status;
      resData = error.response.data;
    });
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
      status = error.response.status;
      resData = error.response.data;
    });
  return { status: status, data: resData };
}

/**
 * @param {string} path required without / in begin
 * @param {map} params
 * @param {map} params
 *
 * @return {map} { status: '', data: '' }
 * can return data from url "auth/update_tokens"
 *
 * @example sendGet('/hello', { message: 'hello world' })
 */
export async function sendAuthPost(path, data = {}, config = []) {
  var accessToken, refreshToken;
  if (isTokenExpired()) {
    refreshToken = localStorage.getItem("refreshToken");
    // call update token
    console.log("token is expired - call update");
    try {
      await axios
        .post(
          BACKEND_URL + "auth/update_tokens",
          { refreshToken: refreshToken },
          ...config
        )
        .then((response) => {
          console.log("API response data ->", response);
          accessToken = response.data.accessToken;
          refreshToken = response.data.refreshToken;
        })
    } catch (error) {
      console.log("API response error ->", error);
      return { status: error.response.status, data: error.response.data };
    }
    localStorage.setItem("accessToken", accessToken);
    localStorage.setItem("refreshToken", refreshToken);
  } else {
    accessToken = localStorage.getItem("accessToken");
  }

  var status;
  var resData;

  await axios
    .post(
      BACKEND_URL + path,
      data,
      {
        headers: { Authorization: "Bearer " + accessToken },
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
      status = error.response.status;
      resData = error.response.data;
    });

  return { status: status, data: resData };
}
