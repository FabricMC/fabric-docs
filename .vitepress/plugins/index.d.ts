declare module "markdown-it-vuepress-code-snippet-enhanced" {
  const snippetPlugin: (md: any) => void;
  export default snippetPlugin;
}

declare module "virtual:fabric-docs:latest-version" {
  const latestVersion: string;
  export default latestVersion;
}
