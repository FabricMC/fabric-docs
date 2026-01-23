---
title: Tools and Weapons
description: Learn how to create your own tools and configure their properties.
authors:
  - IMB11
---

Tools are essential for survival and progression, allowing players to gather resources, construct buildings, and defend themselves.

## Creating a Tool Material {#creating-a-tool-material}

You can create a tool material by instantiating a new `ToolMaterial` object and storing it in a field that can be used later to create the tool items that use the material.

@[code transcludeWith=:::guidite_tool_material](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

The `ToolMaterial` constructor accepts the following parameters, in this specific order:

| Parameter                 | Description                                                                                                                                                         |
| ------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `incorrectBlocksForDrops` | If a block is in the incorrectBlocksForDrops tag, it means that when you use a tool made from this `ToolMaterial` on that block, the block will not drop any items. |
| `durability`              | The durability of all tools that are of this `ToolMaterial`.                                                                                                        |
| `speed`                   | The mining speed of the tools that are of this `ToolMaterial`.                                                                                                      |
| `attackDamageBonus`       | The additional attack damage of the tools that are of this `ToolMaterial` will have.                                                                                |
| `enchantmentValue`        | The "Enchantability" of tools which are of this `ToolMaterial`.                                                                                                     |
| `repairItems`             | Any items within this tag can be used to repair tools of this `ToolMaterial` in an anvil.                                                                           |

For this example, we will use the same repair item we will be using for armor. We define the tag reference as follows:

@[code transcludeWith=:::repair_tag](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

If you're struggling to determine balanced values for any of the numerical parameters, you should consider looking at the vanilla tool material constants, such as `ToolMaterial.STONE` or `ToolMaterial.DIAMOND`.

## Creating Tool Items {#creating-tool-items}

Using the same utility function as in the [Creating Your First Item](./first-item) guide, you can create your tool items:

@[code transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

The two float values (`1f, 1f`) refer to the attack damage of the tool and the attack speed of the tool respectively.

Remember to add them to a creative tab if you want to access them from the creative inventory!

@[code transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

You will also have to add a texture, item translation and item model. However, for the item model, you'll want to use the `item/handheld` model as your parent instead of the usual `item/generated`.

For this example, I will be using the following model and texture for the "Guidite Sword" item:

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_sword.json)

<DownloadEntry visualURL="/assets/develop/items/tools_0.png" downloadURL="/assets/develop/items/tools_0_small.png">Texture</DownloadEntry>

That's pretty much it! If you go in-game you should see your tool item(s) in the tools tab of the creative inventory menu.

![Finished tools in inventory](/assets/develop/items/tools_1.png)
