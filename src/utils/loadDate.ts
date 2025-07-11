export function evalDynamicDates(birth: string): {
  age: number;
  season: string;
} {
  const now = new Date();

  // Birthday
  const b = new Date(birth);
  let age = now.getFullYear() - b.getFullYear();
  const m = now.getMonth() - b.getMonth();
  if (m < 0 || (m === 0 && now.getDate() < b.getDate())) age--;

  // Current season
  const year = now.getFullYear();
  const septFirst = new Date(year, 8, 1);
  const startYear = now < septFirst ? year - 1 : year;
  const endYear = startYear + 1;

  return {
    age,
    season: `${startYear}-${endYear}`,
  };
}
