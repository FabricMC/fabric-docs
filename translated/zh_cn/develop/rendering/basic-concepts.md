---
title: 基本渲染概念
description: 学习 Minecraft 渲染引擎的基本概念。
authors:
  - "0x3C50"
  - IMB11
  - MildestToucan
---

<!---->

::: warning

尽管 Minecraft 是使用 OpenGL 构建的，但是从 1.17 版本开始，就不再能使用旧版 OpenGL 方法渲染自己的东西， 而是必须使用新的 `BufferBuilder`（缓冲构建器）系统，将渲染数据格式化并上传到 OpenGL 以绘制。

总的来说，您应该使用 Minecraft 的渲染系统，否则就得利用 `GL.glDrawElements()` 来构建自己的。

:::

:::warning 重要更新

从 1.21.6 开始，渲染管线实现了大变化，比如移动到 `RenderType` 和 `RenderPipeline` 以及更重要的还有 `RenderState`，其最终目标是在绘制当前帧时可以准备下一帧。 在“准备”阶段，所有用于渲染的游戏数据都会提取至 `RenderState`，所以另一个线程可专心渲染当前帧，同时也能提取下一帧。

例如，在 1.21.8 版本中，GUI 渲染采用了这种模型，`GuiGraphics` 方法只是简单地添加到渲染状态。 实际上传到 `BufferBuilder` 的操作发生在准备阶段结束时，即所有元素都添加到 `RenderState` 之后。 请见 `GuiRenderer#prepare`。

本文介绍了渲染的基础知识，虽然仍然具有一定的相关性，但大多数情况下，为了获得更好的性能和兼容性，我们会采用更高级别的抽象。 欲知更多信息，请参阅[在世界中渲染](./world)。

:::

本文会介绍使用新系统渲染的一些基础，并解释一些关键术语和概念。

尽管 Minecraft 的许多渲染都通过 `GuiGraphics` 中的各种方法抽象出来，且你很可能并不需要接触这里提到的任何内容，但是了解渲染的基础实现依然很重要。

## 镶嵌器 `Tesselator` {#the-tesselator}

镶嵌器 `Tesselator` 是 Minecraft 中用于渲染东西的主类。 它是一个单例，这意味着游戏中只有它的一个实例。 可以使用 `Tesselator.getInstance()` 获取这个实例。

## 缓冲构建器 `BufferBuilder`{#the-bufferbuilder}

缓冲构建器 `BufferBuilder` 是用来将渲染数据格式化并上传到 OpenGL 的类。 用于创建缓冲，随后也会将这个缓冲上传到 OpenGL 用于绘制。

镶嵌器 `Tesselator` 负责创建一个缓冲构建器 `BufferBuilder`，用于将渲染数据格式化并上传到 OpenGL。

### 初始化 `BufferBuilder`{#initializing-the-bufferbuilder}

必须先初始化 `BufferBuilder`，才能往里面写入任何东西。 方法就是使用 `Tesselator#begin(...)` 方法，接收一个 `VertexFormat` 和绘制模式，并返回 `BufferBuilder`。

#### 顶点格式{#vertex-formats}

顶点格式 `VertexFormat` 定义了我们在我们的数据缓冲中包含的元素，并规定了这些元素将如何被转发到 OpenGL。

以下默认的 `VertexFormat` 元素可在 `DefaultVertexFormat` 中找到：

| 元素                            | 格式                                                                                      |
| ----------------------------- | --------------------------------------------------------------------------------------- |
| `EMPTY`                       | `{ }`                                                                                   |
| `BLOCK`                       | `{ position, color, texture uv, texture light (2 shorts), texture normal (3 sbytes) }`  |
| `NEW_ENTITY`                  | `{ position, color, texture uv, overlay (2 shorts), texture light, normal (3 sbytes) }` |
| `PARTICLE`                    | `{ position, texture uv, color, texture light }`                                        |
| `POSITION`                    | `{ position }`                                                                          |
| `POSITION_COLOR`              | `{ position, color }`                                                                   |
| `POSITION_COLOR_NORMAL`       | `{ position, color, normal }`                                                           |
| `POSITION_COLOR_LIGHTMAP`     | `{ position, color, light }`                                                            |
| `POSITION_TEX`                | `{ position, uv }`                                                                      |
| `POSITION_TEX_COLOR`          | `{ position, uv, color }`                                                               |
| `POSITION_COLOR_TEX_LIGHTMAP` | `{ position, color, uv, light }`                                                        |
| `POSITION_TEX_LIGHTMAP_COLOR` | `{ position, uv, light, color }`                                                        |
| `POSITION_TEX_COLOR_NORMAL`   | `{ position, uv, color, normal }`                                                       |

