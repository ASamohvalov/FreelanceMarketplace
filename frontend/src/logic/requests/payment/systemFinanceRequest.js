import { sendAuthGet, sendAuthPatch } from "../requestSender";

export async function getSystemFinanceStatisticRequest() {
  return await sendAuthGet("system_finance/get/statistic");
}

export async function setPointRateRequest(pointRate) {
  return await sendAuthPatch("system_finance/set/point_rate", {
    pointRate: pointRate,
  });
}
