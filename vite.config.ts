import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import tailwindcss from "@tailwindcss/vite";
import vike from "vike/plugin";

export default defineConfig(({ command }) => {
  return {
    base: command === "build" ? "/bigv-portfolio/" : "/",
    plugins: [react(), tailwindcss(), vike()],
  };
});
