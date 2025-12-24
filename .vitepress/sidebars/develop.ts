import { Fabric } from "../types";

export default [
  {
    text: "develop.title",
    link: "/develop/",
    collapsed: false,
    items: [
      {
        text: "develop.getting_started.creating_project",
        link: "/develop/getting-started/creating-a-project",
      },
      {
        text: "develop.getting_started.project_structure",
        link: "/develop/getting-started/project-structure",
      },
      {
        text: "develop.getting_started.setting_up",
        link: "/develop/getting-started/setting-up",
      },
      {
        text: "develop.getting_started.opening_project",
        link: "/develop/getting-started/opening-a-project",
      },
      {
        text: "develop.getting_started.launching_game",
        link: "/develop/getting-started/launching-the-game",
      },
      {
        text: "develop.getting_started.generating_sources",
        link: "/develop/getting-started/generating-sources",
      },
      {
        text: "develop.getting_started.tips_and_tricks",
        link: "/develop/getting-started/tips-and-tricks",
      },
    ],
  },
  {
    text: "develop.items",
    collapsed: true,
    items: [
      {
        text: "develop.items.first_item",
        link: "/develop/items/first-item",
        items: [
          {
            text: "develop.items.food",
            link: "/develop/items/food",
          },
          {
            text: "develop.items.potions",
            link: "/develop/items/potions",
          },
          {
            text: "develop.items.spawn_egg",
            link: "/develop/items/spawn-egg",
          },
          {
            text: "develop.items.custom_tools",
            link: "/develop/items/custom-tools",
          },
          {
            text: "develop.items.custom_armor",
            link: "/develop/items/custom-armor",
          },
        ],
      },
      {
        text: "develop.items.item_models",
        link: "/develop/items/item-models",
      },
      {
        text: "develop.items.item_appearance",
        link: "/develop/items/item-appearance",
      },
      {
        text: "develop.items.custom_creative_tabs",
        link: "/develop/items/custom-item-groups",
      },
      {
        text: "develop.items.custom_item_interactions",
        link: "/develop/items/custom-item-interactions",
      },
      {
        text: "develop.items.custom_enchantment_effects",
        link: "/develop/items/custom-enchantment-effects",
      },
      {
        text: "develop.items.custom_data_components",
        link: "/develop/items/custom-data-components",
      },
    ],
  },
  {
    text: "develop.blocks",
    collapsed: true,
    items: [
      {
        text: "develop.blocks.first_block",
        link: "/develop/blocks/first-block",
      },
      {
        text: "develop.blocks.block_models",
        link: "/develop/blocks/block-models",
      },
      {
        text: "develop.blocks.blockstates",
        link: "/develop/blocks/blockstates",
      },
      {
        text: "develop.blocks.block_entities",
        link: "/develop/blocks/block-entities",
        items: [
          {
            text: "develop.blocks.block_entity_renderer",
            link: "/develop/blocks/block-entity-renderer",
          },
        ],
      },
      {
        text: "develop.blocks.transparency_and_tinting",
        link: "/develop/blocks/transparency-and-tinting",
      },
    ],
  },
  {
    text: "develop.entities",
    collapsed: true,
    items: [
      {
        text: "develop.entities.attributes",
        link: "/develop/entities/attributes",
      },
      {
        text: "develop.entities.effects",
        link: "/develop/entities/effects",
      },
      {
        text: "develop.entities.damage_types",
        link: "/develop/entities/damage-types",
      },
    ],
  },
  {
    text: "develop.sounds",
    collapsed: true,
    items: [
      {
        text: "develop.sounds.using_sounds",
        link: "/develop/sounds/using-sounds",
      },
      {
        text: "develop.sounds.custom",
        link: "/develop/sounds/custom",
      },
      {
        text: "develop.sounds.dynamic_sounds",
        link: "/develop/sounds/dynamic-sounds",
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
        text: "develop.rendering.basic_concepts",
        link: "/develop/rendering/basic-concepts",
      },
      {
        text: "develop.rendering.gui_graphics",
        link: "/develop/rendering/draw-context",
      },
      {
        text: "develop.rendering.hud",
        link: "/develop/rendering/hud",
      },
      {
        text: "develop.rendering.world",
        link: "/develop/rendering/world",
      },
      {
        text: "develop.rendering.gui",
        items: [
          {
            text: "develop.rendering.gui.custom_screens",
            link: "/develop/rendering/gui/custom-screens",
          },
          {
            text: "develop.rendering.gui.custom_widgets",
            link: "/develop/rendering/gui/custom-widgets",
          },
        ],
      },
      {
        text: "develop.rendering.particles",
        items: [
          {
            text: "develop.rendering.particles.creating_particles",
            link: "/develop/rendering/particles/creating-particles",
          },
        ],
      },
    ],
  },
  {
    text: "develop.data_generation",
    collapsed: true,
    items: [
      {
        text: "develop.data_generation.setup",
        link: "/develop/data-generation/setup",
      },
      {
        text: "develop.data_generation.client",
        items: [
          {
            text: "develop.data_generation.translations",
            link: "/develop/data-generation/translations",
          },
          {
            text: "develop.data_generation.block_models",
            link: "/develop/data-generation/block-models",
          },
          {
            text: "develop.data_generation.item_models",
            link: "/develop/data-generation/item-models",
          },
        ],
      },
      {
        text: "develop.data_generation.server",
        items: [
          {
            text: "develop.data_generation.tags",
            link: "/develop/data-generation/tags",
          },

          {
            text: "develop.data_generation.advancements",
            link: "/develop/data-generation/advancements",
          },
          {
            text: "develop.data_generation.recipes",
            link: "/develop/data-generation/recipes",
          },
          {
            text: "develop.data_generation.loot_tables",
            link: "/develop/data-generation/loot-tables",
          },
        ],
      },
    ],
  },
  {
    text: "develop.serialization",
    collapsed: true,
    items: [
      {
        text: "develop.misc.codecs",
        link: "/develop/codecs",
      },
      {
        text: "develop.misc.data_attachments",
        link: "/develop/data-attachments",
      },
      {
        text: "develop.misc.saved_data",
        link: "/develop/saved-data",
      },
    ],
  },
  {
    text: "develop.loom",
    collapsed: true,
    items: [
      {
        text: "develop.loom.introduction",
        link: "/develop/loom",
      },
      {
        text: "develop.loom.fabric_api",
        link: "/develop/loom/fabric-api",
      },
      {
        text: "develop.loom.options",
        link: "/develop/loom/options",
      },
      {
        text: "develop.loom.prod",
        link: "/develop/loom/production-run-tasks",
      },
      {
        text: "develop.loom.classpath_groups",
        link: "/develop/loom/classpath-groups",
      },
      {
        text: "develop.loom.tasks",
        link: "/develop/loom/tasks",
      },
    ],
  },
  {
    text: "develop.mixins",
    collapsed: true,
    items: [
      {
        text: "develop.mixins.bytecode",
        link: "/develop/mixins/bytecode",
      },
    ],
  },
  {
    text: "develop.misc",
    collapsed: true,
    items: [
      {
        text: "develop.misc.events",
        link: "/develop/events",
      },
      {
        text: "develop.misc.text_and_translations",
        link: "/develop/text-and-translations",
      },
      {
        text: "develop.misc.networking",
        link: "/develop/networking",
      },
      {
        text: "develop.misc.key_mappings",
        link: "/develop/key-mappings",
      },
      {
        text: "develop.misc.migrating_mappings",
        link: "/develop/migrating-mappings",
        items: [
          {
            text: "develop.misc.migrating_mappings.loom",
            link: "/develop/migrating-mappings/loom",
          },
          {
            text: "develop.misc.migrating_mappings.ravel",
            link: "/develop/migrating-mappings/ravel",
          },
        ],
      },
      {
        text: "develop.misc.debugging",
        link: "/develop/debugging",
      },
      {
        text: "develop.misc.automatic_testing",
        link: "/develop/automatic-testing",
      },
    ],
  },
] as Fabric.SidebarItem[];
