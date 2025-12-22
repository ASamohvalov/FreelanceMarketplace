import { jwtDecode } from "jwt-decode";

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
 * @returns {bool}
 */
export function isAuth() {
  return localStorage.getItem("accessToken") != undefined;
}

/**
 * get data from localStorage
 * @returns {obj} decoded jwt
 */
export function getUserData() {
  var token = localStorage.getItem("accessToken");
  return jwtDecode(token);
}
