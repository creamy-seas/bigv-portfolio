import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import tailwindcss from "@tailwindcss/vite";
import { visualizer } from "rollup-plugin-visualizer";

export default defineConfig(({ command }) => {
  return {
    base: command === "build" ? "/bigv-portfolio/" : "/",
    plugins: [
      react(),
      tailwindcss(),
      visualizer({ open: true, gzipSize: true }),
    ],
    build: {
      sourcemap: false,
    },
  };
});
