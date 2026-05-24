import { useEffect } from "react";
import { useState } from "react";
import { getSystemFinanceStatisticRequest, setPointRateRequest } from "../../../logic/requests/payment/systemFinanceRequest";
import "./css/admin_pages.css";
import { fromKopeck } from "../../../logic/lang";

export default function AdminPanelFinancePage() {
  const [message, setMessage] = useState({});

  const [currencyRate, setCurrencyRate] = useState(0);
  const [totalServiceEarnings, setTotalServiceEarnings] = useState(0);
  const [totalPoints, setTotalPoints] = useState(0);

  const [inputCurrencyRate, setInputCurrencyRate] = useState(0);

  const maxPossibleRate = totalPoints === 0 ? totalServiceEarnings : (totalServiceEarnings / totalPoints);

  useEffect(() => {
    (async () => {
      const response = await getSystemFinanceStatisticRequest();
      if (response.status !== 200) {
        setMessage({
          message: `Ошибка ${response.status}, попробуйте позже`,
          type: "danger",
        });
        return;
      }
      setCurrencyRate(response.data.currencyRate);
      setTotalServiceEarnings(response.data.totalServiceEarnings);
      setTotalPoints(response.data.totalPoints);
    })();
  }, []);

  const changePointRate = async () => {
    if (currencyRate < 0) {
      setMessage({
        message: "Курс не должен быть отрицательным",
        type: "danger",
      });
      return;
    }
    if (currencyRate > maxPossibleRate) {
      setMessage({
        message: `Превышена максимально доступная стоимость балла: ${fromKopeck(maxPossibleRate)}`,
        type: "danger",
      });
      return;
    }

    const response = await setPointRateRequest(currencyRate);
    if (response.status !== 200) {
      setMessage({
        message: `Ошибка ${response.status}, попробуйте позже`,
        type: "danger",
      });
      return;
    }
    setMessage({
      message: "Курс успешно изменен",
      type: "success",
    });
  };

  return (
    <main className="col-10 p-4 admin_main-flex">
      {message && (
        <div className={`alert alert-${message.type}`} role="alert">
          {message.message}
        </div>
      )}
      <div className="admin-card mb-4">
        <h5 className="mb-3">Информация о финансах</h5>

        <div className="d-flex justify-content-between mb-3">
          <table className="table">
            <thead>
              <tr>
                <th>Текущий курс балла</th>
                <th>Общая прибыль сервиса</th>
                <th>Чистая прибыль (при снятии всех баллов)</th>
                <th>Количество активных баллов исполнителей</th>
              </tr>
            </thead>

            <tbody>
              <tr>
                <td>{fromKopeck(currencyRate)}</td>
                <td>{fromKopeck(totalServiceEarnings)}</td>
                <td>
                  {(totalPoints === 0 || currencyRate === 0)
                    ? fromKopeck(totalServiceEarnings)
                    : fromKopeck(totalServiceEarnings - (totalPoints * currencyRate))}
                </td>
                <td>{totalPoints}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <div className="admin-card mb-4">
        <h5 className="mb-3">Настройка курса</h5>

        <div className="alert alert-primary">
          <ul>
            <li>
              Курс не должен превышать количества баллов на общую прибыль
              сервиса.
            </li>
            <li>
              Рекомендуемое значение — 30% от общего заработка, т. е. 30% общего
              заработка: {fromKopeck((totalServiceEarnings * 30) / 100)}, следовательно,
              для уплаты всех баллов курс должен составлять:{" "}
              {totalPoints === 0
                ? 0
                : fromKopeck((totalServiceEarnings * 30) / 100 / totalPoints)}{" "}
              за балл.
            </li>
            <li>
              Максимально возможная стоимость балла: {fromKopeck(maxPossibleRate)}.
            </li>
            <li>
              Пользователь может снять заработанные баллы со счета компании в
              любое время.
            </li>
          </ul>
        </div>

        <label htmlFor="currentRateInput">Стоимость балла в рублях</label>
        <input
          className="form-control"
          type="number"
          id="currentRateInput"
          value={inputCurrencyRate}
          onChange={(e) => {
            setInputCurrencyRate(e.target.value)
            setCurrencyRate(e.target.value * 100)
          }}
        />
        <button
          className="btn btn-primary mt-3"
          onClick={changePointRate}
        >Изменить курс</button>
      </div>
    </main>
  );
}
