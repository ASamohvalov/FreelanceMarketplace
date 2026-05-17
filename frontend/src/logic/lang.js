export function reviewToRu(count) {
  const pr = new Intl.PluralRules('ru-RU');
  const suffixes = {
    one: 'в',
    few: 'ва',
    many: 'вов'
  };

  return `${count} отзы${suffixes[pr.select(count)]}`;
}


export function fromKopeck(kopeck) {
  const rubles = kopeck / 100;
  const formatter = new Intl.NumberFormat('ru-RU', {
    style: 'currency',
    currency: 'RUB',
    minimumFractionDigits: 2
  });
  return formatter.format(rubles);
}
