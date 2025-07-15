module.exports = {
  content: [
    "./resources/public/index.html",
    "./src/cljs/**/*.{cljs,cljc,html}"
  ],
  theme: {
    extend: {
      colors: {
        myflame: "var(--color-myflame)",
        bg:      "var(--color-bg)",
        fg:      "var(--color-fg)",
      },
      spacing: {
        root: "var(--size-root)",
      }
    }
  },
  plugins: [
    require("daisyui"),
    function ({ addBase, theme }) {
      addBase({
        ":root": {
          "--color-myflame": theme("colors.sandybrown", "sandybrown"),
          "--color-bg":      theme("colors.gray.900", "#111"),
          "--color-fg":      theme("colors.gray.200", "#eee"),
          "--size-root":     "850px",
        }
      });
    }
  ],
  daisyui: {
    themes: false, // skip and save space
    logs: false,
  },
};
