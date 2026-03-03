/**
 * @param {Date} sendAtDate
 * @returns {array} [ str: string, updateInterval: int ]
 */
export function sendAtDateToRUString(sendAtDate) {
  function isFirstOption(number) { // from 2 to 4 and from 11 to 14 => true
    return number % 10 > 1 && number % 10 < 5 || number > 10 && number < 15;
  }

  const SECOND = 1000;
  const MINUTE = 1000 * 60;
  const HOUR = 1000 * 60 * 60;
  const DAY = 1000 * 60 * 60 * 24;
  const WEEK = 1000 * 60 * 60 * 24 * 7;
  const MONTH = 1000 * 60 * 60 * 24 * 30;
  const YEAR = 1000 * 60 * 60 * 24 * 365;

  const currentTime = new Date().getTime();
  const distance = currentTime - sendAtDate;

  if (distance < SECOND * 30) {
    return [ "сейчас", SECOND * 30 ];
  } else if (distance < MINUTE) {
    return [ "30 секунд назад", SECOND * 30 ];
  } else if (distance < HOUR) {
    const minutes = Math.floor((distance % HOUR) / MINUTE);
    return [
      minutes === 1 ? "минута назад" : (
        isFirstOption(minutes) ? minutes + " минуты назад" : minutes + " минут назад"
      ),
      MINUTE
    ];
  } else if (distance < DAY) {
    const hours = Math.floor((distance % DAY) / HOUR);
    return [
      hours === 1 ? "1 час назад" : (
        isFirstOption(hours) ? hours + " часа назад" : hours + " часов назад"
      ),
      HOUR
    ]
  } else if (distance < WEEK) {
    const days = Math.floor(distance / DAY);
    return [
      days === 1 ? "1 день назад" : (
        isFirstOption(days) ? days + " дня назад" : days + " дней назад"
      ),
      -1
    ]
  } else if (distance < MONTH) {
    const weeks = Math.floor(distance / WEEK);
    return [
      weeks === 1 ? "1 неделя назад" : (
        isFirstOption(weeks) ? weeks + " недели назад" : weeks + " недель назад"
      ),
      -1
    ]
  } else if (distance < YEAR) {
    const months = Math.floor(distance / MONTH);
    return [
      months === 1 ? "1 месяц назад" : (
        isFirstOption(months) ? months + " месяца назад" : months + " месяцев назад"
      ),
      -1
    ]
  } else {
    const years = Math.floor(distance / YEAR);
    return [
      years === 1 ? "1 год назад" : (
        isFirstOption(years) ? years + " года назад" : years + " лет назад"
      ),
      -1
    ]
  }
}
