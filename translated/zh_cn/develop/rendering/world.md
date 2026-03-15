---
title: 在世界中渲染
description: 当原版管线不能满足需求时，创建并使用自定义渲染管线。
authors:
  - AzureAaron
  - kevinthegreat1
---

<!---->

:::info 前置条件

确保你已阅读过[渲染概念](./basic-concepts)。 本页基于这些概念并讨论如何渲染世界上的对象。

本页探讨了一些更现代的渲染概念。 你将进一步了解渲染的两个阶段：“提取”（或称“准备”）与“绘制”（或称“渲染”）。 在本指南中，我们将“提取/准备”阶段称为“提取”阶段，将“绘制/渲染”阶段称为“绘制”阶段。

:::

要在世界中渲染自定义对象，你有两种选择。 你可以注入现有的原版渲染并添加代码，但这会限制你只能使用现有的原版渲染管线。 如果现有的原版渲染管线无法满足你的需求，则需要自定义渲染管线。

在讨论自定义渲染管线之前，我们先来了解一下原版渲染。

## 提取和绘制阶段 {#the-extraction-and-drawing-phases}

正如[渲染概念](./basic-concepts)中所提到的，Minecraft 的最新更新致力于将渲染分为两个阶段：“提取”和“绘制”。

渲染所需的所有数据都在“提取”阶段收集。 例如，这包括写入缓冲构建器。 通过 `buffer.addVertex` 将顶点写入缓冲构建器是“提取”阶段的一部分。 请注意，许多方法即使以 `draw` 或 `render` 为前缀，也应该在“提取”阶段调用。 你应该在此阶段添加所有要渲染的元素。

“提取”阶段完成后，“绘制”阶段开始，并构建缓冲构建器。 在这个阶段，缓冲构建器被绘制到屏幕上。 这种“提取”和“绘制”分离的最终目标是允许并行绘制上一帧和提取下一帧，从而提高性能。

现在，考虑到这两个阶段，让我们看看如何创建自定义渲染管线。

## 自定义渲染管线 {#custom-render-pipelines}

假设我们要渲染需要穿过墙壁显示的路径点。 最接近该效果的原版渲染管线是 `RenderPipelines#DEBUG_FILLED_BOX`，但它无法穿过墙壁进行渲染，因此我们需要一个自定义渲染管线。

### 定义自定义渲染管线 {#defining-a-custom-render-pipeline}

我们在一个类中定义自定义渲染管线：

@[code lang=java transcludeWith=:::custom-pipelines:define-pipeline](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

### 提取阶段 {#extraction-phase}

我们首先实现“提取”阶段。 我们可以在“提取”阶段调用这个方法来添加要渲染的路径点。

@[code lang=java transcludeWith=:::custom-pipelines:extraction-phase](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

请注意，`BufferAllocator` 构造函数中使用的大小取决于你使用的渲染管线。 在我们的例子中，它是 `RenderType.SMALL_BUFFER_SIZE`。

如果要渲染多个路径点，则多次调用此方法。 确保在“提取”阶段（即“绘制”阶段开始之前，缓冲区构建器在此时构建）执行此操作。

### 渲染状态 {#render-states}

请注意，在上面的代码中，我们将 `BufferBuilder` 保存在一个字段中。 这是因为我们在“绘制”阶段需要它。 在这种情况下，`BufferBuilder` 就是我们的“渲染状态”或“提取的数据”。 如果你在“绘制”阶段需要额外的数据，则应该创建一个自定义渲染状态类来保存 `BufferedBuilder` 以及你需要的其他渲染数据。

### 绘制阶段 {#drawing-phase}

现在我们将实现“绘制”阶段。 在“提取”阶段，所有需要渲染的路径点都添加到 `BufferBuilder` 后，应该调用这个阶段。

@[code lang=java transcludeWith=:::custom-pipelines:drawing-phase](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

### 清理 {#cleaning-up}

最后，我们需要在游戏渲染器关闭时清理资源。 `GameRenderer#close` 应该调用这个方法，为此，你目前需要将 mixin 注入到 `GameRenderer#close` 中。

@[code lang=java transcludeWith=:::custom-pipelines:clean-up](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

@[code lang=java](@/reference/latest/src/client/java/com/example/docs/mixin/client/GameRendererMixin.java)

### 最终代码 {#final-code}

结合以上所有步骤，我们得到了一个简单的类，渲染一个穿过墙壁的路径点，位于 `(0, 100, 0)`。

@[code lang=java](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

别忘了 `GameRendererMixin`！ 结果如下：

![穿过墙壁的路径点渲染](/assets/develop/rendering/world-rendering-custom-render-pipeline-waypoint.png)
