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

For this example, we will use the same repair item tag we will be using for armor. We define the tag reference as follows:

@[code transcludeWith=:::repair_tag](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

If you're struggling to determine balanced values for any of the numerical parameters, you should consider looking at the vanilla tool material constants, such as `ToolMaterial.STONE` or `ToolMaterial.DIAMOND`.

## Creating Tool Items {#creating-tool-items}

Using the same utility function as in the [Creating Your First Item](./first-item) guide, you can create your tool items:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#sword

The two float values (`1f, 1f`) refer to the attack damage of the tool and the attack speed of the tool respectively.

Note that items with right-click behaviour, like axes, should use classes like `AxeItem` rather than `Item` so that they can be used for stripping. The constructors for `AxeItem` and `HoeItem` call `Item.Properties.axe` and `Item.Properties.hoe` in the constructor, so there is no need to set this twice.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#axe

### Adding to Creative Tabs {#creative-tabs}

Remember to add them to a creative tab if you want to access them from the creative inventory!

@[code transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

### Assets {#models}

You will also have to add a texture, item translation, client item, and item model. However, for the item model, you'll want to use the `item/handheld` model as your parent instead of the usual `item/generated`.

For this example, we will be using the following model and texture for the "Guidite Sword" item:

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_sword.json)

<DownloadEntry visualURL="/assets/develop/items/tools_0.png" downloadURL="/assets/develop/items/tools_0_small.png">Texture</DownloadEntry>

### Tagging Your Tool {#tags}

::: info PREREQUISITES

It's recommended to know how to [data generate tags](../data-generation/tags) before following this section.

:::

It's also recommended to place your tool in the appropriate item tags. Tools have their own individual tags, like `ItemTags.SWORDS`, that are used for enchantability and other logic like whether to apply sweeping damage.

In your item tag provider, add the following lines to `addTags`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#sword-tags

That's pretty much it! If you go in-game you should see your tools in the 'Tools and Utilities' tab of the creative inventory menu.

![Finished tools in inventory](/assets/develop/items/tools_1.png)
