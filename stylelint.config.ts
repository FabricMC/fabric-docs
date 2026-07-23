import githubActionsFormatter from "@csstools/stylelint-formatter-github";
import * as process from "node:process";
import type { Config } from "stylelint";

export default {
  formatter: process.env.GITHUB_ACTIONS ? githubActionsFormatter : undefined,

  extends: [
    "stylelint-config-standard",
    // TODO: stylelint-config-modern - too many changes for now
    "stylelint-config-clean-order",
    "stylelint-config-recommended-vue",
  ],

  overrides: [{ files: ["**/*.md"], customSyntax: "postcss-html" }],

  plugins: ["stylelint-plugin-use-baseline", "stylelint-use-nesting"],

  referenceFiles: ["node_modules/vitepress/dist/client/theme-default/styles/vars.css"],

  rules: {
    "alpha-value-notation": "percentage",
    "color-named": "never",
    // TODO: color-no-hex
    "csstools/use-nesting": "always",
    "declaration-no-important": [true, { severity: "warning" }],
    "font-weight-notation": "named-where-possible",
    "no-unknown-animations": true,
    "no-unknown-custom-media": true,
    "no-unknown-custom-properties": true,
    "plugin/use-baseline": [true, { ignoreSelectors: ["nesting", "has"], severity: "warning" }],
    "relative-selector-nesting-notation": "implicit",
    "selector-class-pattern": null,
    "selector-no-invalid": true,
  },
} satisfies Config;
