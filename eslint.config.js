/* eslint.config.js  – flat-config style (ESM) */
import js from "@eslint/js";
import tseslint from "@typescript-eslint/eslint-plugin";
import tsparser from "@typescript-eslint/parser";
import react from "eslint-plugin-react";
import hooks from "eslint-plugin-react-hooks";
import tailwind from "eslint-plugin-tailwindcss";
import prettier from "eslint-config-prettier";
import globals from "globals";

export default [
  js.configs.recommended,

  {
    files: ["**/*.ts", "**/*.tsx"],
    languageOptions: { parser: tsparser, globals: globals.browser },
    plugins: { "@typescript-eslint": tseslint },
    rules: {
      ...tseslint.configs.recommended.rules,
      "no-unused-vars": "off", // handled by TS
      "@typescript-eslint/no-unused-vars": "warn",
    },
  },

  {
    files: ["**/*.jsx", "**/*.tsx"],
    plugins: { react, "react-hooks": hooks }, // ← key must be "react-hooks"
    settings: { react: { version: "detect" } },
    rules: {
      ...react.configs.recommended.rules,
      ...hooks.configs.recommended.rules,
      "react/react-in-jsx-scope": "off",
    },
  },

  {
    plugins: { tailwind },
    rules: { "tailwindcss/no-custom-classname": "off" },
  },

  prettier, // turns off style rules so Prettier owns formatting
];
