import { sendAuthDelete, sendAuthPost, sendGet } from "./requestSender";

/**
 * @returns {map} { status: int, data: [{ id: string, name: string }, ...] }
 */
export async function getAllJobTitlesRequest() {
  return await sendGet("job_title/get");
}

export async function createJobTitleRequest(name) {
  return await sendAuthPost("job_title/create", {
    name: name
  });
}

export async function deleteJobTitleRequest(id) {
  return await sendAuthDelete(`job_title/delete/${id}`);
}
