---
title: 方块模型生成
description: 通过 datagen 生成方块模型和方块状态的指南。
authors:
  - Fellteros
  - IMB11
  - its-miroma
  - natri0
---

:::info 前提
首先，请确保你已完成 [Datagen 设置](./setup) 。
:::

## 设置 {#setup}

首先，我们需要创建 ModelProvider。 创建一个 `extends FabricModelProvider` 类。 实现两个抽象方法：`generateBlockStateModels` 和 `generateItemModels`。
最后，创建一个与 super 匹配的构造函数。

@[code lang=java transcludeWith=:::datagen-model:provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

在 `onInitializeDataGenerator` 方法中的 `DataGeneratorEntrypoint` 中注册此类。

## 方块状态和方块模型 {#blockstates-and-block-models}

```java
@Override
public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
}
```

对于方块模型，我们将主要关注 `generateBlockStateModels` 方法。 请注意参数 `BlockStateModelGenerator blockStateModelGenerator`——该对象将负责生成所有 JSON 文件。
以下是一些可用于生成所需模型的便捷示例：

### 简单 Cube All {#simple-cube-all}

@[code lang=java transcludeWith=:::datagen-model:cube-all](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

这是最常用的函数。 它为普通的 `cube_all` 方块模型生成一个 JSON 模型文件。 所有六个面都使用一个纹理，在本例中我们使用 `steel_block`。

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/block/steel_block.json)

它还生成一个方块状态 JSON 文件。 由于我们没有方块状态属性（例如轴、朝向等），因此一个变体就够了，并且每次放置方块时都会使用。

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/blockstates/steel_block.json)

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/steel_block_big.png" downloadURL="/assets/develop/data-generation/block-model/steel_block.png">钢块</DownloadEntry>

### 单例 {#singletons}

`registerSingleton` 方法根据你传入的 `TexturedModel` 和单个方块状态变体提供 JSON 模型文件。

