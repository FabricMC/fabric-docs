---
title: 工具和武器
description: 学习如何创建自己的工具并配置其属性。
authors:
  - bluebear94
  - cassiancc
  - ChampionAsh5357
  - IMB11
  - its-miroma
  - NotNightSky
resources:
  https://docs.neoforged.net/docs/items/tools/: Tools - NeoForge 文档（Neo exclusives 除外）
---

工具对于生存和游戏进程至关重要，可让玩家收集资源、建造建筑物和保护自己。

## 创建工具材料 {#creating-a-tool-material}

你可以通过实例化一个`ToolMaterial`对象来创建一个工具材料，并将其储存在一个之后可以用该材料创建工具物品的字段内。

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_tool_material

`ToolMaterial`接口的构造函数接受以下参数（按顺序排列）：

| 参数                        | 描述                                                                                     |
| ------------------------- | -------------------------------------------------------------------------------------- |
| `incorrectBlocksForDrops` | 如果一个方块具有`incorrectBlocksForDrops`标签，这就意味着当你对这个方块使用由这个`ToolMaterial`创建的工具，这个方块不会有任何掉落物。 |
| `durability`              | 该`ToolMaterial`的耐久度。                                                                   |
| `speed`                   | 该`ToolMaterial`的挖掘速度。                                                                  |
| `attackDamageBonus`       | 该`ToolMaterial`的额外攻击伤害。                                                                |
| `enchantmentValue`        | 该`ToolMaterial`在附魔时得到更好附魔的概率。                                                          |
| `repairItems`             | 任何具有这个标签的物品可以被用来在铁砧中修复该`ToolMaterial`。                                                 |

在本例中，我们将使用与修复盔甲相同的修复物品标签。我们定义标签引用如下：

<<< @/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java#repair_tag

如果你不确定怎么给上述参数设置一个平衡的数值，你可以考虑参考原版材料的数值，比如`ToolMaterial.STONE`或者`ToolMaterial.DIAMOND`。

### 创建工具材料标签 {#creating-the-tool-material-tag}

针对我们的`incorrectBlocksForDrops`标签，我们可以创建一个类似于原版`minecraft:incorrect_for_*_drops`的标签，用于指定使用该材料挖掘时不会掉落物品的方块。我们定义标签引用如下：

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_incorrect_blocks_tag

接下来，我们使用标签 JSON 文件来定义该标签的具体内容。这里设定 Guidite 工具可以挖掘木制工具能挖掘的方块，外加铜矿石和深层铜矿石：

<<< @/reference/latest/src/main/resources/data/example-mod/tags/block/incorrect_for_guidite_tool.json

请注意，这个例子继承自较弱的工具材料，并 _移除_ 了我们更强的材料能挖掘的条目，从而继承了所有木制工具无法挖掘的其他方块。

::: tip

我们也可以反过来操作：继承更强的工具，并 _追加_ 那些 Guidite 工具挖不动的额外方块。

举例来说，如果我们想创建一个等效于铁制工具、但无法挖掘钻石矿石的工具，其`values`中就需要包含`#minecraft:incorrect_for_iron_tool`以及`#minecraft:diamond_ores`。

如果你希望你的工具材料能够挖掘与现有材料完全相同的方块，你可以在标签定义中直接包含相应的标签，无需进行任何添加或删除。建议采用此方法，而不是直接将现有的标签当作你材料的`incorrectBlocksForDrops`参数传入，这样能确保用户可以独立配置每种材料的无效方块。

:::

## 注册工具物品 {#registering-tool-items}

你可以使用我们在[创建你的第一个物品](./first-item)指南中所实现的相同类和函数来创建工具物品：

:::: tabs

== 剑

这些浮点参数分别对应攻击伤害和攻击速度。

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#guidite_sword

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_sword

== 矛

这些浮点参数分别对应攻击持续时间、伤害倍率、延迟、脱离时长、脱离阈值、击退时长、击退阈值、伤害时长以及伤害阈值。

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#guidite_spear

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_spear

== 镐

这些浮点参数分别对应攻击伤害和攻击速度。

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#guidite_pickaxe

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_pickaxe

== 斧

对于斧，你应该创建一个 `AxeItem`，而不是通用的 `Item`。这实现了针对斧的右键操作，并会自动调用 `Item.Properties` 的 `axe` 方法。

这些浮点参数分别对应攻击伤害和攻击速度。

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#guidite_axe

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_axe

== 锹

对于锹，你应该创建一个 `ShovelItem`，而不是通用的 `Item`。这实现了针对锹的右键操作，并会自动调用 `Item.Properties` 的 `shovel` 方法。

这些浮点参数分别对应攻击伤害和攻击速度。

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#guidite_shovel

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_shovel

== 锄

对于锄，你应该创建一个 `HoeItem`，而不是通用的 `Item`。这实现了针对锄的右键操作，并会自动调用 `Item.Properties` 的 `hoe` 方法。

这些浮点参数分别对应攻击伤害和攻击速度。

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#guidite_hoe

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#guidite_hoe

::::

如果你想通过创造模式物品栏访问，记得其加入一个创造标签页！

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#add_guidite_sword_to_create_tab

你还得添加纹理、物品翻译和物品模型。然而，对于物品模型，你应该使用 `item/handheld` 模型作为父模型，而非通常的 `item/generaterd`。

## 资产 {#models}

:::: tabs

::::

你还需要添加[纹理](./first-item#adding-a-texture)、[翻译](./first-item#naming-the-item)、[客户端物品](./first-item#creating-the-client-item)和[物品模型](./item-models)。然而，对于物品模型，你应该使用 `item/handheld` 模型作为父模型，而非通常的 `item/generaterd`。

::: info

以下模型是利用数据生成创建的。更多信息，请参阅有关[生成物品模型](../data-generation/item-models)的文档。

:::

::: tabs

== 剑

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#guidite_sword

== 矛

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#guidite_spear

== 镐

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#guidite_pickaxe

== 斧

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#guidite_axe

== 锹

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#guidite_shovel

== 锄

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#guidite_hoe

:::

这里是本指南中使用的所有示例纹理：

<DownloadEntry downloadURL="/assets/develop/items/tools.zip">Guidite 工具纹理</DownloadEntry>

## 标签工具物品 {#tags}

:::info 前置知识

更多信息，请参阅有关生成[物品标签](../data-generation/tags)的文档。

:::

建议将工具放置在相应的物品标签中。工具拥有各自独特的标签，例如 `ItemTags.SWORDS`，这些标签用于附魔能力和其他特定逻辑，例如是否施加横扫伤害。

在你的物品标签提供程序中，将以下代码添加到 `addTags` 函数中：

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#tool_tags

这样就差不多了！进入游戏后，你应该能在创造模式物品栏菜单的“工具与实用物品”标签页中看到你的工具。

![物品栏中的成品工具](/assets/develop/items/tools_1.png)
