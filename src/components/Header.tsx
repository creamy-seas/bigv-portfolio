import React from "react";

/**
 * Consistent header across all pages
 */
const Header: React.FC = () => (
  <header style={{ textAlign: "center", margin: "2rem 0" }}>
    <a
      href="/"
      className="
            text-myflame text-3xl font-bold
            decoration-myflame underline-offset-4
            hover:text-myflame/80 select-none
          "
    >
      🏒 BigV Webpage 🏒
    </a>
  </header>
);

export default Header;
