import { sendPost } from "./requestSender";

/**
 * @param {string} email
 * @param {string} password
 * @returns {map<int, string>} - status, response
 */
export async function signIn(email, password) {
  var response = null;
  var status = 403;

  await sendPost('auth/sign_in', {
    email: email,
    password: password,
  }).then((res) => {
    status = res.status;
    response = res.data;
  }).catch((error) => {
    console.log('Send request error', error.response);
    response = error.response.data;
    status = error.response.status;
  });
  return { status: status, data: response };
}

/**
 * @param {string} email
 * @param {string} password
 * @returns {map<bool, string>} - ok, response
 */
export async function signUp(email, password) {
  var response;
  var ok = false;
  await sendPost('sign_up', {
    username: email,
    password: password,
  }).then((res) => {
    ok = true;
    response = res.data;
  }).catch((error) => {
    console.log('Send request error', error.toJSON);
    response = error.data;
  });
  return { ok: ok, data: response };
}
