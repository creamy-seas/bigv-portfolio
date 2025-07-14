import React from "react";

/**
 * Consistent header across all pages
 */
const Header = ({ children }: { children: React.ReactNode }) => (
  <>
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
    <main className="container mx-auto px-4">{children}</main>
  </>
);

export default Header;
