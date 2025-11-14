---
title: 在 Hud 中渲染
description: 学习如何使用 HudRenderCallback 事件来渲染Hud。
authors:
  - IMB11
  - kevinthegreat1
---

在[基本渲染概念](./basic-concepts)页面和[使用绘制上下文](./draw-context)中，我们已经简要介绍了如何将内容渲染到 HUD，因此本页我们将重点介绍 Hud API 以及 `RenderTickCounter` 参数。

## HudRenderCallback{#hudrendercallback}

:::warning
以前，Fabric 提供了 `HudRenderCallback` 以渲染 HUD。 由于 HUD 渲染的改变，这一事件受到诸多限制，并且自从 Fabric API 0.116 开始被弃用。 因此强烈不推荐使用。
:::

## `HudLayerRegistrationCallback` {#hudlayerregistrationcallback}

Fabric 提供 Hud API 以在 HUD 上渲染和布局元素。

首先，我们需要为 `HudLayerRegistrationCallback` 注册一个监听器，注册你的图层。 每个图层是一个 `IdentifiedLayer`，是个原版的 `LayeredDrawer.Layer` 并附上了一个 `Identifier`。 `LayeredDrawer.Layer` 实例通常是个 lambda，接收一个 `DrawContext` 和 `RenderTickCounter` 实例作为参数。 关于如何使用此 API 的更多信息，请看 `HudLayerRegistrationCallback` 及相关的 Javadoc。

绘制上下文可用于访问游戏提供的各种渲染工具，并访问原始矩阵堆栈。 要了解有关绘制上下文的更多信息，应该查看[使用绘制上下文](./draw-context)页面。

### 渲染刻计数器 {#render-tick-counter}

`RenderTickCounter` 类允许检索当前的 `tickDelta` 值。 `tickDelta` 是上一个游戏刻和下一个游戏刻之间的“过程”。

例如，我们假设 200 FPS 的场景，游戏大约每 10 帧运行一次新的刻。 每一帧，`tickDelta` 代表上一刻与下一刻之间的距离。 超过 11 帧时，你可能会看到：

|   帧  | tickDelta    |
| :--: | ------------ |
|  `1` | `1`：新的刻      |
|  `2` | `1/10 = 0.1` |
|  `3` | `2/9 ~ 0.22` |
|  `4` | `3/9 ~ 0.33` |
|  `5` | `4/9 ~ 0.44` |
|  `6` | `5/9 ~ 0.55` |
|  `7` | `6/9 ~ 0.66` |
|  `8` | `7/9 ~ 0.77` |
|  `9` | `8/9 ~ 0.88` |
| `10` | `9/10 = 0.9` |
| `11` | `1`：新的刻      |

实际上，只有当动画依赖于 Minecraft 刻时，才应该使用 `tickDelta`。 针对基于时间的动画，请使用 `Util.getMeasuringTimeMs()`，它可以测量现实世界的时间。

可以调用 `renderTickCounter.getTickDelta(false)` 以检索 `tickDelta`，其中布尔值参数是 `ignoreFreeze`，这实际上只是允许忽略玩家使用 `/tick freeze` 命令的情况。

在此示例中，我们将使用 `Util.getMeasuringTimeMs()` 线性插入要渲染到 HUD 的正方形的颜色。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![](/assets/develop/rendering/hud-rendering-deltatick.webp)

试试使用 `tickDelta` 并查看运行 `/tick freeze` 命令时动画会发生什么情况。 你会看到动画在 `tickDelta` 变为常量时冻结（假设你已将 `false` 作为参数传递给 `RenderTickCounter#getTickDelta`）
