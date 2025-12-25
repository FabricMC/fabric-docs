---
title: 物品模型生成
description: 通过数据生成器生成物品模型的指南。
authors:
  - PEQB1145
---

::: info 前提条件
请确保你已完成 [datagen 设置](./setup) 和 [创建你的第一个物品](../items/first-item)。
:::

对于每个我们需要生成的物品模型，我们必须创建两个独立的 JSON 文件：

1. 一个**物品模型**，用于定义物品的纹理、旋转和整体外观。它位于 `generated/assets/example-mod/models/item` 目录。
2. 一个**客户端物品**，用于定义基于各种条件（例如组件、交互等）应使用哪个模型。它位于 `generated/assets/example-mod/items` 目录。

## 设置 {#setup}

首先，我们需要创建我们的模型提供器（model provider）。

创建一个类，它继承 `FabricModelProvider`，并实现两个抽象方法：`generateBlockStateModels` 和 `generateItemModels`。
然后，创建一个与 `super` 匹配的构造函数。

@[code transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

在你的 `DataGeneratorEntrypoint` 的 `onInitializeDataGenerator` 方法中注册该类。

## 内置物品模型 {#built-in}

对于物品模型，我们将使用 `generateItemModels` 方法。其参数 `ItemModelGenerators itemModelGenerator` 负责生成物品模型，并包含了执行此操作的方法。

以下是几个最常用物品模型生成器方法的参考。

### 简单模型 {#simple}

简单物品模型是默认的，也是大多数 Minecraft 物品使用的。它们的父模型是 `GENERATED`。它们在物品栏中使用 2D 纹理，并在游戏内以 3D 形式渲染。例如船只、蜡烛或染料。

::: tabs

== 源代码

@[code transcludeWith=:::generated](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== 客户端物品

`generated/assets/example-mod/items/ruby.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/ruby.json)

== 物品模型

`generated/assets/example-mod/models/item/ruby.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/ruby.json)

你可以在 Minecraft 资源中的 [`generated.json` 文件](https://mcasset.cloud/1.21.10/assets/minecraft/models/item/generated.json) 中找到模型旋转、缩放和定位的精确默认值。

== 纹理

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/ruby_big.png" downloadURL="/assets/develop/data-generation/item-model/ruby.png">红宝石纹理</DownloadEntry>

:::

### 手持模型 {#handheld}

手持物品模型通常用于工具和武器（斧头、剑、三叉戟）。它们的旋转和定位与简单模型略有不同，以便在手中看起来更自然。

::: tabs

== 源代码

@[code transcludeWith=:::handheld](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== 客户端物品

`generated/assets/example-mod/items/guidite_axe.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/guidite_axe.json)

== 物品模型

`generated/assets/example-mod/models/item/guidite_axe.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_axe.json)

你可以在 Minecraft 资源中的 [`handheld.json` 文件](https://mcasset.cloud/1.21.10/assets/minecraft/models/item/handheld.json) 中找到模型旋转、缩放和定位的精确默认值。

== 纹理

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/guidite_axe_big.png" downloadURL="/assets/develop/data-generation/item-model/guidite_axe.png">Guidite 斧头纹理</DownloadEntry>

:::

### 可染色模型 {#dyeable}

可染色物品的方法会生成一个简单物品模型和一个指定染色颜色的客户端物品。此方法需要一个默认的十进制颜色值，在物品未染色时使用。皮革的默认值是 `0xFFA06540`。

:::: tabs

== 源代码

@[code transcludeWith=:::dyeable](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

::: warning 重要
你必须将你的物品添加到 `ItemTags.DYEABLE` 标签，才能在其物品栏中染色！
:::

== 客户端物品

`generated/assets/example-mod/items/leather_gloves.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/leather_gloves.json)

== 物品模型

`generated/assets/example-mod/models/item/leather_gloves.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/leather_gloves.json)

== 纹理

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/leather_gloves_big.png" downloadURL="/assets/develop/data-generation/item-model/leather_gloves.png">皮革手套纹理</DownloadEntry>

== 预览

![染色皮革手套](/assets/develop/data-generation/item-model/leather_gloves_dyeing.png)

::::

### 条件模型 {#conditional}

接下来，我们将介绍生成物品模型，这些模型在满足第二个参数 `BooleanProperty` 指定的特定条件时会改变外观。以下是一些示例：

| 属性            | 描述                                                               |
| --------------- | ------------------------------------------------------------------ |
| `IsKeybindDown` | 当按下指定按键时为真。                                             |
| `IsUsingItem`   | 当物品被使用时（例如用盾牌格挡时）为真。                           |
| `Broken`        | 当物品耐久度为 0 时（例如鞘翅破裂时改变纹理）为真。                 |
| `HasComponent`  | 当物品具有特定组件时为真。                                         |

第三和第四个参数分别是属性为 `true` 或 `false` 时要使用的模型。

:::: tabs

== 源代码

@[code transcludeWith=:::condition](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

::: warning 重要
要获取传递给 `ItemModelUtils.plainModel()` 的 `ResourceLocation`，请始终使用 `itemModelGenerator.createFlatItemModel()`，否则只会生成客户端物品，而不会生成物品模型！
:::

== 客户端物品

`generated/assets/example-mod/items/flashlight.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/flashlight.json)

== 物品模型

`generated/assets/example-mod/models/item/flashlight.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/flashlight.json)

`generated/assets/example-mod/models/item/flashlight_lit.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/flashlight_lit.json)

== 纹理

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/flashlight_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/flashlight_textures.zip">手电筒纹理</DownloadEntry>

== 预览

<VideoPlayer src="/assets/develop/data-generation/item-model/flashlight_turning_on.webm">手电筒开关</VideoPlayer>

::::

### 复合模型 {#composite}

复合物品模型由一个或多个纹理叠加而成。原版没有此类方法；你必须使用 `itemModelGenerator` 的 `itemModelOutput` 字段，并对其调用 `accept()`。

::: tabs

== 源代码

@[code transcludeWith=:::composite](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== 客户端物品

`generated/assets/example-mod/items/enhanced_hoe.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/enhanced_hoe.json)

== 物品模型

`generated/assets/example-mod/models/item/enhanced_hoe.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/enhanced_hoe.json)

`generated/assets/example-mod/models/item/enhanced_hoe_plus.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/enhanced_hoe_plus.json)

== 纹理

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/enhanced_hoe_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/enhanced_hoe_textures.zip">强化锄纹理</DownloadEntry>

:::

### 选择模型 {#select}

基于特定属性的值渲染物品模型。以下是一些示例：

| 属性                | 描述                                                                                      |
| ------------------- | ------------------------------------------------------------------------------------------ |
| `ContextDimension`  | 根据玩家所在的维度（主世界、下界、末地）渲染物品模型。                                        |
| `MainHand`          | 当物品装备在玩家主手时渲染物品模型。                                                          |
| `DisplayContext`    | 根据物品所在位置渲染物品模型（`ground`、`ground`、`head` 等）。                              |
| `ContextEntityType` | 根据持有物品的实体类型渲染物品模型。                                                          |

在此示例中，物品在维度间移动时会改变纹理：在主世界为绿色，在下界为红色，在末地为黑色。

::: tabs

== 源代码

@[code transcludeWith=:::select](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== 客户端物品

`generated/assets/example-mod/items/dimensional_crystal.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/dimensional_crystal.json)

== 物品模型

`generated/assets/example-mod/models/item/dimensional_crystal_overworld.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/dimensional_crystal_overworld.json)

`generated/assets/example-mod/models/item/dimensional_crystal_nether.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/dimensional_crystal_nether.json)

`generated/assets/example-mod/models/item/dimensional_crystal_end.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/dimensional_crystal_end.json)

