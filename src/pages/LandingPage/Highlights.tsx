import React, { useEffect, useState } from "react";
import { loadHighlights, type Highlight } from "../../utils/loadHighlights";

const Highlights: React.FC = () => {
  const [highlights, setHighlights] = useState<Highlight[]>([]);
  useEffect(() => {
    loadHighlights().then(setHighlights);
  }, []);

  return (
    <section className="p-2 rounded-lg overflow-auto">
      <h2 className="text-2xl font-semibold text-myflame mb-4">
        🎉 Highlights
      </h2>
      <ul className="p-4">
        {highlights.map((h, i) => (
          <li key={i} className="mb-2">
            <div className="font-semibold">
              Age {h.age} — <span className="italic">{h.date}</span>
            </div>
            <div className="text-sm">{h.highlight}</div>
          </li>
        ))}
      </ul>
    </section>
  );
};

export default Highlights;
