export function codeBlockFullScreen(root: any){
  root.children.push({
    type: "element",
    tagName: "button",
    properties: {
      class: "full-screen-button",
      title: "Full Screen",
    },
    children: [],
  });
}
