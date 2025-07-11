/**
 * Utility to sum up fields after loading from csv files
 */
export function accumulate<T extends object, K extends keyof T>(
  data: T[],
  fields: K[],
): Array<T & Record<K, number>> {
  const acc = fields.reduce(
    (o, f) => ({ ...o, [f]: 0 }),
    {} as Record<K, number>,
  );

  return data.map((item) => {
    fields.forEach((f) => {
      acc[f] += (item[f] as unknown as number) ?? 0;
    });
    return { ...item, ...acc };
  });
}
