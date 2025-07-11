import React, { useEffect, useMemo, useState } from "react";
import {
  ResponsiveContainer,
  LineChart,
  Line,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
} from "recharts";
import { loadGameStats, type GameStats } from "../../utils/loadGameStats";
import { accumulate } from "../../utils/accumulate";

const GameGraph: React.FC = () => {
  const [data, setData] = useState<GameStats[]>([]);
  const [cumulative, setCumulative] = useState(true);

  useEffect(() => {
    loadGameStats().then(setData);
  }, []);

  // Tools allowing cumulative sums to be evaluated
  const graphFields: (keyof GameStats)[] = [
    "goals",
    "passes",
    "shots",
    "carries",
    "takeaways",
  ];
  const graphData = useMemo(() => {
    if (!cumulative) return data;
    return accumulate(data, graphFields);
  }, [data, cumulative]);

  return (
    <div className="p-2 max-w-4xl mx-auto">
      <h2 className="text-2xl font-semibold text-myflame mb-4">
        📈 Game Stats
      </h2>

      <div className="h-96">
        <ResponsiveContainer width="100%" height="100%">
          <LineChart data={graphData}>
            <XAxis
              dataKey="gameNo"
              label={{ value: "Game #", position: "insideBottom", offset: -5 }}
            />
            <YAxis />
            <Tooltip />
            <Legend verticalAlign="top" height={36} />
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
          className="px-4 py-2 bg-myflame text-bg rounded hover:bg-fg transition"
        >
          {cumulative ? "Show Per-Game" : "Show Cumulative"}
        </button>
      </div>
    </div>
  );
};

export default GameGraph;
