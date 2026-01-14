import { sendAuthPost } from "../requestSender";

export async function createServiceRequest(title, description, price) {
  await sendAuthPost("service/create", {
    title: title,
    description: description,
    price: price,
  });
}
