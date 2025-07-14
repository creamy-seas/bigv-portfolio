import React from "react";
import Overview from "./Overview";
import Highlights from "./Highlights";
import SeasonTable from "./SeasonTable";
import GameGraph from "./GameGraph";

const LandingPage: React.FC = () => (
  <>
    <Overview />
    <div className="text-center">
      <a
        href="/gallery"
        className="
          text-myflame text-3xl font-bold
          underline decoration-myflame underline-offset-4
          hover:text-myflame/80
        "
      >
        View Gallery
      </a>
    </div>
    <div className="grid grid-cols-1 md:grid-cols-2 gap-2">
      <Highlights />
      <SeasonTable />
    </div>
    <GameGraph />
  </>
);

export default LandingPage;