== 纹理

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/dimensional_crystal_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/dimensional_crystal_textures.zip">维度水晶纹理</DownloadEntry>

== 预览

![维度水晶根据维度改变纹理](/assets/develop/data-generation/item-model/crystal.png)

:::

### 范围分发模型 {#range-dispatch}

基于数值属性的值渲染物品模型。接收一个物品和一个变体列表，每个变体都与一个值配对。示例包括指南针、弓和刷子。

支持很多属性，以下是一些例子：

| 属性          | 描述                                                                  |
| ------------- | --------------------------------------------------------------------- |
| `Cooldown`    | 根据物品的剩余冷却时间渲染物品模型。                                    |
| `Count`       | 根据堆叠数量渲染物品模型。                                              |
| `UseDuration` | 根据物品被使用时长渲染物品模型。                                          |
| `Damage`      | 根据攻击伤害（`minecraft:damage` 组件）渲染物品模型。                    |

此示例使用 `Count`，它根据堆叠数量将一个飞刀纹理变为三个。

::: tabs

== 源代码

@[code transcludeWith=:::range-dispatch](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== 客户端物品

`generated/assets/example-mod/items/throwing_knives.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/throwing_knives.json)

== 物品模型

`generated/assets/example-mod/models/item/throwing_knives_one.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/throwing_knives_one.json)

