---
title: Tools and Weapons
description: Learn how to create your own tools and configure their properties.
authors:
  - bluebear94
  - cassiancc
  - ChampionAsh5357
  - IMB11
  - its-miroma
  - NotNightSky
resources:
  https://docs.neoforged.net/docs/items/tools/: Tools - NeoForge Docs (except Neo exclusives)
---

Tools are essential for survival and progression, allowing players to gather resources, construct buildings, and defend themselves.

## Creating a Tool Material {#creating-a-tool-material}

You can create a tool material by instantiating a new `ToolMaterial` object and storing it in a field that can be used later to create the tool items that use the material.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_tool_material

The `ToolMaterial` constructor accepts the following parameters, in this specific order:

| Parameter                 | Description                                                                                                                                                           |
| ------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `incorrectBlocksForDrops` | If a block is in the `incorrectBlocksForDrops` tag, it means that when you use a tool made from this `ToolMaterial` on that block, the block will not drop any items. |
| `durability`              | The durability of all tools that are of this `ToolMaterial`.                                                                                                          |
| `speed`                   | The mining speed of the tools that are of this `ToolMaterial`.                                                                                                        |
| `attackDamageBonus`       | The additional attack damage of the tools that are of this `ToolMaterial` will have.                                                                                  |
| `enchantmentValue`        | The "Enchantability" of tools which are of this `ToolMaterial`.                                                                                                       |
| `repairItems`             | Any items within this tag can be used to repair tools of this `ToolMaterial` in an anvil.                                                                             |

For this example, we will use the same repair item tag we will be using for armor. We define the tag reference as follows:

<<< @/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java#repair_tag

If you're struggling to determine balanced values for any of the numerical parameters, you should consider looking at the vanilla tool material constants, such as `ToolMaterial.STONE` or `ToolMaterial.DIAMOND`.

### Creating the Tool Material Tag {#creating-the-tool-material-tag}

For our `incorrectBlocksForDrops` tag, we can create a tag similar to vanilla's `minecraft:incorrect_for_*_drops` tags, which determines the blocks that will **not** drop when mined with the material. Let's define the tag reference as follows:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_incorrect_blocks_tag

Next, we define the tag's contents using a tag JSON. Let's make Guidite tools be able to mine the same blocks as wooden tools, plus Copper Ore and Deepslate Copper Ore:

<<< @/reference/latest/src/main/resources/data/example-mod/tags/block/incorrect_for_guidite_tool.json

Note that this example inherits from a weaker tool material and _removes_ entries that our stronger material can mine, inheriting all the other blocks that wood cannot.

::: tip

We could also do the reverse: inherit from a stronger tool and _append_ additional blocks that Guidite tools are unfit for.

As an example, if we wanted to create a tool that worked like iron but couldn't mine Diamond Ore, `values` would need to contain `#minecraft:incorrect_for_iron_tool` and `#minecraft:diamond_ores`.

If you want to make your tool material mine the same blocks as an existing one, you can include the respective tag in your tag's definition without any additions or removals. This is recommended over passing the existing tag as your material's `incorrectBlocksForDrops` so that users can configure the incorrect blocks for each of the materials independently.

:::

## Registering Tool Items {#registering-tool-items}

You can create your tool items by using the same classes and functions that we implemented in the [Creating Your First Item](./first-item) guide:

:::: tabs

== Sword

The two float values (`1f, 1f`) refer to the attack damage of the tool and the attack speed of the tool respectively.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#guidite_sword

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_sword

== Axe

The two float values (`5.0f, -3.0f`) refer to the attack damage of the tool and the attack speed of the tool respectively.

For axes, you should create a `AxeItem` instead of a generic `Item`, as this implements tool-specific right-click action:

::: info

`AxeItem` calls the `axe` method of `Item.Properties` in its constructor.

:::

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#guidite_axe

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_axe

== Spear

The nine float values (`2.8f, 0.3f, 1.4f, 1.1f, 2.0f, 1.1f, 0.15f, 0.25f, 0.45f`) refer to the float attack duration, damage multiplier, delay, dismount time, dismount threshold, knockback time, knockback threshold, damage time and damage threshold respectively.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#guidite_spear

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_spear

== Bow

For bows, you should create a `BowItem` instead of a generic `Item`, as this implements tool-specific right-click action:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#guidite_bow

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_bow

== Shovel

The two float values (`1.0f, -1.0f`) refer to the attack damage of the tool and the attack speed of the tool respectively.

For shovels, you should create a `ShovelItem` instead of a generic `Item`, as this implements tool-specific right-click action:

::: info

`ShovelItem` calls the `shovel` method of `Item.Properties` in its constructor.

:::

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#guidite_shovel

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_shovel

== Hoe

The two float values (`2.0f, -1.0f`) refer to the attack damage of the tool and the attack speed of the tool respectively.

For hoes, you should create a `HoeItem` instead of a generic `Item`, as this implements tool-specific right-click action:

::: info

`HoeItem` calls the `hoe` method of `Item.Properties` in its constructor.

:::

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#guidite_hoe

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_hoe

::::

Remember to add them to a creative tab if you want to access them from the creative inventory!

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#add_guidite_sword_to_create_tab

You will also have to add a texture, item translation and item model. However, for the item model, you'll want to use the `item/handheld` model as your parent instead of the usual `item/generated`.

## Assets {#models}

:::: tabs

::::

You will also have to add a [texture](./first-item#adding-a-texture), [translation](./first-item#naming-the-item), [client item](./first-item#creating-the-client-item), and [item model](./item-models). However, for the item model, you'll want to use the `item/handheld` model as your parent instead of the usual `item/generated`.

::: info

The models below are using data generation. For more information, see the documentation on generating [item models](../data-generation/item-models).

:::

::: tabs

== Sword

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#guidite_sword

== Axe

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#guidite_axe

== Spear

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#guidite_spear

== Bow

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#guidite_bow

== Shovel

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#guidite_shovel

== Hoe

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#guidite_hoe

:::

Here are all the example textures used in this guide:

<DownloadEntry downloadURL="/assets/develop/items/tools.zip">Guidite Tool Textures</DownloadEntry>

## Tagging Tool Items {#tags}

::: info PREREQUISITES

For more information, see the documentation on generating [item tags](../data-generation/tags).

:::

It's also recommended to place your tool in the appropriate item tags. Tools have their own individual tags, such as `ItemTags.SWORDS`, which are used for enchantability and other specific logic, like whether to apply sweeping damage.

In your item tag provider, add the following lines to `addTags`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#tool_tags

That's pretty much it! If you go in-game you should see your tools in the "Tools and Utilities" tab of the creative inventory menu.

![Finished tools in inventory](/assets/develop/items/tools_1.png)
