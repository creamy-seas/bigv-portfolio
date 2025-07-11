import React from "react";
import { Link } from "react-router-dom";
import Overview from "./Overview";
import Highlights from "./Highlights";
import SeasonTable from "./SeasonTable";
import GameGraph from "./GameGraph";

const LandingPage: React.FC = () => (
  <div className="container mx-auto px-4 space-y-8">
    <Overview />
    <div className="text-center">
      <Link
        to="/gallery"
        className="
          text-myflame text-3xl font-bold
          underline decoration-myflame underline-offset-4
          hover:text-myflame/80
        "
      >
        View Gallery
      </Link>
    </div>
    <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
      <Highlights />
      <SeasonTable />
    </div>
    <GameGraph />
  </div>
);

export default LandingPage;
