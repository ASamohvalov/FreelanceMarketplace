import { sendPost } from "./requestSender";

export async function signIn(username, password) {
  var ok = true;
  await sendPost('sign_in', {
    username: username,
    password: password,
  }).catch((error) => {
    console.log('Send request error', error.toJSON);
    ok = false;
  });
  return ok;
}
