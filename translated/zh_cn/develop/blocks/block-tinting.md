---
title: 方块着色
description: 学习如何动态地着色方块。
authors:
  - cassiancc
  - dicedpixels
---

有时，你可能希望在游戏中对方块的外观进行特殊处理。 例如，一些方块，比如草，会被应用一些着色。

我们来看看如何操控方块的外观。

在本例中，我们先来注册一个方块。 如果你不熟悉这个过程，请先阅读[方块注册](./first-block)。

@[code lang=java transcludeWith=:::waxcap-tinting](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

请务必添加：

- `/blockstates/waxcap.json` 中的[方块状态](./blockstates)
- `/models/block/waxcap.json` 中的[模型](./block-models)
- `/textures/block/waxcap.png` 中的[纹理](./first-block#models-and-textures)

如果一切正确，你应该能在游戏中看到这个方块。

![正确的方块外观](/assets/develop/transparency-and-tinting/block_appearance_1.png)

## 方块着色源{#block-tint-sources}

我们的方块尽管在游戏中看起来不错，但纹理是灰度的。 我们可以动态地应用颜色着色，就像原版游戏中的树叶会根据生物群系变色一样。

Fabric API 提供了 `BlockColorRegistry` 来注册 `BlockTintSource` 的列表，可以用来来动态地为方块着色。

我们使用这个 API 注册一个颜色，这样当我们的 Waxcap 块放置在草地上时是绿色的，其他情况则是棕色的。

在你的**客户端初始化器**中，将你的代码块注册到 `ColorProviderRegistry`，并附上相应的逻辑。

@[code lang=java transcludeWith=:::color_provider](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

现在，方块的颜色将根据其放置的位置而变化。

![带颜色提供器的方块](/assets/develop/transparency-and-tinting/block_appearance_2.png)