#### 绘制模式{#draw-modes}

绘制模式定义了如何绘制数据。 `VertexFormat.Mode` 中提供了以下绘制模式：

| 绘制模式               | 描述                                               |
| ------------------ | ------------------------------------------------ |
| `LINES`            | 每个元素由 2 个顶点构成，代表一条直线。                            |
| `LINE_STRIP`       | 第一个元素需要 2 个顶点。 附加的元素只需要 1 个新的顶点就能绘制，创建出一条连续的线。   |
| `DEBUG_LINES`      | 与 `Mode.LINES` 类似，但线在屏幕上总是保持 1 像素宽。              |
| `DEBUG_LINE_STRIP` | 与 `Mode.LINE_STRIP` 相同，但线总是保持 1 像素宽。             |
| `TRIANGLES`        | 每个元素都由 3 个顶点组成，形成一个三角形。                          |
| `TRIANGLE_STRIP`   | 前 3 个顶点形成第一个三角形。 每个新增的顶点与前两个顶点形成一个新的三角形。         |
| `TRIANGLE_FAN`     | 前 3 个顶点形成第一个三角形。 每个新增的顶点与第一个顶点和最后的一个顶点形成一个新的三角形。 |
| `QUADS`            | 每个元素由 4 个顶点构成，形成一个四边形。                           |

### 向 `BufferBuilder` 写入{#writing-to-the-bufferbuilder}

`BufferBuilder` 初始化完成后，您就可以向它写入数据。

`BufferBuilder` 允许我们一个顶点一个顶点地构造我们的缓冲。 要添加顶点，我们使用 `buffer.addVertex(Matrix4f, float, float, float)` 方法。 `Matrix4f` 参数是变换矩阵，稍后会详细讨论。 3 个 `float` 参数代表顶点坐标的 (x, y, z)。

这个方法返回一个顶点构建器（vertex builder），可以用来为这个顶点指定附加信息。 附加这些信息时，按照我们先前定义的顶点格式 `VertexFormat` 至关重要。 如果不这么做，OpenGL 可能无法正确解释我们的数据。 完成构建顶点后，只需要给缓冲继续添加更多顶点和数据，直到完成。

“剔除”的概念也值得理解一下。 剔除是从 3D 形状中移除那些在观察者视角不可见的面的过程。 如果一个面的顶点指定顺序错误，这个面可能会因为剔除而无法正确渲染。

#### 什么是变换矩阵？ {#what-is-a-transformation-matrix}

一个变换矩阵是一个 4×4 的矩阵，用于变换一个向量。 在 Minecraft 中，变换矩阵只是将我们传递给 `addVertex` 调用的坐标进行变换。 这些变换可以放缩、平移和旋转我们的模型。

有时称为位置矩阵，或模型矩阵。

通常是通过 `Matrix3x2fStack` 类获得的，而该类可以通过调用 `GuiGraphics#pose()` 方法从 `GuiGraphics` 对象获得。

#### 渲染三角条纹{#rendering-a-triangle-strip}

用实际案例来解释如何向 `BufferBuilder` 写入会更轻松一些。 假设我们要使用 `VertexFormat.Mode.TRIANGLE_STRIP` 绘制模式和 `POSITION_COLOR` 顶点格式来渲染某些东西。

我们将在平视显示器（HUD）上的以下几个点按顺序绘制顶点：

