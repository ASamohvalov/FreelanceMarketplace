import { sendPost } from "./requestSender";

/**
 * @param {string} email
 * @param {string} password
 * @returns {map<bool, string>} - ok, response
 */
export async function signIn(email, password) {
  var response = null;
  var ok = false;
  await sendPost('sign_in', {
    username: email,
    password: password,
  }).then(() => {
    ok = true;
  }).catch((error) => {
    console.log('Send request error', error.toJSON);
    response = error.data;
  });
  return { ok: ok, data: response };
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
