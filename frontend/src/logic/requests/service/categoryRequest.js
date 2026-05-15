import { sendAuthDelete, sendAuthPost, sendGet } from "../requestSender";

/**
 * @returns {map} { status: int, data: [ id: string, name: string, subcategries: [ id: string, name: string] ] }
 */
export async function getAllCategoryInfo() {
  return await sendGet("category/get_all_info");
}

export async function getAllCategories() {
  return await sendGet("category/get_all");
}

export async function createCategoryRequest(name) {
  return await sendAuthPost("category/create", {
    name: name,
  });
}

export async function createSubcategoryRequest(name, categoryId) {
  return await sendAuthPost("category/subcategory/create", {
    name: name,
    categoryId: categoryId,
  });
}

export async function deleteCategoryRequest(id) {
  return await sendAuthDelete("category/delete/" + id);
}

export async function deleteSubcategoryRequest(id) {
  return await sendAuthDelete("category/subcategory/delete/" + id);
}