```text:no-line-numbers
(20, 20)
(5, 40)
(35, 40)
(20, 60)
```

这应当给出一个漂亮的菱形——因为我们在使用绘制模式 `TRIANGLE_STRIP`，渲染器将执行以下几步：

![四个步骤展示顶点在屏幕上的放置是如何形成两个三角形的](/assets/develop/rendering/concepts-practical-example-draw-process.png)

由于我们在本例中在平视显示器（HUD）上绘制，因此我们将使用 `HudElementRegistry`：

:::warning 重要更新

从 1.21.8 开始，传入 HUD 渲染的矩阵栈由 `PoseStack` 改变为 `Matrix3x2fStack`。 大多数方法都略有些不同，不再接收 `z` 参数，但概念还是相同的。

此外，下面的代码与上面的解释并不完全一致：无需手动写入 `BufferBuilder`，因为 `GuiGraphics` 方法会在准备过程中自动写入 HUD 的 `BufferBuilder`。

请阅读上面的重要更新以了解更多信息。

:::

**元素注册：**

@[code lang=java transcludeWith=:::registration](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

**`hudLayer()` 的实现：**

@[code lang=java transcludeWith=:::hudLayer](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

在平视显示器（HUD）上绘制的结果如下：

![最终结果](/assets/develop/rendering/concepts-practical-example-final-result.png)

::: tip

尝试给顶点随机的颜色和位置，看看会发生什么！ 您也可以尝试使用不同的绘制模式和顶点格式。

:::

## `PoseStack` {#the-posestack}

::: warning

本段的代码和文本在讨论不同的东西！

代码展示了 `Matrix3x2fStack`，自 1.21.8 起用于 HUD 渲染，而下文描述了 `PoseStack`，其方法略有不同。

请阅读上面的重要更新以了解更多信息。

:::

在学习完如何向 `BufferBuilder` 写入后，您可能会好奇如何变换你的模型——或者甚至让它动起来。 这时就需要引入 `PoseStack` 类。

`PoseStack` 类有以下方法：

- `pushPose()` - 向栈压入一个新的矩阵。
- `popPose()` - 从栈中弹出最顶部的矩阵。
- `last()` - 返回栈顶的矩阵。
- `translate(x, y, z)` - 平移栈顶的矩阵。
- `translate(vec3)`
- `scale(x, y, z)` - 放缩栈顶的矩阵。

还可以使用四元数对栈顶的矩阵做乘法，这些内容会在下一节讲到。

从我们上面的案例出发，我们可以用 `PoseStack` 和 `tickDelta`（从上一帧到现在经过的时间）让我们的菱形放大和缩小。 稍后会在[渲染 HUD](./hud#render-tick-counter) 页面中讲清楚这一点。

::: warning

必须先压入一个矩阵栈，然后再在完成后将栈顶的矩阵弹出， 否则，就会破坏矩阵栈，导致渲染问题。

在获取变换矩阵前，请确保向矩阵栈压入一个新的矩阵！

:::

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

![一段展示菱形放大和缩小的视频](/assets/develop/rendering/concepts-matrix-stack.webp)

## 四元数 `Quaternion`（旋转物体）{#quaternions-rotating-things}

::: warning

本段的代码和文本在讨论不同的东西！

代码展示了 HUD 上的渲染，而下文描述了三维世界空间的渲染。

请阅读上面的重要更新以了解更多信息。

:::

四元数是三维空间中的旋转的一种表示方法， 它们用于通过 `rotateAround(quaternionfc, x, y, z)` 方法旋转 `PoseStack` 上的顶层矩阵。

你几乎不可能直接使用四元数类，因为 Minecraft 在其 `Axis` 工具类中提供了各种预构建的四元数实例。

不妨让我们尝试绕 z 轴旋转我们的方形。 我们可以通过使用 `PoseStack` 和 `rotateAround(quaternionfc, x, y, z)` 方法来实现这一点。

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

这会产生如下结果：

![一段展示菱形绕 z 轴旋转的视频](/assets/develop/rendering/concepts-quaternions.webp)
