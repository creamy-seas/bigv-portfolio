import React, { useEffect, useMemo, useState } from "react";
import { loadGameStats, type GameStats } from "../../utils/loadGameStats";
import { loadTimeLog, type TimeLog } from "../../utils/loadTimeLog";
import { buildSeasonTable } from "../../utils/buildSeasonTable";

const SeasonTable: React.FC = () => {
  const [gameStats, setGameStats] = useState<GameStats[] | null>(null);
  useEffect(() => {
    loadGameStats().then(setGameStats);
  }, []);

  const [timeLog, setTimeLog] = useState<TimeLog[] | null>(null);
  useEffect(() => {
    loadTimeLog().then(setTimeLog);
  }, []);

  const data = useMemo(() => {
    if (timeLog === null || gameStats === null) return [];
    return buildSeasonTable(gameStats, timeLog);
  }, [timeLog, gameStats]);

  return (
    <section className="p-2 rounded-lg overflow-auto">
      <h2 className="text-2xl font-semibold text-myflame mb-4">
        📊 Season Stats
      </h2>
      <table className="table table-compact w-full text-center">
        <thead className="bg-myflame/90">
          <tr>
            {["Season", "Games", "Goals", "Ice time (h)"].map((h) => (
              <th key={h} className="text-left">
                {h}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {data.map((r, i) => (
            <tr key={i} className="hover">
              <td className="font-medium text-myflame">{r.season}</td>
              <td>{r.games}</td>
              <td>{r.goals}</td>
              <td>{r.timeOnIceH}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </section>
  );
};

export default SeasonTable;
