---
title: 使用绘制上下文
description: 学习如何使用 DrawContext 类来渲染各种图形、文字、纹理。
authors:
  - IMB11

search: false
---

# 使用绘制上下文

本文假设您已经看过[基本渲染概念](./basic-concepts)。

`DrawContext` 是控制渲染的核心类。 它提供了诸多渲染图形、文字、纹理的方法，此外还用来操纵 `MatrixStack` 和 `BufferBuilder`。

## 绘制图形

使用 `DrawContext` 绘制**矩形**十分容易。 如果您想绘制三角形或其他不规则图形，您需要使用 `BufferBuilder` 手动添加图形顶点信息。

### 绘制矩形

您可以使用 `DrawContext#fill` 方法来绘制一个实心矩形。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![矩形](/assets/develop/rendering/draw-context-rectangle.png)

### 绘制边框

假设我们想勾勒出我们刚才绘制的矩形的轮廓。 我们可以使用 `DrawContext#drawBorder` 方法来绘制轮廓。

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![带边框的矩形](/assets/develop/rendering/draw-context-rectangle-border.png)

### 绘制线条

我们可以使用 `DrawContext#drawHorizontalLine` 和 `DrawContext#drawVerticalLine` 来绘制线条。

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![线条](/assets/develop/rendering/draw-context-lines.png)

## 裁剪

`DrawContext` 有一套内建的裁剪功能。 它可以用来裁剪渲染区域。 这个功能在绘制某些元素时十分有用，比如悬浮提示，或者其他不应该超出指定渲染区域的界面元素。

### 使用裁剪功能

:::tip
裁剪区域可以内嵌！ 但是请一定配对 `enableScissor` 和 `disableScissor`，否则错误的裁剪区域将影响到其他界面元素。
:::

要启用裁剪功能，只需调用 `DrawContext#enableScissor` 方法。 同样地，调用 `DrawContext#disableScissor` 方法以禁用裁剪功能。

@[code lang=java transcludeWith=:::4](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![裁剪区域](/assets/develop/rendering/draw-context-scissor.png)

如您所见，即使我们让游戏尝试用渐变色铺满整个界面，它却只能在裁剪区域内绘制。

## 绘制纹理

注意，不存在唯一“正确”的绘制纹理的方法，因为 `drawTexture` 有很多重载。 本节内容只会涵盖最常用的方法。

### 绘制整个纹理

一般来说，我们推荐您使用需要指定 `textureWidth` 和 `textureHeight` 参数的 `drawTexture` 方法重载。 因为如果使用不指定的重载， `DrawContext` 会假设您的纹理文件尺寸是 256x256，而您的纹理文件不一定是这个尺寸，于是渲染结果就不一定正确。

@[code lang=java transcludeWith=:::5](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![绘制整个纹理](/assets/develop/rendering/draw-context-whole-texture.png)

### 绘制纹理的一部分

在这个情形中，我们需要指定纹理区域的 `u` 和 `v`。 这俩参数用于指定纹理区域左上角的坐标。另外，`regionWidth` 和 `regionHeight` 参数用于指定纹理区域的尺寸。

我们以此纹理为例。

![配方书纹理](/assets/develop/rendering/draw-context-recipe-book-background.png)

如果我们只希望绘制包含放大镜的区域，我们可以使用如下 `u`、`v`、`regionWidth`、`regionHeight` 值：

@[code lang=java transcludeWith=:::6](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![绘制纹理的一部分](/assets/develop/rendering/draw-context-region-texture.png)

## 绘制文字

`DrawContext` 提供了许多不言自明的渲染文字的方法，您可以自行尝试，此处不再赘述。

假设我们想在界面上绘制 `Hello World`。 我们可以使用 `DrawContext#drawText` 方法来完成。

@[code lang=java transcludeWith=:::7](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![绘制文字](/assets/develop/rendering/draw-context-text.png)
