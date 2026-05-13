import SuccessInfoComponent from "../../components/SuccessInfoComponent";

export default function SendOrderReportSuccessPage() {

  return (
    <SuccessInfoComponent
      title={"Отчёт успешно отправлен"}
      description={"Ожидайте проверки отчета заказчиком. Заказчик должен проверить отчет в течение 3 дней, в противном случае он будет считаться принятым."}
    />
  );
}
