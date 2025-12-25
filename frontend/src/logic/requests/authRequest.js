import { sendPost } from "./requestSender";

/**
 * @param {string} email
 * @param {string} password
 * @return {map} { status: '', data: '' }
 */
export async function signIn(email, password) {
  return await sendPost('auth/sign_in', {
    email: email,
    password: password,
  });
}

/**
 * @param {string} email
 * @param {string} password
 * @return {int} - ok, response
 */
export async function signUp(email, password) {
  return await sendPost('auth/sign_up', {
    email: email,
    password: password,
  });
}
