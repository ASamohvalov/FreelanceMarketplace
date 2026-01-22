import { sendGet } from "./requestSender";

/**
 * @returns {map} { status: int, data: [{ id: string, name: string }, ...] }
 */
export async function getAllJobTitlesRequest() {
  return await sendGet("job_title/get_all");
}
