import React from "react";
import { AGE, TEAM, CURRENT_SEASON } from "../config";
import profileUrl from "../assets/profile.jpeg";

const Landing: React.FC = () => {
  const games = 18;
  const goals = 4;
  const hours = 368;
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
            ["Season", CURRENT_SEASON],
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

export default Landing;
