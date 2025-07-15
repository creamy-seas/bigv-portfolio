module.exports = {
  content: [
    "./resources/public/index.html",
    "./src/cljs/**/*.{cljs,cljc,html}"
  ],
  plugins: [require("daisyui")],
  daisyui: {
    themes: false, // skip and save space
    logs: false,
  },
};