@[code lang=java transcludeWith=:::datagen-model:cube-top-for-ends](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

该方法将为一个普通立方体生成模型，该立方体使用纹理文件 `pipe_block` 作为侧面，使用纹理文件 `pipe_block_top` 作为顶部和底部。

@[code](@/reference/latest/src/main/generated/assets/fabric-docs-reference/models/block/pipe_block.json)

:::tip
如果您无法选择应该使用哪个 `TextureModel`，请打开 `TexturedModel` 类并查看 [`纹理映射`](#using-texture-map)！
:::

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/pipe_block_textures_big.png" downloadURL="/assets/develop/data-generation/block-model/pipe_block_textures.zip">管块</DownloadEntry>

### 方块纹理池 {#block-texture-pool}

@[code lang=java transcludeWith=:::datagen-model:block-texture-pool-normal](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

另一个有用的方法是 `registerCubeAllModelTexturePool`：通过传入“基础方块”来定义纹理，然后附加具有相同纹理的“子方块”。
在这种情况下，我们传入了 `RUBY_BLOCK`，因此楼梯、台阶和栅栏将使用 `RUBY_BLOCK` 纹理。

:::warning
它还将为“基础方块”生成一个[简单立方体所有 JSON 模型](#simple-cube-all)，以确保它具有方块模型。

请注意这一点，如果您正在更改这种特定方块的方块模型，它可能导致错误。
:::

你还可以附加一个 `BlockFamily`，它将为其所有“子项”生成模型。

@[code lang=java transcludeWith=:::datagen-model:family-declaration](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

@[code lang=java transcludeWith=:::datagen-model:block-texture-pool-family](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/ruby_block_big.png" downloadURL="/assets/develop/data-generation/block-model/ruby_block.png">红宝石块</DownloadEntry>

### 门与活板门 {#doors-and-trapdoors}

@[code lang=java transcludeWith=:::datagen-model:door-and-trapdoor](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

门和活板门略有不同。 在这里，你必须制作三个新纹理——两个用于门，一个用于活板门。

1. 门：
  - 分为两部分——上半部分和下半部分。 \*\*每个都需要独自的纹理：\*\*在本例中，`ruby_door_top` 用于上半部分，`ruby_door_bottom` 用于下半部分。
  - `registerDoor()` 方法将为门的所有方向（打开和关闭）创建模型。
  - \*\*你还需要一个物品纹理！\*\*将其放在 `assets/mod_id/textures/item/` 文件夹中。
2. 活板门：
  - 在这里，只需要一个纹理，在本例中名为 `ruby_trapdoor`。 它将被用于所有面。
  - 由于 `TrapdoorBlock` 具有 `FACING` 属性，你可以使用注释掉的方法生成具有旋转纹理的模型文件 = 活板门将是“可定向的”。 否则，无论它面向哪个方向，看起来都会一样。

<DownloadEntry visualURL="/assets/develop/data-generation/block-model/ruby_door_trapdoor_big.png" downloadURL="/assets/develop/data-generation/block-model/ruby_door_trapdoor_textures.zip">红宝石门和活板门</DownloadEntry>

## 自定义方块类 {#custom-block-class}

在本节中，我们将创建具有橡木原木纹理的垂直橡木原木台阶模型。

_点 2. - 6. 在名为 `CustomBlockStateModelGenerator` 的内部静态辅助类中声明。_

### 自定义方块模型 {#custom-block-models}

创建一个具有 `FACING` 属性和 `SINGLE` 布尔属性的 `VerticalSlab` 方块，类似于 [方块状态](../blocks/blockstates) 教程中的那样。 `SINGLE` 将指示是否存在两块台阶。
然后你应该重写 `getOutlineShape` 和 `getCollisionShape`，以便正确渲染轮廓，并且方块具有正确的碰撞形状。

@[code lang=java transcludeWith=:::datagen-model-custom:voxels](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

@[code lang=java transcludeWith=:::datagen-model-custom:collision](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

还要重写 `canReplace()` 方法，否则无法使台阶成为完整方块。

@[code lang=java transcludeWith=:::datagen-model-custom:replace](@/reference/latest/src/main/java/com/example/docs/block/custom/VerticalSlabBlock.java)

然后就大功告成了！ 你现在可以去测试方块并将其放置在游戏中了。

### 父方块模型 {#parent-block-model}

现在，我们来创建一个父方块模型。 它可以确定尺寸、在手中或其他槽位中的位置以及纹理的 `x` 和 `y` 坐标。
建议使用诸如 [Blockbench](https://www.blockbench.net/) 之类的编辑器来完成此操作，因为手动制作非常繁琐。 它看起来应该是这样的：

@[code lang=json](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/block/vertical_slab.json)

请参阅 [方块状态如何格式化（仅英文版）](https://minecraft.wiki/w/Blockstates_definition_format) 来了解更多信息。
请注意 `#bottom`、`#top`、`#side` 关键字。 它们充当变量，可以由以此为父级的模型进行设置：

```json
{
  "parent": "minecraft:block/cube_bottom_top",
  "textures": {
    "bottom": "minecraft:block/sandstone_bottom",
    "side": "minecraft:block/sandstone",
    "top": "minecraft:block/sandstone_top"
  }
}
```

`bottom` 值将替换 `#bottom` 占位符，依此类推。 **将其放在 `resources/assets/mod_id/models/block/` 文件夹中。**

### 自定义模型 {#custom-model}

我们还需要 `Model` 类的实例。 它代表我们模型内部的实际[父方块模型](#parent-block-model)。

@[code lang=java transcludeWith=:::datagen-model-custom:model](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

`block()` 方法创建一个新的 `Model`，指向 `resources/assets/mod_id/models/block/` 文件夹内的 `vertical_slab.json` 文件。
`TextureKey` 将“占位符”（#bottom、#top...） 表示为一个对象。

### 使用纹理映射 {#using-texture-map}

`TextureMap` 是干什么的？ 它实际上提供了指向纹理的标识符。 从技术上讲，它的行为类似于普通映射——将 `TextureKey`（键）与 `Identifier`（值）关联起来。

你可以使用原版的，例如 `TextureMap.all()`（它将所有 TextureKey 与相同的标识符关联），或者创建一个新实例然后用 `.put()` 将键与值关联起来。

:::tip
`TextureMap.all()` 将所有的 TextureKey 与相同的标识符关联起来，无论它们有多少！
:::

因为我们想要用橡木原木纹理，但是有 `BOTTOM`、`TOP` 和 `SIDE` 的 `TextureKey`，所以我们需要创建一个新的。

@[code lang=java transcludeWith=:::datagen-model-custom:texture-map](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

`bottom`（底部）和 `top`（顶部）面使用 `oak_log_top.png`，侧面则使用 `oak_log.png`。

:::warning
TextureMap 中的所有 `TextureKey` **必须**与父方块模型中的所有 `TextureKey` 匹配！
:::

### 自定义 `BlockStateSupplier` 方法 {#custom-supplier-method}

`BlockStateSupplier` 包含所有方块状态变体、旋转以及其他选项（如 uvlock）。

@[code lang=java transcludeWith=:::datagen-model-custom:supplier](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

首先，我们使用 `VariantsBlockStateSupplier.create()` 创建一个新的 `VariantsBlockStateSupplier`。
然后我们创建一个新的 `BlockStateVariantMap`，它包含方块的所有变体的参数，在本例中是 `FACING` 和 `SINGLE`，并将其传递给 `VariantsBlockStateSupplier`。
指定使用 `.register()` 时使用哪个模型和哪些变换（uvlock、rotation）。
例如：

- 在第一行，方块朝北，并且是单个的 => 我们使用没有旋转的模型。
- 在第四行，方块朝西，并且是单个的 => 我们将模型沿 Y 轴旋转 270°。
- 在第六行，方块朝东，但不是单个的 => 看起来像普通的橡木原木 => 我们不必旋转它。

### 自定义 Datagen 方法 {#custom-datagen-method}

最后一步——创建一个可以调用的实际方法并生成 JSON。
但这些参数是用来做什么的呢？

1. `BlockStateModelGenerator generator`，与传递到 `generateBlockStateModels` 的生成器相同。
2. `Block vertSlabBlock` 是我们将生成 JSON 的方块。
3. `Block fullBlock` 是当 `SINGLE` 属性为 false 时使用的模型 = 台阶方块看起来像一个完整方块。
4. `TextureMap textures` 定义了模型使用的实际纹理。 参见[使用纹理映射](#using-texture-map)章节。

@[code lang=java transcludeWith=:::datagen-model-custom:gen](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

首先，我们使用 `VERTICAL_SLAB.upload()` 获取单个台阶模型的 `Identifier`。 然后我们使用 `ModelIds.getBlockModelId()` 获取完整方块模型的 `Identifier`，并将这两个模型传递给 `createVerticalSlabBlockStates`。
`BlockStateSupplier` 被传递到 `blockStateCollector`，从而实际生成 JSON 文件。
另外，我们使用 `BlockStateModelGenerator.registerParentedItemModel()` 为垂直台阶物品创建一个模型。

就这样！ 现在剩下要做的就是在 `ModelProvider` 中调用方法：

@[code lang=java transcludeWith=:::datagen-model-custom:method-call](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

## 来源和链接 {#sources-and-links}

您可以查看 [Fabric API](https://github.com/FabricMC/fabric/blob/1.21.4/fabric-data-generation-api-v1/src/) 中的示例测试和此文档的 [参考模组](https://github.com/FabricMC/fabric-docs/tree/main/reference) 以获取更多信息。

您还可以通过浏览模组的开源代码找到更多使用自定义数据生成方法的示例，例如 Fellteros 的 [Vanilla+ Blocks](https://github.com/Fellteros/vanillablocksplus) 和 [Vanilla+ Verticals](https://github.com/Fellteros/vanillavsplus)。
