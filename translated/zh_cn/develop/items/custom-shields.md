---
title: 自定义盾牌
description: 了解如何创建你自己的盾牌并配置其属性。
authors:
  - cassiancc
  - ChampionAsh5357
resources:
  https://mcsrc.dev/1/26.2/net/minecraft/client/renderer/special/ShieldSpecialRenderer: 原版盾牌渲染器
  https://minecraft.wiki/w/Data_component_format/blocks_attacks: 格挡攻击数据组件
---

<!--  -->

:::info 前置知识

你必须首先了解如何[创建工具](./custom-tools)。本指南还参考了[配方](../data-generation/recipes)、[物品模型](../data-generation/item-models)和[物品标签](../data-generation/tags)的数据生成。

:::

盾牌可以用来防御来自敌人的攻击。要向游戏中添加新的盾牌，你需要一个 `Item`、两个物品模型、一个客户端物品、配方、物品标签以及一个专用渲染器。

## 创建物品 {#item}

:::info 前置知识

有关更多信息，请参阅关于[创建物品](./first-item)的文档。

:::

在此示例中，我们将使用在[自定义盔甲](./custom-armor)和[自定义工具](./custom-tools)页面中使用的相同修复物品标签。我们定义标签引用如下：

<<< @/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java#repair_tag

然后，我们创建一个物品 ID 并注册包含以下组件的物品。

