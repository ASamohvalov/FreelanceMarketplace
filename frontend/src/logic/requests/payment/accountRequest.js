import { sendAuthGet } from "../requestSender";

export async function getBalanceRequest() {
  return await sendAuthGet("account/get/balance");
}

export async function getIncomeTransfersRequest() {
  return await sendAuthGet("account/get/transfer/income");
}

export async function getExpenseTransfersRequest() {
  return await sendAuthGet("account/get/transfer/expense");
}

export async function getCurrentPointRateRequest() {
  return await sendAuthGet("account/get/current_point_rate");
}
