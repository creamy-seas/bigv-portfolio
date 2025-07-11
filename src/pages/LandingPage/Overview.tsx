import React, { useEffect, useMemo, useState } from "react";
import profileUrl from "../../assets/profile.jpeg";
import { AGE, SEASON, TEAM } from "../../config";
import { loadGameStats, type GameStats } from "../../utils/loadGameStats";
import { loadTimeLog, type TimeLog } from "../../utils/loadTimeLog";
import { accumulate } from "../../utils/accumulate";

const Overview: React.FC = () => {
  const [gameStats, setGameStats] = useState<GameStats[] | null>(null);
  useEffect(() => {
    loadGameStats().then(setGameStats);
  }, []);

  const [timeLog, setTimeLog] = useState<TimeLog[] | null>(null);
  useEffect(() => {
    loadTimeLog().then(setTimeLog);
  }, []);

  // Derived data
  const games = gameStats?.length || "";
  const goals = useMemo(() => {
    if (gameStats === null) return "";
    return accumulate(gameStats, ["goals"]).at(-1)?.goals;
  }, [gameStats]);
  const hours = useMemo(() => {
    if (timeLog === null) return "";
    return accumulate(timeLog, ["timeOnIceH"]).at(-1)?.timeOnIceH;
  }, [timeLog]);

  return (
    <section className="flex flex-col md:flex-row items-center bg-bg p-6 rounded-lg shadow-lg">
      <img
        src={profileUrl}
        alt="Player Photo"
        className="
                    block
                    order-first
   w-full        /* full width on mobile */
   h-auto       /* preserve aspect ratio */
   rounded-[5%]
   mb-4

   md:order-last
   md:mb-0
   md:w-[360px]
   md:h-auto
                            flex-shrink-0       /* don’t let flexbox squash or overlap it */"
      />
      <div className="flex-1 space-y-2 md:space-y-4">
        <h2 className="text-2xl font-bold">Viktor Antonov</h2>
        <dl className="grid grid-cols-2 gap-y-2">
          {[
            ["Age", AGE],
            ["Team", TEAM],
            ["Season", SEASON],
            ["Career Games", games],
            ["Career Goals", goals],
            ["Career Hours", hours],
          ].map(([label, val]) => (
            <React.Fragment key={label}>
              <dt className="font-semibold text-myflame">{label}:</dt>
              <dd>{val}</dd>
            </React.Fragment>
          ))}
        </dl>
      </div>
    </section>
  );
};

export default Overview;
