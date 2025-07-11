import React from "react";

interface Highlight {
  age: string;
  date: string;
  event: string;
}

const Highlights: React.FC = () => {
  // TODO: read from highlights.csv
  const highlights: Highlight[] = [
    {
      age: "2",
      date: "September 2021",
      event: "First time on skates",
    },
    {
      age: "3",
      date: "September 2022",
      event: "First independent skating",
    },
    {
      age: "4",
      date: "September 2024",
      event: "First game",
    },
    {
      age: "4",
      date: "September 2024",
      event: "Spirit of the game award",
    },
    {
      age: "5",
      date: "April 2025",
      event: "First goal",
    },
  ];

  return (
    <section className="bg-bg/80 p-6 rounded-lg overflow-auto">
      <h2 className="text-2xl font-semibold text-myflame mb-4">
        🎉 Highlights
      </h2>
      <ul className="p-4">
        {highlights.map((step, i) => (
          <li key={i} className="mb-2">
            <div className="font-semibold">
              Age {step.age} — <span className="italic">{step.date}</span>
            </div>
            <div className="text-sm">{step.event}</div>
          </li>
        ))}
      </ul>
    </section>
  );
};

export default Highlights;
