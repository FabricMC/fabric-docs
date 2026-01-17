---
title: 世界中的渲染
description: 当原版渲染管线无法满足需求时，创建并使用自定义渲染管线。
authors:
  - PEQB1145
---

::: info 前提知识
请确保你已阅读[渲染基础概念](./basic-concepts)。本页面将在此基础上讨论如何在世界中渲染对象。

本页面将探讨一些更现代的渲染概念。你将了解渲染的两个独立阶段："提取"（或称"准备"）阶段和"绘制"（或称"渲染"）阶段。在本指南中，我们将"提取/准备"阶段称为"提取"阶段，而将"绘制/渲染"阶段称为"绘制"阶段。
:::

要在世界中渲染自定义对象，你有两种选择。你可以嵌入到现有的原版渲染中并添加你的代码，但这会限制你只能使用现有的原版渲染管线。如果现有的原版渲染管线无法满足你的需求，你就需要一个自定义渲染管线。

在我们深入自定义渲染管线之前，先来看看原版渲染。

## 提取阶段与绘制阶段 {#the-extraction-and-drawing-phases}

如[渲染基础概念](./basic-concepts)所述，最近的Minecraft更新正在将渲染分为两个阶段："提取"和"绘制"。

渲染所需的所有数据均在"提取"阶段收集。例如，这包括向缓冲构建器写入数据。调用渲染方法，如 `ShapeRenderer.addChainedFilledBoxVertices`，会将顶点写入缓冲构建器，这属于"提取"阶段。请注意，尽管许多方法以 `draw` 或 `render` 为前缀，但它们应在"提取"阶段调用。所有你希望渲染的元素都应在此阶段添加。

当"提取"阶段完成后，"绘制"阶段开始，缓冲构建器被构建。在此阶段，缓冲构建器被绘制到屏幕上。实现"提取"和"绘制"分离的最终目标，是为了能在提取下一帧的同时并行绘制上一帧，从而提高性能。

现在，结合这两个阶段，让我们看看如何创建自定义渲染管线。

## 自定义渲染管线 {#custom-render-pipelines}

假设我们要渲染路标，且路标应能穿透墙壁显示。原版渲染管线中最接近的可能是 `RenderPipelines#DEBUG_FILLED_BOX`，但它无法穿透墙壁渲染，因此我们需要一个自定义渲染管线。

### 定义自定义渲染管线 {#defining-a-custom-render-pipeline}

我们在一个类中定义一个自定义渲染管线：

@[code lang=java transcludeWith=:::custom-pipelines:define-pipeline](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

### 提取阶段 {#extraction-phase}

我们首先实现"提取"阶段。我们可以在"提取"阶段调用此方法，来添加一个待渲染的路标。

@[code lang=java transcludeWith=:::custom-pipelines:extraction-phase](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

请注意，`BufferAllocator` 构造函数中使用的尺寸取决于你所使用的渲染管线。在我们的例子中，它是 `RenderType.SMALL_BUFFER_SIZE`。

如果你想渲染多个路标，请多次调用此方法。确保你在"提取"阶段进行此操作，即在"绘制"阶段开始之前，因为那时缓冲构建器会被构建。

### 渲染状态 {#render-states}

请注意，在上述代码中，我们将 `BufferBuilder` 保存在一个字段中。这是因为它需要在"绘制"阶段使用。在这个例子中，`BufferBuilder` 就是我们的"渲染状态"或"提取的数据"。如果你在"绘制"阶段需要额外的数据，你应该创建一个自定义的渲染状态类来保存 `BufferedBuilder` 以及你需要的任何其他渲染数据。

### 绘制阶段 {#drawing-phase}

现在我们将实现"绘制"阶段。此方法应在所有你想要渲染的路标在"提取"阶段被添加到 `BufferBuilder` 之后调用。

@[code lang=java transcludeWith=:::custom-pipelines:drawing-phase](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

### 清理资源 {#cleaning-up}

最后，当游戏渲染器关闭时，我们需要清理资源。`GameRenderer#close` 应调用此方法，为此你目前需要通过Mixin注入到 `GameRenderer#close` 中。

@[code lang=java transcludeWith=:::custom-pipelines:clean-up](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)
@[code lang=java](@/reference/latest/src/client/java/com/example/docs/mixin/client/GameRendererMixin.java)

### 完整代码 {#final-code}

结合以上所有步骤，我们得到了一个在坐标 `(0, 100, 0)` 处穿透墙壁渲染路标的简单类。

@[code lang=java](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

别忘了还要有 `GameRendererMixin`！以下是渲染效果：

![穿透墙壁渲染的路标](/assets/develop/rendering/world-rendering-custom-render-pipeline-waypoint.png)
