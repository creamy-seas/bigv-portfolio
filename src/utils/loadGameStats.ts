import { parse } from "papaparse";

export interface GameStats {
  season: string;
  date: string;
  location: string;
  name: string;
  timeOnIceM: number;
  goals: number;
  passes: number;
  shots: number;
  carries: number;
  takeaways: number;
  gameNo: number;
}

let dataPromise: Promise<GameStats[]> | null = null;

/**
 * Game stats read row by row into a list
 */
export function loadGameStats(): Promise<GameStats[]> {
  if (!dataPromise) {
    dataPromise = new Promise((resolve) =>
      parse<Omit<GameStats, "gameNo">>("data/game_stats.csv", {
        header: true,
        download: true,
        dynamicTyping: true,
        skipEmptyLines: true,
        complete: ({ data: raw }) => {
          resolve(
            raw.map((row, i) => ({
              gameNo: i + 1,
              ...row,
            })),
          );
        },
      }),
    );
  }
  return dataPromise;
}
