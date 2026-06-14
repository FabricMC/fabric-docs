---
title: Tools and Weapons
description: Learn how to create your own tools and configure their properties.
authors:
  - IMB11
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

For this example, we will use the same repair item we will be using for armor. We define the tag reference as follows:

<<< @/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java#repair_tag

If you're struggling to determine balanced values for any of the numerical parameters, you should consider looking at the vanilla tool material constants, such as `ToolMaterial.STONE` or `ToolMaterial.DIAMOND`.

### Creating the Tool Material Tag {#creating-the-tool-material-tag}

For our `incorrectBlocksForDrops` tag, we can use one of the vanilla `minecraft:incorrect_for_*_drops` tag, but defining our own tag allows data packs to change what blocks our tool material can mine independently from other tool materials. We define the tag reference as follows:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_incorrect_blocks_tag

Next, we define the tag's contents using a tag JSON. Let's make Guidite tools be able to mine the same blocks as wooden tools, plus copper ore and deepslate copper ore:

<<< @/reference/latest/src/main/resources/data/example-mod/tags/block/incorrect_for_guidite_tool.json

Note that we inherit from a weaker tool material and _remove_ entries from its incorrect blocks tag because we're defining our material in terms of adding mineable blocks to a different material. If we instead wanted to define our material in terms of removing mineable blocks from another (e.g. like iron, but can't mine diamond ore), then we could add the incorrect blocks tag for the stronger material and _add_ entries to it.

## Creating Tool Items {#creating-tool-items}

Using the same utility function as in the [Creating Your First Item](./first-item) guide, you can create your tool items:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_sword

The two float values (`1f, 1f`) refer to the attack damage of the tool and the attack speed of the tool respectively.

::: warning

For shovels, axes, and hoes, you should create a `ShovelItem`, `AxeItem`, or `HoeItem` instead of a generic `Item`, as these implement tool-specific right-click actions. In this case, you don't need to manually call the respective method in `Item.Properties`, as the respective item class's constructors do that for you.

:::

Remember to add them to a creative tab if you want to access them from the creative inventory!

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#add_guidite_sword_to_create_tab

You will also have to add a texture, item translation and item model. However, for the item model, you'll want to use the `item/handheld` model as your parent instead of the usual `item/generated`.

For this example, I will be using the following model and texture for the "Guidite Sword" item:

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_sword.json

<DownloadEntry visualURL="/assets/develop/items/tools_0.png" downloadURL="/assets/develop/items/tools_0_small.png">Texture</DownloadEntry>

That's pretty much it! If you go in-game you should see your tool item(s) in the tools tab of the creative inventory menu.

![Finished tools in inventory](/assets/develop/items/tools_1.png)
