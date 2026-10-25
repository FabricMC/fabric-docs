declare module "markdown-it-vuepress-code-snippet-enhanced" {
  const snippetPlugin: (md: any) => void;
  export default snippetPlugin;
}

declare module "*.css" {
  const content: any;
  export default content;
}
