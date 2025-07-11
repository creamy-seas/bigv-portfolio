import React from "react";

interface Season {
  season: string;
  games: string;
  goals: string;
  iceTime: number;
}

const SeasonTable: React.FC = () => {
  const data: Season[] = [
    {
      season: "2021-2022",
      games: "",
      goals: "",
      iceTime: 15,
    },
    {
      season: "2022-2023",
      games: "",
      goals: "",
      iceTime: 52,
    },
    {
      season: "2023-2024",
      games: "",
      goals: "",
      iceTime: 120,
    },
    {
      season: "2024-2025",
      games: "17",
      goals: "4",
      iceTime: 184,
    },
  ];

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
              <td>{r.iceTime}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </section>
  );
};

export default SeasonTable;
