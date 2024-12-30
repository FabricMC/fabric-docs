---
title: Tools and Weapons
description: Learn how to create your own tools and configure their properties.
authors:
  - IMB11

search: false
---

# Tools {#tools}

Tools are essential for survival and progression, allowing players to gather resources, construct buildings, and defend themselves.

## Creating a Tool Material {#creating-a-tool-material}

::: info
If you're creating multiple tool materials, consider using an `Enum` to store them. Vanilla does this in the `ToolMaterials` class, which stores all the tool materials that are used in the game.

This class can also be used to determine your tool material's properties in relation to vanilla tool materials.
:::

You can create a tool material by creating a new class that inherits it - in this example, I'll be creating "Guidite" tools:

@[code transcludeWith=:::1](@/reference/1.21/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

Once you have created your tool material and tweaked it to your likings, you can create an instance of it to be used in the tool item constructors.

@[code transcludeWith=:::8](@/reference/1.21/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

The tool material tells the game the following information:

### Durability - `getDurability()` {#durability}

How many times the tool can be used before breaking:

@[code transcludeWith=:::2](@/reference/1.21/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

### Mining Speed - `getMiningSpeedMultiplier()` {#mining-speed}

If the tool is used to break blocks, how fast should it break the blocks?

@[code transcludeWith=:::3](@/reference/1.21/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

For reference purposes, the diamond tool material has a mining speed of `8.0F` whilst the stone tool material has a mining speed of `4.0F`.

### Attack Damage - `getAttackDamage()` {#attack-damage}

How many points of damage should the tool do when used as a weapon against another entity?

@[code transcludeWith=:::4](@/reference/1.21/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

### Inverse Tag - `getMiningLevel()` {#inverse-tag}

The inverse tag shows what the tool _**cannot**_ mine. For instance, using the `BlockTags.INCORRECT_FOR_WOODEN_TOOL` tag stops the tool from mining certain blocks:

```json
{
  "values": [
    "#minecraft:needs_diamond_tool",
    "#minecraft:needs_iron_tool",
    "#minecraft:needs_stone_tool"
  ]
}
```

This means the tool can't mine blocks that need a diamond, iron, or stone tool.

Let's use the iron tool tag. This stops Guidite tools from mining blocks that require a stronger tool than iron.

@[code transcludeWith=:::5](@/reference/1.21/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

You can use `TagKey.of(...)` to create a custom tag key if you want to use a custom tag.

### Enchantability - `getEnchantability()` {#enchantability}

How easy is it to get better and higher level enchantments with this item? For reference, Gold has an enchantability of 22 whilst Netherite has an enchantability of 15.

@[code transcludeWith=:::6](@/reference/1.21/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

### Repair Ingredient(s) - `getRepairIngredient()` {#repair-ingredient}

What item or items are used to repair the tool?

@[code transcludeWith=:::7](@/reference/1.21/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

## Creating Tool Items {#creating-tool-items}

Using the same utility function as in the [Creating Your First Item](./first-item) guide, you can create your tool items:

@[code transcludeWith=:::7](@/reference/1.21/src/main/java/com/example/docs/item/ModItems.java)

Remember to add them to an item group if you want to access them from the creative inventory!

@[code transcludeWith=:::8](@/reference/1.21/src/main/java/com/example/docs/item/ModItems.java)

You will also have to add a texture, item translation and item model. However, for the item model, you'll want to use the `item/handheld` model as your parent.

For this example, I will be using the following model and texture for the "Guidite Sword" item:

@[code](@/reference/1.21/src/main/resources/assets/fabric-docs-reference/models/item/guidite_sword.json)

<DownloadEntry visualURL="/assets/develop/items/tools_0.png" downloadURL="/assets/develop/items/tools_0_small.png">Texture</DownloadEntry>

That's pretty much it! If you go in-game you should see your tool item(s) in the tools tab of the creative inventory menu.

![Finished tools in inventory](/assets/develop/items/tools_1.png)
