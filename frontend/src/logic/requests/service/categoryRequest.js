import { sendGet } from "../requestSender";

/**
 * @returns {map} { status: int, data: [ id: string, name: string, subcategries: [ id: string, name: string] ] }
 */
export async function getAllCategoryInfo() {
  return await sendGet("category/get_all_info");
}
