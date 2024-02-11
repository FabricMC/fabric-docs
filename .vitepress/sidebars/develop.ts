import { DefaultTheme } from "vitepress";

export default [
  {
    text: "Developer Guides",
    link: "/develop/",
    collapsed: false,
    items: [
      {
        text: "Fabric API GitHub",
        link: "https://github.com/FabricMC/fabric"
      },
      {
        text: "Yarn GitHub",
        link: "https://github.com/FabricMC/yarn"
      },
      {
        text: "Loom GitHub",
        link: "https://github.com/FabricMC/fabric-loom"
      }
    ]
  },
  {
    text: "Items",
    collapsed: true,
    items: [
      {
        text: "Potions",
        link: "/develop/items/potions",
      }
    ]
  },
  {
    text: "Entities",
    collapsed: true,
    items: [
      {
        text: "Status Effects",
        link: "/develop/entities/effects"
      }
    ]
  },
  {
    text: "Commands",
    collapsed: true,
    items: [
      {
        text: "Creating Commands",
        link: "/develop/commands/basics"
      },
      {
        text: "Arguments",
        link: "/develop/commands/arguments"
      },
      {
        text: "Suggestions",
        link: "/develop/commands/suggestions"
      }
    ]
  },
  {
    text: "Rendering",
    collapsed: true,
    items: [
      {
        text: "Basic Rendering Concepts",
        link: "/develop/rendering/basic-concepts"
      },
      {
        text: "Using The Drawing Context",
        link: "/develop/rendering/draw-context"
      },
      {
        text: "Rendering In The Hud",
        link: "/develop/rendering/hud"
      },
      {
        text: "GUIs and Screens",
        items: [
          {
            text: "Custom Screens",
            link: "/develop/rendering/gui/custom-screens"
          },
          {
            text: "Custom Widgets",
            link: "/develop/rendering/gui/custom-widgets"
          }
        ]
      },
      {
        text: "Particles",
        items: [
          {
            text: "Creating Custom Particles",
            link: "/develop/rendering/particles/creating-particles"
          }
        ]
      },
    ]
  },
  {
    text: "Miscelaneous Pages",
    collapsed: true,
    items: [
      {
        text: "Codecs",
        link: "/develop/codecs"
      },
      {
        text: "Events",
        link: "/develop/events"
      }
    ]
  }
] as DefaultTheme.SidebarItem[];