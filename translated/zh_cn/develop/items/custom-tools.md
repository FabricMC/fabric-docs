---
title: 工具和武器
description: 学习如何创建自己的工具并配置其属性。
authors:
  - IMB11
---

# 工具{#tools}

工具对于生存和游戏进程至关重要，可让玩家收集资源、建造建筑物和保护自己。

## 创建工具材料{#creating-a-tool-material}

::: info
If you're creating multiple tool materials, consider using an `Enum` to store them. Vanilla does this in the `ToolMaterials` class, which stores all the tool materials that are used in the game.

此类还可用于确定你的工具原型相较于原版工具原型不同的的属性。
:::

为了创建一个工具材料，你可以创建一个继承它的、新的类——在此示例中，将创建 Guidite 质的工具：

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

工具原型向游戏告知以下信息：

- ### 耐久 - `getDurability()` {#durability}

  该工具在损坏前可被使用多少次。

  **示例**

  @[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

- ### 挖掘速度 - `getMiningSpeedMultiplier()` {#mining-speed}

  如果该工具用来破坏方块，那么它破坏方块的速度应该多快？

  作为参考，钻石工具材料的挖掘速度为 `8.0F`，而石质工具材料的挖掘速度为 `4.0F`。

  **示例**

  @[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

- ### 攻击伤害 - `getAttackDamage()` {#attack-damage}

  当该工具被攻击别的实体的武器时应该造成多少点伤害？

  **示例**

  @[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

- ### 反向标签 - `getMiningLevel()` {#inverse-tag}

  反向标签显示工具 _**无法**_ 挖掘的内容。 例如，使用 `BlockTags.INCORRECT_FOR_WOODEN_TOOL` 标签可阻止工具挖掘某些方块：

  ```json
  {
    "values": [
      "#minecraft:needs_diamond_tool",
      "#minecraft:needs_iron_tool",
      "#minecraft:needs_stone_tool"
    ]
  }
  ```

  这意味着该工具无法开采需要钻石、铁或石质工具才能开采的方块。

  **示例**

  我们将对 Guidite 工具使用铁制工具标签。 铁制工具标签会阻止 Guidite 工具开采需要用比铁制工具更强的的工具来开采的方块。

  @[code transcludeWith=:::5](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

  如果你想自定义标签，可以使用 `TagKey.of(...)` 来创建自定义标签键。

- ### 附魔能力 - `getEnchantability()` {#enchantability}

  这个物品在附魔台中附上更好、更高级的附魔有多轻松？ 作为参考和比较，金质的附魔等级为 22，而下界合金的附魔等级为 15。

  **示例**

  @[code transcludeWith=:::6](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

- ### 修复原料 - `getRepairIngredient()` {#repair-ingredient}

  使用什么物品来修理该工具？

  **示例**

  @[code transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

创建了工具原型并根据自己的喜好对其进行了调整后，你就可以创建它的一个实例以用于工具物品构造函数。

@[code transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/item/tool/GuiditeMaterial.java)

## 创建工具物品{#creating-tool-items}

使用与 [创建你的第一个物品](./first-item) 指南中相同的实用功能，你可以创建工具物品：

@[code transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

如果你想从创造物品栏中访问它们，请记得将它们添加到物品组中！

@[code transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

你还得添加纹理、物品翻译和物品模型。 然而，对于物品模型，你需要使用 `item/handheld` 模型作为你的父级。

在此示例中，我将对“Guidite Sword”物品使用以下模型和纹理：

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/item/guidite_sword.json)

<DownloadEntry type="Texture" visualURL="/assets/develop/items/tools_0.png" downloadURL="/assets/develop/items/tools_0_small.png" />

---

这样就差不多了！ 如果你进入游戏，你应该会在创造物品栏菜单的工具选项栏中看到你的工具物品。

![物品栏中的成品工具](/assets/develop/items/tools_1.png)
