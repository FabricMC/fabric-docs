---
title: 物品模型生成
description: 通过数据生成器生成物品模型的指南。
authors:
  - CelDaemon
  - Fellteros
  - skycatminepokie
  - VatinMc
---

<!---->

:::info 前置条件

首先，请确保你已完成[数据生成器设置](./setup)并且创建了你的[第一个物品](../items/first-item)。

:::

对于每一个需要生成模型的物品，我们必须创建两个独立的 JSON 文件：

1. 一个**物品模型**，用于定义物品的纹理、旋转和整体外观。 将生成在 `generated/assets/example-mod/models/item` 目录下。
2. 一个**客户端物品**，根据组件、交互等不同标准来定义使用的模型。 将生成在 `generated/assets/example-mod/items` 目录下。

## 设置 {#setup}

首先，我们需要创建 ModelProvider。

::: tip

可以重新使用在[方块模型生成](./block-models#setup)中创建的 `FabricModelProvider`。

:::

创建一个继承 `FabricModelProvider` 的类，并且实现两个抽象方法：`generateBlockStateModels` 和 `generateItemModels`。
然后，创建一个匹配 `super` 的构造器。

@[code transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

接着在 `DataGeneratorEntrypoint` 入口点的 `onInitializeDataGenerator` 方法里注册这个类。

@[code transcludeWith=:::datagen-models:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## 内置物品模型 {#built-in}

对于物品模型，我们将使用 `generateItemModels` 方法， 其形参 `ItemModelGenerators itemModelGenerator` 负责生成物品模型，并包含相应的方法。

以下是最常用的物品模型生成方法。

### 简单 {#simple}

简单物品模型即是默认的模型，也是大多数原版物品所使用的模型， 其父模型为 `GENERATED`， 在背包中使用 2D 纹理，但在游戏中渲染为 3D， 例如船、蜡烛和染料。

::: tabs

== 源代码

@[code transcludeWith=:::generated](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== 客户端物品

`generated/assets/example-mod/items/ruby.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/ruby.json)

== 物品模型

`generated/assets/example-mod/models/item/ruby.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/ruby.json)

可以在 [Minecraft 资源中的 `generated.json` 文件](https://mcasset.cloud/1.21.11/assets/minecraft/models/item/generated.json)中找到模型的旋转、缩放和定位的默认值。

== 纹理

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/ruby_big.png" downloadURL="/assets/develop/data-generation/item-model/ruby.png">红宝石纹理</DownloadEntry>

:::

### 手持{#handheld}

手持物品通常用于工具和武器（斧、剑、三叉戟）。 这些物品的旋转和定位与简单模型有点区别，从而在手中看上去更自然。

::: tabs

== 源代码

@[code transcludeWith=:::handheld](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== 客户端物品

`generated/assets/example-mod/items/guidite_axe.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/guidite_axe.json)

== 物品模型

`generated/assets/example-mod/models/item/guidite_axe.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_axe.json)

可以在 [Minecraft 资源中的 `handheld.json` 文件](https://mcasset.cloud/1.21.11/assets/minecraft/models/item/handheld.json)中找到模型的旋转、缩放和定位的默认值。

== 纹理

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/guidite_axe_big.png" downloadURL="/assets/develop/data-generation/item-model/guidite_axe.png">Guidite 斧纹理</DownloadEntry>

:::

### 可染色{#dyeable}

可染色物品的方法会生成一个简单的物品模型和客户端物品，并会指定着色（tint）颜色。 此方法需要一个默认的十进制颜色值，用于未染色的物品。 皮革的默认值为 `0xFFA06540`。

:::: tabs

== 源代码

@[code transcludeWith=:::dyeable](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

:::warning 重要

需要将物品添加到 `ItemTags.DYEABLE` 标签，才能在物品栏中染色！

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

![给皮革手套染色](/assets/develop/data-generation/item-model/leather_gloves_dyeing.png)

::::

### 条件{#conditional}

接下来，我们来尝试生成会在当由第二个参数 `BooleanProperty` 指定的特定条件满足时改变其外观的物品模型。 这是其中的一些：

| 属性              | 描述                               |
| --------------- | -------------------------------- |
| `IsKeybindDown` | 当特定按键按下时为 true。                  |
| `IsUsingItem`   | 当物品被使用时为 true（例如用盾牌格挡）。          |
| `Broken`        | 当物品的耐久为 0 时为 true（例如鞘翅被破坏时改变纹理）。 |
| `HasComponent`  | 当物品拥有特定的组件时为 true。               |

第 3 和第 4 个参数，分别是在属性为 `true` 和 `false` 时使用的模型。

:::: tabs

== 源代码

@[code transcludeWith=:::condition](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

:::warning 重要

要获得传入 `ItemModelUtils.plainModel()` 的 `Identifier`，始终使用 `itemModelGenerator.createFlatItemModel()`，否则只会生成客户端物品，而不是物品模型！

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

### 复合{#composite}

复合物品模型会由一个或更多个纹理层叠组成。 这个没有原版的方法，你需要使用 `itemModelGenerator` 的 `itemModelOutput` 字段，然后调用 `accept()`。

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

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/enhanced_hoe_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/enhanced_hoe_textures.zip">增强的锄纹理</DownloadEntry>

:::

### 选择{#select}

基于特定属性的值渲染物品模型。 有这样一些：

| 属性                  | 描述                                                         |
| ------------------- | ---------------------------------------------------------- |
| `ContextDimension`  | 基于玩家所在的维度渲染物品模型（主世界、下界、末地）。                                |
| `MainHand`          | 当物品装备在玩家的主手时渲染物品模型。                                        |
| `DisplayContext`    | 基于物品所在的地方渲染物品模型（`ground`：地面，`fixed`：固定在物品展示框中，`hand`：手上…）。 |
| `ContextEntityType` | 基于持有物品的实体渲染物品模型。                                           |

在此例中，物品会在不同维度时改变纹理：在主世界是绿的，下界是红的，末地是黑的。

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

![基于维度改变纹理的维度水晶](/assets/develop/data-generation/item-model/crystal.png)

:::

### 范围派发{#range-dispatch}

基于数字属性的值渲染物品模型。 接收一个物品和变种列表，每个都与值配对。 示例包括指南针、弓和刷子。

有不少支持的属性，这里是一些例子：

| 属性            | 描述                                   |
| ------------- | ------------------------------------ |
| `Cooldown`    | 基于物品的剩余冷却时间渲染物品模型。                   |
| `Count`       | 基于物品堆的大小渲染物品模型。                      |
| `UseDuration` | 基于物品使用了多久渲染物品模型。                     |
| `Damage`      | 基于攻击伤害渲染物品模型（`minecraft:damage` 组件）。 |

这是个使用 `Count` 的例子，纹理从一个刀改变为三个，基于物品堆的大小。

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

![基于数量改变纹理的飞刀](/assets/develop/data-generation/item-model/throwing_knives_example.png)

:::

## 自定义物品模型{#custom}

生成物品模型不仅可以通过原版方法完成，你当然还可以创建自己的。 在本段中，我们会为一个气球物品创建自定义的模型。

本教程此部分的所有字段和模型都声明在叫做 `CustomItemModelGenerator` 的静态内部类中。

:::details 显示 `CustomItemModelGenerator`

@[code transcludeWith=:::custom-item-model-generator:::](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

:::

### 创建自定义父模型{#custom-parent}

首先，先创建一个父物品模型，定义物品在游戏内如何显示。 比如说，我们要让气球看上去像普通的物品模型，但放大。

要做到这个，我们先创建 `resources/assets/example-mod/models/item/scaled2x.json`，将其父模型设置为 `item/generated` 模型，然后覆盖缩放。

@[code](@/reference/latest/src/main/resources/assets/example-mod/models/item/scaled2x.json)

这会使模型看上去有普通物品的两倍大小。

### 创建 `ModelTemplate`{#custom-item-model}

接下来我们需要创建 `ModelTemplate` 类的实例， 代表我们模组内部的实际[父物品模型](#custom-parent)。

@[code transcludeWith=:::custom-item-model:::](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

`item()` 方法会创建新的 `ModelTemplate` 实例，指向我们刚刚创建的 `scaled2x.json` 文件。

TextureSlot `LAYER0` 代表 `#layer0` 纹理变量，会被指向纹理的 ID 替代。

### 添加自定义数据生成方法{#custom-datagen-method}

最后一步是创建自定义方法，会在 `generateItemModels()` 中调用，我们在此需要创建自己的物品模型。

@[code transcludeWith=:::custom-item-datagen-method](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

我们看看参数是做什么的：

1. `Item item`：物品，为此物品生成模型。
2. `ItemModelGenerators generator`：和传入 `generateItemModels()` 的相同。 用于其字段。

首先，先使用 `SCALED2X.create()` 得到 `Identifier`，从我们的 `generator` 参数传入一个 `TextureMapping` 和 `modelOutput`。

然后，使用其字段的另一个，`itemModelOutput`（本质上用作一个 consumer），并使用 `accept()` 方法，从而让模型实际生成。

### 调用自定义方法{#custom-call}

现在，我们只需要在 `generateItemModels()` 方法中调用我们的方法。

@[code transcludeWith=:::custom-balloon](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

不要忘记添加纹理文件！

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/balloon_big.png" downloadURL="/assets/develop/data-generation/item-model/balloon.png">气球纹理</DownloadEntry>

## 来源和链接 {#sources-and-links}

你可以查看 [Fabric API](https://github.com/FabricMC/fabric/blob/1.21.11/fabric-data-generation-api-v1/src/) 中的示例测试和此文档的[示例模组](https://github.com/FabricMC/fabric-docs/tree/main/reference)以获取更多信息。
