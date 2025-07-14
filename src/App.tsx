import React from "react";
import LandingPage from "./pages/LandingPage";
import GalleryPage from "./pages/GalleryPage";

const App: React.FC = () => {
  // Simple page selector
  const Page = window.location.pathname.startsWith("/gallery")
    ? GalleryPage
    : LandingPage;

  return (
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

      <Page />
    </>
  );
};

export default App;