- [**旗帜图案**](https://minecraft.wiki/w/Data_component_format/banner_patterns)：创建一个带有空旗帜图案集合的物品。
- [**可修复**](https://minecraft.wiki/w/Data_component_format/repairable)：创建一个可以使用给定物品标签进行修复的物品。
- [**可装备/不可交换**](https://minecraft.wiki/w/Data_component_format/equippable)：在 GUI 中，按住 Shift 键点击物品会将其装备到副手。在世界中，右键点击它不会装备该物品。
- [**格挡攻击**](https://minecraft.wiki/w/Data_component_format/blocks_attacks)：创建一个可以格挡攻击的物品。本例使用了原版盾牌的数值。
  - 这是一个_延迟组件_，意味着它在世界加载后才会加载，从而允许其引用数据包对象（如标签）。
- [**损坏声音**](https://minecraft.wiki/w/Data_component_format/break_sound)：当物品损坏时，它将播放指定的音效。

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#shield

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#shield

如果你想从创造模式物品栏中访问它，请记得将它添加到创造模式标签页中！

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#add_guidite_shield_to_create_tab

## 创建特殊渲染器 {#special-renderer}

我们将使用专用渲染器来渲染盾牌，而不是普通的物品模型。

首先，我们将创建一个模型层位置，指向盾牌模型所在的位置：

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldLayers.java#layer

然后，在你的客户端初始化器中注册该图层：

<<< @/reference/latest/src/client/java/com/example/docs/ExampleModClient.java#shield_layer

接着，我们将为该物品创建一个专用渲染器。这个渲染器基于原版的 [`ShieldSpecialRenderer`](https://mcsrc.dev/1/26.2/net/minecraft/client/renderer/special/ShieldSpecialRenderer)，并进行了修改，使其能够从客户端物品中接收自定义精灵图。我们将在下一节中为渲染器提供这些精灵图。

该渲染器较为复杂，因此我们将对其进行拆解分析。

### 构造函数 {#constructor}

渲染器的构造函数接受四个参数：

- 一个可以通过 `Identifier` 提供精灵图的 `SpriteGetter` 接口。
- 我们将使用的模型，在本例中为 `ShieldModel`。
- 作为 `SpriteId` 提供的基础白色纹理（在客户端物品中提供）。
- 在没有染色或旗帜图案时使用的纹理，作为 `SpriteId` 提供。

构造函数将所有四个参数存储为字段，以便我们后续使用。

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#renderer

### 提取 {#extraction}

提取待渲染数据时，我们需要一份不可变的数据副本，其中仅包含渲染物品所需的信息。我们可以在 `extractArgument` 中通过将其 `DataComponentMap` 转换为不可变映射，从而从 `ItemStack` 获取该数据：

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#extract_argument

### 范围 {#extents}

我们还将设置模型的边界范围，定义模型的包围盒，用于模型中的渲染和动画：

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#extents

### 提交 {#submission}

提交过程处理“渲染_什么_”的逻辑。盾牌渲染器的逻辑执行以下操作：

1. 获取盾牌的旗帜图案并存储在 `patterns` 中。如果盾牌没有旗帜图案，则将其设置为 `BannerPatternLayers.EMPTY`。
2. 获取盾牌的染料颜色并存储在 `baseColor` 中。如果盾牌没有染料颜色，则该变量设置为 `null`。
3. 如果盾牌带有旗帜图案或经过染色，则使用 `base` 纹理。如果否，则使用 `base_nopattern` 纹理。
4. 使用提供的参数和纹理提交要渲染的盾牌模型。
5. 如果盾牌带有旗帜图案，也一并提交。
6. 如果盾牌附有魔咒，则提交附魔光泽（glint）。

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#submit

### 未烘焙模型 {#unbaked-models}

你还需要一个[未烘焙模型](https://docs.neoforged.net/docs/resources/client/models/modelsystem)，用于引用模型渲染器并向模型提供精灵图。

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#unbaked

## 创建模型 {#model}

:::info 前置知识

更多信息，请参阅有关生成[物品模型](../data-generation/item-models)的文档。

:::

我们将创建两个物品模型（一个用于普通状态，另一个用于盾牌格挡状态），以及一个带有自定义纹理的盾牌条件客户端物品：

:::: tabs

== 源代码

::: info

这些模型是通过数据生成创建的。更多信息，请参阅有关生成[物品模型](../data-generation/item-models)的文档。

:::

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#shield

== 客户端物品

`generated/assets/example-mod/items/guidite_shield.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/items/guidite_shield.json

== 物品模型

`generated/assets/example-mod/models/item/guidite_shield.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_shield.json

`generated/assets/example-mod/models/item/guidite_shield_blocking.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_shield_blocking.json

== 纹理

<DownloadEntry visualURL="/assets/develop/items/guidite_shield_base_hd.png" downloadURL="/assets/develop/items/guidite_shield_base.png">Guidite 盾牌基础纹理</DownloadEntry>

<DownloadEntry visualURL="/assets/develop/items/guidite_shield_base_nopattern_hd.png" downloadURL="/assets/develop/items/guidite_shield_base_nopattern.png">Guidite 盾牌基础（无旗帜图案）纹理</DownloadEntry>

::::

## 创建饰纹盾牌配方 {#recipe}

:::info 前置知识

有关更多信息，请参阅生成[配方](../data-generation/recipes)的文档。

:::

在生存模式中有两种获得我们盾牌的方法：合成普通盾牌，或者使用旗帜图案来装饰盾牌。

基础盾牌的合成配方可以由你任意指定。另一方面，装饰盾牌配方可以在配方提供程序中像这样创建：

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#shield_decoration

定义此配方后，你现在就可以在盾牌上套用旗帜图案了：

![在合成方格中应用旗帜图案](/assets/develop/items/shield_banner_example.png)

## 为盾牌物品添加标签 {#tags}

:::info 前置知识

更多信息，请参阅有关生成[物品标签](../data-generation/tags)的文档。

:::

你还应该将盾牌放入适当的物品标签中：

- `ItemTags.DURABILITY_ENCHANTABLE`，允许其附魔经验修补和耐久；
- `ConventionalItemTags.SHIELD_TOOLS`，Mod 开发者可用其实现盾牌特有的行为（如自定义盾牌附魔）。

在你的物品标签提供程序中，将以下代码添加到 `addTags` 函数中：

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#shield_tags

这样就差不多了！进入游戏后，你应该可以在创造模式物品栏菜单的“战斗”标签页中看到你的盾牌。

![游戏中的成品盾牌](/assets/develop/items/shield_use.png)
