import { useEffect, useMemo, useState } from "react";
import Papa from "papaparse";
import {
  ResponsiveContainer,
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
} from "recharts";

interface RawStatsRow {
  Season: string;
  Date: string;
  Location: string;
  Name: string;
  "Time on ice (minutes)": number;
  Goals: number;
  Passes: number;
  Shots: number;
  Carries: number;
  Takeaways: number;
}

interface GameStats {
  game: number;
  timeOnIce: number;
  goals: number;
  passes: number;
  shots: number;
  carries: number;
  takeaways: number;
}

function GameGraph() {
  const [data, setData] = useState<GameStats[]>([]);
  const [cumulative, setCumulative] = useState(true);

  useEffect(() => {
    Papa.parse<RawStatsRow>("data/game_log.csv", {
      header: true,
      download: true,
      dynamicTyping: true,
      complete: ({ data: raw }) => {
        const formatted = raw.map((row, i) => ({
          game: i + 1,
          timeOnIce: row["Time on ice (minutes)"],
          goals: row.Goals,
          passes: row.Passes,
          shots: row.Shots,
          carries: row.Carries,
          takeaways: row.Takeaways,
        }));
        setData(formatted);
      },
    });
  }, []);

  // Memoize the transformed data to avoid recomputing on every render
  const displayedData = useMemo(() => {
    if (!cumulative) return data;

    let acc = {
      timeOnIce: 0,
      goals: 0,
      passes: 0,
      shots: 0,
      carries: 0,
      takeaways: 0,
    };

    return data.map((d) => {
      acc = {
        timeOnIce: acc.timeOnIce + d.timeOnIce,
        goals: acc.goals + d.goals,
        passes: acc.passes + d.passes,
        shots: acc.shots + d.shots,
        carries: acc.carries + d.carries,
        takeaways: acc.takeaways + d.takeaways,
      };
      return { ...d, ...acc };
    });
  }, [data, cumulative]);

  return (
    <div className="p-4 max-w-4xl mx-auto">
      <h2 className="text-2xl font-semibold text-myflame mb-4">
        📈 Game Stats
      </h2>

      <div className="h-96">
        <ResponsiveContainer width="100%" height="100%">
          <LineChart data={displayedData}>
            <XAxis
              dataKey="game"
              label={{ value: "Game #", position: "insideBottom", offset: -5 }}
            />
            <YAxis />
            <Tooltip />
            <Legend verticalAlign="top" height={36} />
            {/* <Line type="monotone" dataKey="timeOnIce" name="Time on Ice" stroke="#8884d8" dot={false} /> */}
            <Line
              type="monotone"
              dataKey="goals"
              name="Goals"
              stroke="#eec900"
            />
            <Line
              type="monotone"
              dataKey="shots"
              name="Shots"
              stroke="#ff7300"
            />
            <Line
              type="monotone"
              dataKey="passes"
              name="Passes"
              stroke="#00bfff"
            />
            <Line
              type="monotone"
              dataKey="carries"
              name="Carries"
              stroke="#82ca9d"
            />
            <Line
              type="monotone"
              dataKey="takeaways"
              name="Takeaways"
              stroke="#a83232"
            />
          </LineChart>
        </ResponsiveContainer>
      </div>

      <div className="flex justify-center mt-4">
        <button
          onClick={() => setCumulative(!cumulative)}
          className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
        >
          {cumulative ? "Show Per-Game" : "Show Cumulative"}
        </button>
      </div>
    </div>
  );
}

export default GameGraph;
