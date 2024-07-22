import { Fabric } from "../types";

export default [
  {
    text: "develop.title",
    link: "/develop/",
    collapsed: false,
    items: [
      {
        text: "Fabric API GitHub",
        translatable: false,
        link: "https://github.com/FabricMC/fabric",
      },
      {
        text: "Yarn GitHub",
        translatable: false,
        link: "https://github.com/FabricMC/yarn",
      },
      {
        text: "Loom GitHub",
        translatable: false,
        link: "https://github.com/FabricMC/fabric-loom",
      },
    ],
  },
  {
    text: "develop.gettingStarted",
    collapsed: false,
    items: [
      {
        text: "develop.gettingStarted.introduction",
        link: "/develop/getting-started/introduction-to-fabric-and-modding",
      },
      {
        text: "develop.gettingStarted.devEnvSetup",
        link: "/develop/getting-started/setting-up-a-development-environment",
      },
      {
        text: "develop.gettingStarted.creatingProject",
        link: "/develop/getting-started/creating-a-project",
      },
      {
        text: "develop.gettingStarted.projectStructure",
        link: "/develop/getting-started/project-structure",
      },
      {
        text: "develop.gettingStarted.launchGame",
        link: "/develop/getting-started/launching-the-game"
      }
    ]
  },
  {
    text: "develop.items",
    collapsed: true,
    items: [
      {
        text: "develop.items.first-item",
        link: "/develop/items/first-item"
      },
      {
        text: "develop.items.food",
        link: "/develop/items/food"
      },
      {
        text: "develop.items.custom-tools",
        link: "/develop/items/custom-tools"
      },
      {
        text: "develop.items.custom-armor",
        link: "/develop/items/custom-armor"
      },
      {
        text: "develop.items.custom-item-groups",
        link: "/develop/items/custom-item-groups"
      },
      {
        text: "develop.items.custom-item-interactions",
        link: "/develop/items/custom-item-interactions"
      },
      {
        text: "develop.items.custom-data-components",
        link: "/develop/items/custom-data-components"
      },
      {
        text: "develop.items.potions",
        link: "/develop/items/potions",
      },
    ],
  },
  {
    text: "develop.blocks",
    collapsed: true,
    items: [
      {
        text: "develop.blocks.first-block",
        link: "/develop/blocks/first-block"
      },
      {
        text: "develop.blocks.blockstates",
        link: "/develop/blocks/blockstates"
      }
    ]
  },
  {
    text: "develop.entities",
    collapsed: true,
    items: [
      {
        text: "develop.entities.effects",
        link: "/develop/entities/effects",
      },
      {
        text: "develop.entities.damage-types",
        link: "/develop/entities/damage-types",
      },
    ],
  },
  {
    text: "develop.sounds",
    collapsed: true,
    items: [
      {
        text: "develop.sounds.using-sounds",
        link: "/develop/sounds/using-sounds",
      },
      {
        text: "develop.sounds.custom",
        link: "/develop/sounds/custom",
      },
    ],
  },
  {
    text: "develop.commands",
    collapsed: true,
    items: [
      {
        text: "develop.commands.basics",
        link: "/develop/commands/basics",
      },
      {
        text: "develop.commands.arguments",
        link: "/develop/commands/arguments",
      },
      {
        text: "develop.commands.suggestions",
        link: "/develop/commands/suggestions",
      },
    ],
  },
  {
    text: "develop.rendering",
    collapsed: true,
    items: [
      {
        text: "develop.rendering.basicConcepts",
        link: "/develop/rendering/basic-concepts",
      },
      {
        text: "develop.rendering.drawContext",
        link: "/develop/rendering/draw-context",
      },
      {
        text: "develop.rendering.hud",
        link: "/develop/rendering/hud",
      },
      {
        text: "develop.rendering.gui",
        items: [
          {
            text: "develop.rendering.gui.customScreens",
            link: "/develop/rendering/gui/custom-screens",
          },
          {
            text: "develop.rendering.gui.customWidgets",
            link: "/develop/rendering/gui/custom-widgets",
          },
        ],
      },
      {
        text: "develop.rendering.particles",
        items: [
          {
            text: "develop.rendering.particles.creatingParticles",
            link: "/develop/rendering/particles/creating-particles",
          },
        ],
      },
    ],
  },
  {
    text: "develop.misc",
    collapsed: true,
    items: [
      {
        text: "develop.misc.codecs",
        link: "/develop/codecs",
      },
      {
        text: "develop.misc.events",
        link: "/develop/events"
      },
      {
        text: "develop.misc.text-and-translations",
        link: "/develop/text-and-translations"
      },
      {
        text: "develop.misc.ideTipsAndTricks",
        link: "/develop/ide-tips-and-tricks"
      }
    ]
  }
] as Fabric.SidebarItem[];