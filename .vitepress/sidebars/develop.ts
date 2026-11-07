import type { SidebarItem } from "../types.d.ts";

export const DEVELOP_SIDEBAR: SidebarItem = {
  base: "/develop",
  link: "/",
  items: [
    //
    {
      collapsed: false,
      base: "/develop/getting-started",
      items: [
        //
        "/creating-a-project",
        "/project-structure",
        "/setting-up",
        "/opening-a-project",
        "/launching-the-game",
        "/generating-sources",
        "/building-a-mod",
        "/tips-and-tricks",
      ],
    },
    {
      base: "/develop/items",
      items: [
        //
        {
          link: "/first-item",
          items: [
            //
            "/food",
            "/custom-fuel",
            "/custom-compostable",
            "/potions",
            "/spawn-egg",
            "/custom-tools",
            "/custom-armor",
            "/custom-shields",
          ],
        },
        "/item-models",
        "/item-appearance",
        "/custom-creative-tabs",
        "/custom-item-interactions",
        "/custom-enchantment-effects",
        "/custom-data-components",
      ],
    },
    {
      base: "/develop/blocks",
      items: [
        //
        "/first-block",
        "/block-models",
        "/blockstates",
        {
          link: "/block-entities",
          items: [
            //
            "/block-entity-renderer",
            "/block-containers",
            "/container-menus",
          ],
        },
        "/block-tinting",
        "/workstations",
      ],
    },
    {
      base: "/develop/fluids",
      items: [
        //
        "/first-fluid",
      ],
    },
    {
      base: "/develop/entities",
      items: [
        //
        {
          link: "/first-entity",
          items: [
            //
            "/projectiles",
          ],
        },
        "/attributes",
        "/effects",
        "/damage-types",
      ],
    },
    {
      base: "/develop/events",
      items: [
        //
        "/",
        "/loot-tables",
      ],
    },
    {
      base: "/develop/sounds",
      items: [
        //
        "/using-sounds",
        "/custom",
        "/dynamic-sounds",
      ],
    },
    {
      base: "/develop/commands",
      items: [
        //
        "/basics",
        "/arguments",
        "/suggestions",
      ],
    },
    {
      base: "/develop/recipes",
      items: [
        //
        "/custom-recipe-types",
        "/extending-vanilla-recipes",
      ],
    },
    {
      base: "/develop/rendering",
      items: [
        //
        "/basic-concepts",
        "/gui-graphics",
        "/hud",
        "/world",
        {
          base: "/develop/rendering/gui",
          items: [
            //
            "/custom-screens",
            "/custom-widgets",
          ],
        },
        {
          base: "/develop/rendering/particles",
          items: [
            //
            "/creating-particles",
          ],
        },
      ],
    },
    {
      base: "/develop/data-generation",
      items: [
        //
        "/setup",
        {
          text: "/develop/data-generation/client",
          items: [
            //
            "/translations",
            {
              text: "/develop/data-generation/models",
              items: [
                //
                "/block-models",
                "/item-models",
              ],
            },
          ],
        },
        {
          text: "/develop/data-generation/server",
          items: [
            //
            "/advancements",
            "/enchantments",
            "/loot-tables",
            "/recipes",
            "/tags",
            {
              text: "/develop/data-generation/world-generation",
              items: [
                //
                "/features",
              ],
            },
          ],
        },
      ],
    },
    {
      base: "/develop/registries",
      items: [
        //
        "/dynamic-registry",
      ],
    },
    {
      base: "/develop/serialization",
      items: [
        //
        "/codecs",
        "/data-attachments",
        "/saved-data",
      ],
    },
    {
      base: "/develop/loom",
      items: [
        //
        "/",
        "/fabric-api",
        "/options",
        "/production-run-tasks",
        "/classpath-groups",
        "/tasks",
      ],
    },
    {
      base: "/develop/loader",
      items: [
        //
        "/",
        "/fabric-mod-json",
      ],
    },
    {
      base: "/develop/porting",
      items: [
        //
        {
          link: "/",
          items: [
            //
            {
              base: "/26.1/develop/porting",
              link: "/fabric-api",
            },
          ],
        },
        {
          link: "/mappings/",
          items: [
            //
            {
              base: "/1.21.11/develop/porting/mappings",
              link: "/loom",
            },
            {
              base: "/1.21.11/develop/porting/mappings",
              link: "/ravel",
            },
          ],
        },
      ],
    },
    {
      base: "/develop/mixins",
      items: [
        //
        "/bytecode",
        "/accessors",
      ],
    },
    {
      base: "/develop/class-tweakers",
      items: [
        //
        "/",
        "/access-widening",
        "/interface-injection",
        "/enum-extension",
      ],
    },
    {
      text: "/develop/misc",
      items: [
        //
        "/automatic-testing",
        "/debugging",
        "/game-rules",
        "/key-mappings",
        "/networking",
        "/resource-conditions",
        "/statistics",
        "/text-and-translations",
      ],
    },
  ].map((i) => ({ collapsed: true, ...i }) as SidebarItem),
};
