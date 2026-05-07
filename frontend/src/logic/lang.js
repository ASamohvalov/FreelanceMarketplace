export function reviewToRu(count) {
  const pr = new Intl.PluralRules('ru-RU');
  const suffixes = {
    one: 'в',
    few: 'ва',
    many: 'вов'
  };

  return `${count} отзы${suffixes[pr.select(count)]}`;
}
