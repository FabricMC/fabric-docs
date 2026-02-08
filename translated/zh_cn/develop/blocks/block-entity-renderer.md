---
title: 方块实体渲染器
description: 了解如何使用方块实体渲染器为渲染增色。
authors:
  - natri0
---

有的时候，用 Minecraft 自带的模型格式和渲染器并不足够。 如果需要为你的方块的视觉效果添加动态渲染，则需要使用 `BlockEntityRenderer`。

举个例子，让我们来制作一个在 [方块实体](../blocks/block-entities) 文章中出现的 Counter Block，这个方块会在方块顶部显示点击次数。

## 创建一个 BlockEntityRenderer {#creating-a-blockentityrenderer}

方块实体渲染使用提交/渲染系统，首先将渲染对象所需的数据提交到屏幕，然后游戏使用其提交状态渲染对象。

在为 `CounterBlockEntity` 创建 `BlockEntityRenderer` 时，如果您的项目对客户端和服务器端使用了不同的源代码集，则需要确保将该渲染器类放置于对应的源代码集中，例如客户端相关的类应放在 `src/client/` 目录下。 直接访问 `src/main/` 源代码集中与渲染相关的类并不安全，因为这些类可能已在服务器上加载。

首先，我们需要为 `CounterBlockEntity` 创建一个 `BlockEntityRenderState` 来保存将用于渲染的数据。 在这种情况下，我们需要 `clicks` 在渲染期间可用。

@[code transcludeWith=::render-state](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderState.java)

然后我们为 `CounterBlockEntity` 创建一个 `BlockEntityRenderer`。

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

新类有一个构造函数，以 `BlockEntityRendererProvider.Context` 作为参数。 `Context` 有几个非常有用的渲染辅助工具，比如 `ItemRenderer` 或 `TextRenderer`。
此外，通过包含这样一个构造函数，就可以将该构造函数用作 `BlockEntityRendererProvider` 函数式接口本身：

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/ExampleModBlockEntityRenderer.java)

我们将重写一些方法来设置渲染状态以及设置渲染逻辑的 `render` 方法。

`createRenderState` 可以用于初始化渲染状态。

@[code transclude={31-34}](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

`extractRenderState` 可以用于使用实体数据更新渲染状态。

@[code transclude={36-42}](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

你应该在 `ClientModInitializer` 类中注册你的方块实体渲染器。

`BlockEntityRenderers` 是一个注册表，它将每个具有自定义渲染代码的 `BlockEntityType` 映射到其各自的 `BlockEntityRenderer`。

## 在方块上绘制 {#drawing-on-blocks}

现在我们有了渲染器，就可以开始绘制了。 `render` 方法在每一帧都会被调用，这就是渲染魔法发生的地方。

### 四处移动 {#moving-around}

首先，我们需要偏移和旋转文本，使其位于方块的顶部。

::: info

顾名思义，`PoseStack` 是一个_堆栈_，这意味着你可以压入和弹出变换。
一个好的经验法则是在 `render` 方法开始时压入一个新的方块，并在结束时弹出，这样一个方块的渲染就不会影响到其他方块。

有关 `PoseStack` 的更多信息，请参阅[基本渲染概念文章](../rendering/basic-concepts)。

:::

为了更容易理解所需的平移和旋转，不妨将其可视化。 在该图中，绿色方块是绘制文本的位置，默认情况下位于方块的最左下角：

![默认渲染位置](/assets/develop/blocks/block_entity_renderer_1.png)

因此，首先我们需要在 X 轴和 Z 轴上将文本移动到方块的一半，然后在 Y 轴上将其移动到方块的顶部：

![绿色块在最上面的中心点](/assets/develop/blocks/block_entity_renderer_2.png)

这些都可以以单个 `translate` 调用来实现：

```java
matrices.translate(0.5, 1, 0.5);
```

我们已经完成了 _平移_，接下来是 _旋转_ 和 _缩放_。

默认情况下，文字会在 XY 平面上渲染，所以我们需要将其绕 X 轴旋转 90 度，让他面向上方的 XZ 平面：

![绿色块位于最上面的中心点，朝上](/assets/develop/blocks/block_entity_renderer_3.png)

`PoseStack` 没有 `rotate` 函数，我们需要使用 `multiply` 和 `Axis.XP`：

```java
matrices.multiply(Axis.XP.rotationDegrees(90));
```

那么现在的文字就在正确的位置了，但是文字现在太大了。 `BlockEntityRenderer` 映射整个方块到一个 `[-0.5, 0.5]` 的立方体，而 `TextRenderer` 使用 `[0, 9]` 的 Y 坐标。 因此，我们需要将其缩小到原来的 1/18：

```java
matrices.scale(1/18f, 1/18f, 1/18f);
```

那么，我们整个的变换看起来就像这样：

@[code transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

### 绘制文本 {#drawing-text}

如前所述，传递到渲染器构造函数的 `Context` 包含一个 `TextRenderer`，我们可以用它来测量文本（`width`），这对于居中很有用。

为了绘制文本，我们将向渲染队列提交必要的数据。 由于我们正在绘制一些文本，因此我们可以使用通过传递到 `render` 方法的 `OrderedRenderCommandQueue` 实例提供的 `SubmitText` 方法。

@[code transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

`submitText` 方法接受许多的参数，但最重要的几个是：

- 要绘制的 `FormattedCharSequence`；
- 文字的 `X` 和 `Y` 坐标；
- 文字的 RGB 颜色 `color` 值；
- 描述其变换方式的 `Matrix4f` 矩阵（要从 `PoseStack` 中获取该矩阵，我们可以使用 `.last().pose()` 来获取最顶层条目的 `Matrix4f` 矩阵）。

经过我们的努力，这就是最终结果：

![顶部有数字的计数器方块](/assets/develop/blocks/block_entity_renderer_4.png)
