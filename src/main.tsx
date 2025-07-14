import React from "react";
import { createRoot } from "react-dom/client";
import App from "./App";
import "./style.css";

/**
 * Bootstrapping react into DOM
 */
const container = document.getElementById("root")!;
createRoot(container).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
);
