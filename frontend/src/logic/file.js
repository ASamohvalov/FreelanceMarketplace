export function getFileRUString(count) {
  const pr = new Intl.PluralRules('ru-RU');
  const suffixes = {
    one: '',
    few: 'ла',
    many: 'лов'
  };

  return `${count} фай${suffixes[pr.select(count)]}`;
}