`generated/assets/example-mod/models/item/throwing_knives_two.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/throwing_knives_two.json)

`generated/assets/example-mod/models/item/throwing_knives_three.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/throwing_knives_three.json)

== 纹理

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/throwing_knives_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/throwing_knives_textures.zip">飞刀纹理</DownloadEntry>

== 预览

![飞刀根据数量改变纹理](/assets/develop/data-generation/item-model/throwing_knives_example.png)

:::

## 自定义物品模型 {#custom}

生成物品模型不一定只能使用原版方法；当然，你也可以创建自己的。在本节中，我们将为一个气球物品创建自定义模型。

本部分教程的所有字段和方法都在一个名为 `CustomItemModelGenerator` 的静态内部类中声明。

::: details 显示 `CustomItemModelGenerator`

@[code transcludeWith=:::custom-item-model-generator:::](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

:::

### 创建自定义父模型 {#custom-parent}

首先，让我们创建一个父物品模型，定义物品在游戏中的外观。假设我们希望气球看起来像简单物品模型，但经过放大。

为此，我们将创建 `resources/assets/example-mod/models/item/scaled2x.json`，将其父模型设置为 `item/generated` 模型，然后覆盖缩放。

@[code](@/reference/latest/src/main/resources/assets/example-mod/models/item/scaled2x.json)

<!-- TODO: 这不会使模型大到八倍吗？ -->
这将使模型大小变为简单模型的两倍。

### 创建 `ModelTemplate` {#custom-item-model}

接下来，我们需要创建 `ModelTemplate` 类的一个实例。它将代表我们模组内部的[父物品模型](#custom-parent)。

@[code transcludeWith=:::custom-item-model:::](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

`item()` 方法创建了一个新的 `ModelTemplate` 实例，指向我们之前创建的 `scaled2x.json` 文件。

纹理槽 `LAYER0` 代表 `#layer0` 纹理变量，稍后将被指向纹理的标识符替换。

### 添加自定义数据生成器方法 {#custom-datagen-method}

最后一步是创建一个自定义方法，该方法将在 `generateItemModels()` 方法中被调用，并负责生成我们的物品模型。

@[code transcludeWith=:::custom-item-datagen-method](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

让我们看看参数的作用：

1. `Item item`：我们要为其生成模型的物品。
2. `ItemModelGenerators generator`：与传递给 `generateItemModels()` 方法的相同。使用其字段。

首先，我们通过 `SCALED2X.create()` 获取物品的 `ResourceLocation`，传入一个 `TextureMapping` 和来自 `generator` 参数的 `modelOutput`。

然后，我们将使用它的另一个字段 `itemModelOutput`（本质上充当消费者），并使用 `accept()` 方法，以使模型实际生成。

### 调用自定义方法 {#custom-call}

现在，我们只需要在 `generateItemModels()` 方法中调用我们的方法。

@[code transcludeWith=:::custom-balloon](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

别忘了添加纹理文件！

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/balloon_big.png" downloadURL="/assets/develop/data-generation/item-model/balloon.png">气球纹理</DownloadEntry>

## 来源与链接 {#sources-and-links}

你可以在 [Fabric API](https://github.com/FabricMC/fabric/blob/1.21.10/fabric-data-generation-api-v1/src/) 中查看示例测试，或查阅本文档的 [示例模组](https://github.com/FabricMC/fabric-docs/tree/main/reference) 以获取更多信息。
