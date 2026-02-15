---
title: 工具和武器
description: 学习如何创建自己的工具并配置其属性。
authors:
  - IMB11
---

工具对于生存和游戏进程至关重要，可让玩家收集资源、建造建筑物和保护自己。

## 创建工具材料 {#creating-a-tool-material}

你可以通过实例化一个`ToolMaterial`对象来创建一个工具材料，并将其储存在一个之后可以用该材料创建工具物品的字段内。

@[code transcludeWith=:::guidite_tool_material](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

`ToolMaterial`接口的构造函数接受以下参数（按顺序排列）：

| 参数                        | 描述                                                                                   |
| ------------------------- | ------------------------------------------------------------------------------------ |
| `incorrectBlocksForDrops` | 如果一个方块具有incorrectBlocksForDrops标签，这就意味着当你对这个方块使用由这个`ToolMaterial`创建的工具，这个方块不会有任何掉落物。 |
| `durability`              | 该`ToolMaterial`的耐久度。                                                                 |
| `speed`                   | 该`ToolMaterial`的挖掘速度。                                                                |
| `attackDamageBonus`       | 该`ToolMaterial`的额外攻击伤害。                                                              |
| `enchantmentValue`        | 该`ToolMaterial`在附魔时得到更好附魔的概率。                                                        |
| `repairItems`             | 任何具有这个标签的物品可以被用来在铁砧中修复该`ToolMaterial`。                                               |

在本例中，我们将使用与修复盔甲相同的修复物品。 我们定义标签引用如下：

@[code transcludeWith=:::repair_tag](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

如果你不确定怎么给上述参数设置一个平衡的数值，你可以考虑参考原版材料的数值，比如`ToolMaterial.STONE`或者`ToolMaterial.DIAMOND`。

## 创建工具物品 {#creating-tool-items}

使用与 [创建你的第一个物品](./first-item) 指南中相同的实用功能，你可以创建工具物品：

@[code transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

两个浮点数值 (`1f, 1f`) 分别表示工具的攻击伤害以及攻击速度。

如果你想通过创造模式物品栏访问，记得其加入一个创造标签页！

@[code transcludeWith=:::8](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

你还得添加纹理、物品翻译和物品模型。 然而，对于物品模型，你应该使用 `item/handheld` 模型作为父模型，而非通常的 `item/generaterd`。

在此示例中，我将对“Guidite Sword”物品使用以下模型和纹理：

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_sword.json)

<DownloadEntry visualURL="/assets/develop/items/tools_0.png" downloadURL="/assets/develop/items/tools_0_small.png">纹理</DownloadEntry>

这样就差不多了！ 如果你进入游戏，你应该会在创造物品栏菜单的工具标签页中看到你的工具物品。

![物品栏中的成品工具](/assets/develop/items/tools_1.png)
