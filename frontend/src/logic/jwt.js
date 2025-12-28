import { jwtDecode } from "jwt-decode";
import { logoutRequest } from "./requests/user/userRequest";

/**
 * @param {string} accessToken
 * @param {string} refreshToken
 *
 * @returns {void}
 */
export function login(accessToken, refreshToken) {
  localStorage.setItem("accessToken", accessToken);
  localStorage.setItem("refreshToken", refreshToken);
}

/**
 * @returns {Promise} void
 */
export async function logout() {
  await logoutRequest();
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken");
}

/**
 * @returns {bool}
 */
export function isAuth() {
  return localStorage.getItem("accessToken") != undefined &&
    localStorage.getItem("refreshToken") != undefined;
}

/**
 * get data from localStorage
 * @returns {obj?} decoded jwt
 */
export function getUserData() {
  var token = localStorage.getItem("accessToken");
  try {
    return jwtDecode(token);
  } catch {
    return null;
  }
}

/**
 * @returns {bool}
 */
export function isTokenExpired() {
  var token = localStorage.getItem("accessToken");
  try {
    var claims = jwtDecode(token)
    return Date.now() >= claims.exp * 1000;
  } catch {
    return false;
  }
}
