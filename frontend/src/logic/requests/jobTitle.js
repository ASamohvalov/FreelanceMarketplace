import { sendGet } from "./requestSender";

/**
 * @returns {array} [{ id: string, name: string }, ...]
 */
export async function getAllJobTitlesRequest() {
  return await sendGet("job_title/get_all");
}
