---
title: 透明度与染色
description: 学习如何动态操控方块外观并为其染色。
authors:
  - PEQB1145
---

有时你可能希望方块的视觉效果在游戏中进行特殊处理。例如，有些方块可能显示为透明，而另一些方块可能需要应用染色效果。

让我们看看如何操控方块的外观。

以注册一个方块为例。如果你对这个过程不熟悉，请先阅读关于[方块注册](./first-block)的内容。

@[code lang=java transcludeWith=:::block](@/reference/latest/src/main/java/com/example/docs/appearance/ExampleModAppearance.java)

请确保添加：

- 在`/blockstates/waxcap.json`中添加一个[方块状态](./blockstates)。
- 在`/models/block/waxcap.json`中添加一个[模型](./block-models)。
- 在`/textures/block/waxcap.png`中添加一张[纹理](./first-block#models-and-textures)。

如果一切正确，你将在游戏中看到这个方块。但是，你会发现当方块放置时，它的外观看起来不太对。

![错误的方块外观](/assets/develop/transparency-and-tinting/block_appearance_0.png)

这是因为带有透明度的纹理需要一些额外的设置。

## 操控方块外观 {#manipulating-block-appearance}

即使你的方块纹理是透明或半透明的，它依然会显示为不透明。要解决这个问题，你需要设置方块的 _区块切面层级_。

区块切面层级是用于对不同类型方块表面进行分类渲染的类别。这允许游戏为每种类型使用正确的视觉效果和优化。

我们需要用正确的区块切面层级注册我们的方块。原版提供了以下选项。

- `SOLID`：默认值，是一个没有任何透明度的实心方块。
- `CUTOUT` 和 `CUTOUT_MIPPED`：一个利用了透明度的方块，例如玻璃或花朵。`CUTOUT_MIPPED`在远距离看起来效果更好。
- `TRANSLUCENT`：一个利用了半透明（部分透明）像素的方块，例如染色玻璃或水。

我们的示例具有透明度，因此将使用`CUTOUT`。

在你的**客户端初始化器**中，使用Fabric API的`BlockRenderLayerMap`以正确的`ChunkSectionLayer`注册你的方块。

@[code lang=java transcludeWith=:::block_render_layer_map](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

现在，你的方块应该具有正确的透明度了。

![正确的方块外观](/assets/develop/transparency-and-tinting/block_appearance_1.png)

## 方块颜色提供器 {#block-color-providers}

尽管我们的方块在游戏中看起来很好，但其纹理是灰度的。我们可以动态应用一个颜色染色效果，就像原版树叶根据生物群系改变颜色那样。

Fabric API提供了`ColorProviderRegistry`来注册一个染色颜色提供器，我们将用它来动态地为方块上色。

让我们使用这个API来注册一个染色效果，使得当我们的`Waxcap`方块放置在草上时，它看起来是绿色的，否则就看起来是棕色的。

在你的**客户端初始化器**中，将你的方块注册到`ColorProviderRegistry`，并附上相应的逻辑。

@[code lang=java transcludeWith=:::color_provider](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

现在，方块将根据其放置的位置而被染色。

![带颜色提供器的方块](/assets/develop/transparency-and-tinting/block_appearance_2.png)
