import React from "react";
import { Link, Navigate, Route, Routes } from "react-router-dom";
import LandingPage from "./pages/LandingPage";
import GalleryPage from "./pages/GalleryPage";

const App: React.FC = () => (
  <>
    <div style={{ textAlign: "center", margin: "2rem 0" }}>
      <Link
        to="/"
        className="
          text-myflame text-3xl font-bold
          decoration-myflame underline-offset-4
          hover:text-myflame/80
        "
      >
        🏒 BigV Webpage 🏒
      </Link>
    </div>
    <Routes>
      <Route path="/" element={<LandingPage />} />
      <Route path="/gallery" element={<GalleryPage />} />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  </>
);

export default App;
