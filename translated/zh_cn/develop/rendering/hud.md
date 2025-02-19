---
title: 在 Hud 中渲染
description: 学习如何使用 HudRenderCallback 事件来渲染Hud。
authors:
  - IMB11
---

在 [基本渲染概念](./basic-concepts) 页面和[使用绘制上下文](./draw-context) 中，我们已经简要介绍了如何将内容渲染到 Hud，因此在本页中，我们将重点介绍 `HudRenderCallback` 事件和 `tickDelta` 参数。

## HudRenderCallback{#hudrendercallback}

由 Fabric API 提供的 `HudRenderCallback` 事件每帧都会被调用，用于向 HUD 渲染内容。

要注册此事件，只需调用 `HudRenderCallback.EVENT.register` 并传入一个以 `DrawContext` 和 `RenderTickCounter` 实例作为参数的 lambda。

绘制上下文可用于访问游戏提供的各种渲染工具，并访问原始矩阵堆栈。

要了解有关绘制上下文的更多信息，应该查看[使用绘制上下文](./draw-context)页面。

### 渲染刻计数器{#render-tick-counter}

`RenderTickCounter` 类允许检索当前的 `tickDelta` 值。

`tickDelta` 是上一个游戏刻和下一个游戏刻之间的“过程”。

例如，我们假设 200 FPS 的场景，游戏大约每 10 帧运行一次新的刻。 每一帧，`tickDelta` 代表上一刻与下一刻之间的距离。 超过 10 帧时，你可能会看到：

|   帧  | tickDelta    |
| :--: | ------------ |
|  `1` | `1`：新的刻      |
|  `2` | `1/9 ~ 0.11` |
|  `3` | `2/9 ~ 0.22` |
|  `4` | `3/9 ~ 0.33` |
|  `5` | `4/9 ~ 0.44` |
|  `6` | `5/9 ~ 0.55` |
|  `7` | `6/9 ~ 0.66` |
|  `8` | `7/9 ~ 0.77` |
|  `9` | `8/9 ~ 0.88` |
| `10` | `1`：新的刻      |

实际上，只有当动画依赖于 Minecraft 刻时，才应该使用 `tickDelta`。 针对基于时间的动画，请使用 `Util.getMeasuringTimeMs()`，它可以测量现实世界的时间。

您可以使用 `renderTickCounter.getTickDelta(false);` 函数检索 `tickDelta`，其中布尔参数是 `ignoreFreeze`，这实际上只是允许您忽略玩家使用 `/tick freeze` 命令的情况。

在此示例中，我们将使用 `Util.getMeasuringTimeMs()` 线性插入要渲染到 HUD 的正方形的颜色。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![](/assets/develop/rendering/hud-rendering-deltatick.webp)

试试使用 `tickDelta` 并查看运行 `/tick freeze` 命令时动画会发生什么情况。 你会看到动画在 `tickDelta` 变为常量时冻结（假设你已将 `false` 作为参数传递给 `RenderTickCounter#getTickDelta`）
