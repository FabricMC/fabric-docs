import { ExtendedSidebarItem } from "./utils";

export default [
  {
    text: "develop.title",
    link: "/develop/",
    collapsed: false,
    items: [
      {
        text: "Fabric API GitHub",
        disableTranslation: true,
        link: "https://github.com/FabricMC/fabric"
      },
      {
        text: "Yarn GitHub",
        disableTranslation: true,
        link: "https://github.com/FabricMC/yarn"
      },
      {
        text: "Loom GitHub",
        disableTranslation: true,
        link: "https://github.com/FabricMC/fabric-loom"
      }
    ]
  },
  {
    text: "develop.items",
    collapsed: true,
    items: [
      {
        text: "develop.items.potions",
        link: "/develop/items/potions",
      }
    ]
  },
  {
    text: "develop.entities",
    collapsed: true,
    items: [
      {
        text: "develop.entities.effects",
        link: "/develop/entities/effects"
      }
    ]
  },
  {
    text: "develop.commands",
    collapsed: true,
    items: [
      {
        text: "develop.commands.basics",
        link: "/develop/commands/basics"
      },
      {
        text: "develop.commands.arguments",
        link: "/develop/commands/arguments"
      },
      {
        text: "develop.commands.suggestions",
        link: "/develop/commands/suggestions"
      }
    ]
  },
  {
    text: "develop.rendering",
    collapsed: true,
    items: [
      {
        text: "develop.rendering.basicConcepts",
        link: "/develop/rendering/basic-concepts"
      },
      {
        text: "develop.rendering.drawContext",
        link: "/develop/rendering/draw-context"
      },
      {
        text: "develop.rendering.hud",
        link: "/develop/rendering/hud"
      },
      {
        text: "develop.rendering.gui",
        items: [
          {
            text: "develop.rendering.gui.customScreens",
            link: "/develop/rendering/gui/custom-screens"
          },
          {
            text: "develop.rendering.gui.customWidgets",
            link: "/develop/rendering/gui/custom-widgets"
          }
        ]
      },
      {
        text: "develop.rendering.particles",
        items: [
          {
            text: "develop.rendering.particles.creatingParticles",
            link: "/develop/rendering/particles/creating-particles"
          }
        ]
      },
    ]
  },
  {
    text: "develop.misc",
    collapsed: true,
    items: [
      {
        text: "develop.misc.codecs",
        link: "/develop/codecs"
      },
      {
        text: "develop.misc.events",
        link: "/develop/events"
      }
    ]
  }
] as ExtendedSidebarItem[];