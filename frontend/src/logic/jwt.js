import { jwtDecode } from "jwt-decode";

/**
 * @returns {string?}
 */
export function getAccessToken() {
  return localStorage.getItem("accessToken");
}

/**
 * @returns {string?}
 */
export function getRefreshToken() {
  return localStorage.getItem("refreshToken");
}

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
 * @returns {void}
 */
export function logout() {
  localStorage.removeItem("accessToken");
  localStorage.removeItem("refreshToken");
}

/**
 * @returns {bool}
 */
export function isAuth() {
  return getAccessToken() != null && getRefreshToken() != null;
}

/**
 * get data from localStorage
 * @returns {map?} decoded jwt
 */
export function getUserData() {
  try {
    return jwtDecode(getAccessToken());
  } catch {
    return null;
  }
}

/**
 * @returns {bool}
 */
export function isTokenExpired() {
  try {
    var claims = jwtDecode(getAccessToken())
    return Date.now() + 5000 >= claims.exp * 1000;
  } catch {
    return false;
  }
}

/**
 * @param {string} role - { ROLE_USER, ROLE_ADMIN, ROLE_FREELANCER }
 *
 * @returns {bool}
 */
export function hasRole(role) {
  const data = getUserData();
  return data?.roles.includes(role) ?? false;
}
