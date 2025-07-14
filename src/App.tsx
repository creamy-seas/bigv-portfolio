import React from "react";
import LandingPage from "./pages/LandingPage";
import GalleryPage from "./pages/GalleryPage";
import Header from "./components/Header";

/**
 * Top-level component
 */
const App: React.FC = () => {
  // Simple routing
  const base = import.meta.env.BASE_URL;
  const fullPath = window.location.pathname;
  const relativePath = fullPath.startsWith(base)
    ? fullPath.slice(base.length)
    : fullPath.replace(/^\//, "");

  const Page = relativePath.startsWith("gallery") ? GalleryPage : LandingPage;

  return (
    <>
      <Header />
      <Page />
    </>
  );
};

export default App;
