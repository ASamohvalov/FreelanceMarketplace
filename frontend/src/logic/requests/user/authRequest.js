import { sendPost } from "../requestSender";

/**
 * @param {string} email
 * @param {string} password
 * @returns {map} { status: int?, data: string? }
 */
export async function signInRequest(email, password) {
  return await sendPost('auth/sign_in', {
    email: email,
    password: password,
  });
}

/**
 * @param {string} email
 * @param {string} password
 * @param {string} firstName
 * @param {string} lastName
 *
 * @returns {map} { status: int?, data: string? }
 */
export async function signUpRequest(email, password, firstName, lastName) {
  return await sendPost('auth/sign_up', {
    email: email,
    password: password,
    firstName: firstName,
    lastName: lastName,
  });
}
