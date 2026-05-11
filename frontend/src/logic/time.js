/**
 * @param {Date} sendAtDate
 * @returns {int} updateInterval
 */
export function sendAtDateToRUString(sendAtDate, setTime) {
  function isFirstOption(number) {
    // from 2 to 4 and from 11 to 14 => true
    return (number % 10 > 1 && number % 10 < 5) || (number > 10 && number < 15);
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
    setTime("сейчас");
    return SECOND * 30;
  } else if (distance < MINUTE) {
    setTime("30 секунд назад");
    return SECOND * 30;
  } else if (distance < HOUR) {
    const minutes = Math.floor((distance % HOUR) / MINUTE);
    setTime(
      minutes === 1
        ? "1 минуту назад"
        : isFirstOption(minutes)
          ? minutes + " минуты назад"
          : minutes + " минут назад",
    );
    return MINUTE;
  } else if (distance < DAY) {
    const hours = Math.floor((distance % DAY) / HOUR);
    setTime(
      hours === 1
        ? "1 час назад"
        : isFirstOption(hours)
          ? hours + " часа назад"
          : hours + " часов назад",
    );
    return HOUR;
  } else if (distance < WEEK) {
    const days = Math.floor(distance / DAY);
    setTime(
      days === 1
        ? "1 день назад"
        : isFirstOption(days)
          ? days + " дня назад"
          : days + " дней назад",
    );
    return -1;
  } else if (distance < MONTH) {
    const weeks = Math.floor(distance / WEEK);
    setTime(
      weeks === 1
        ? "1 неделя назад"
        : isFirstOption(weeks)
          ? weeks + " недели назад"
          : weeks + " недель назад",
    );
    return -1;
  } else if (distance < YEAR) {
    const months = Math.floor(distance / MONTH);
    setTime(
      months === 1
        ? "1 месяц назад"
        : isFirstOption(months)
          ? months + " месяца назад"
          : months + " месяцев назад",
    );
    return -1;
  } else {
    const years = Math.floor(distance / YEAR);
    setTime(
      years === 1
        ? "1 год назад"
        : isFirstOption(years)
          ? years + " года назад"
          : years + " лет назад",
    );
    return -1;
  }
}

export function daysBetween(date1, date2) {
  const ONE_DAY = 1000 * 60 * 60 * 24;
  const differenceMs = Math.abs(date1 - date2);
  return Math.round(differenceMs / ONE_DAY);
}

export function now() {
  return Date.now();
}

export function fromIsoDateToDate(isoStr) {
  const date = new Date(isoStr);
  return date.toLocaleString("ru-RU", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
  });
}

export function fromIsoDateToYear(isoStr) {
  const date = new Date(isoStr);
  return date.toLocaleString("ru-RU", {
    year: "numeric",
  });
}

export function fromIsoDate(isoStr) {
  const date = new Date(isoStr);
  return date.toLocaleString("ru-RU", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
  });
}

export function calculateDays(startDate, endDate) {
  const oneDay = 24 * 60 * 60 * 1000;
  const firstDate = new Date(startDate)
  const secondDate = new Date(endDate)
  return Math.round(Math.abs((firstDate - secondDate) / oneDay));
}

export function getDayRUString(count) {
  const pr = new Intl.PluralRules('ru-RU');
  const suffixes = {
    one: 'ень',
    few: 'ня',
    many: 'ней'
  };

  return `${count} д${suffixes[pr.select(count)]}`;
}

export function isDeadlineHasPassed(deadline) {
  const deadlineDate = new Date(deadline);
  const now = new Date(Date.now());

  return deadlineDate < now;
}
