import { DefaultTheme } from "vitepress"

export default [
  {
    text: "Technical Information",
    link: "/technical/",
    items: [
      {
        text: "Fabric Loader",
        link: "/technical/fabric-loader"
      },
      {
        text: "Fabric Loom",
        link: "/technical/fabric-loom"
      },
      {
        text: "Yarn",
        link: "/technical/yarn"
      },
      {
        text: "Entrypoints",
        link: "/technical/entrypoints"
      },
    ]
  },
  {
    text: 'Specifications',
    items: [
      {
        text: 'fabric.mod.json',
        link: '/technical/specifications/fabric-mod-json'
      },
      {
        text: 'Mapping Formats',
        items: [
          {
            text: 'Enigma',
            link: '/technical/specifications/enigma'
          },
          {
            text: 'Tiny v1',
            link: '/technical/specifications/tiny/v1'
          },
          {
            text: 'Tiny v2',
            link: '/technical/specifications/tiny/v2'
          }
        ]
      }
    ]
  },
] as DefaultTheme.SidebarItem[];