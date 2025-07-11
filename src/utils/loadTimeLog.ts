import Papa from "papaparse";

export interface TimeLog {
  season: string;
  timeOnIceH: number;
}

let dataPromise: Promise<TimeLog[]> | null = null;

export function loadTimeLog(): Promise<TimeLog[]> {
  if (!dataPromise) {
    dataPromise = new Promise((resolve) =>
      Papa.parse<TimeLog>("data/time_log.csv", {
        header: true,
        download: true,
        dynamicTyping: true,
        skipEmptyLines: true,
        complete: ({ data }) => resolve(data),
      }),
    );
  }
  return dataPromise;
}
