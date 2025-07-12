import Papa from "papaparse";
import { evalDynamicDates } from "./loadDate";

export interface Highlight {
  age: number;
  date: string;
  highlight: string;
  link?: string;
}

let dataPromise: Promise<Highlight[]> | null = null;

export function loadHighlights(): Promise<Highlight[]> {
  if (!dataPromise) {
    dataPromise = new Promise((resolve) =>
      Papa.parse<Omit<Highlight, "age">>("data/highlights.csv", {
        header: true,
        download: true,
        dynamicTyping: true,
        skipEmptyLines: true,
        complete: ({ data }) =>
          resolve(
            data.map((row) => ({
              ...row,
              age: evalDynamicDates("2019-10-15", new Date(row.date)).age,
            })),
          ),
      }),
    );
  }
  return dataPromise;
}
