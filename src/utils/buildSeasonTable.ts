import type { GameStats } from "./loadGameStats";
import type { TimeLog } from "./loadTimeLog";

interface Season {
  season: string;
  games: number | null;
  goals: number | null;
  timeOnIceH: number;
}

/**
 * Bundle info for a season summary
 */
export function buildSeasonTable(
  gameStats: GameStats[],
  timeLog: TimeLog[],
): Array<Season> {
  const m = new Map<
    string,
    { games: number; goals: number; timeOnIceH: number }
  >();

  for (const { season, goals } of gameStats) {
    const e = m.get(season) ?? { games: 0, goals: 0, timeOnIceH: 0 };
    e.games++;
    e.goals += goals ?? 0;
    m.set(season, e);
  }

  for (const { season, timeOnIceH } of timeLog) {
    const e = m.get(season) ?? { games: 0, goals: 0, timeOnIceH: 0 };
    e.timeOnIceH += timeOnIceH;
    m.set(season, e);
  }

  return Array.from(m, ([season, { games, goals, timeOnIceH }]) => ({
    season,
    games: games > 0 ? games : null,
    goals: goals > 0 ? goals : null,
    timeOnIceH,
  })).sort((a, b) => parseInt(b.season, 10) - parseInt(a.season, 10));
}
