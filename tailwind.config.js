import { defineConfig } from "tailwindcss";
import daisyui from "daisyui";

export default defineConfig({
  content: ["./index.html", "./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      // your customizations…
    },
  },
  plugins: [daisyui],
  daisyui: {
    themes: false, // skip and save space
    logs: false,
  },
});
