---
title: 在 HUD 中渲染
description: 了解如何使用 Fabric Hud API 渲染到 HUD。
authors:
  - IMB11
  - kevinthegreat1
---

在[基本渲染概念](./basic-concepts)页面和[使用绘制上下文](./draw-context)中，我们已经简要介绍了如何将内容渲染到 HUD，因此本页我们将重点介绍 Hud API 以及 `DeltaTracker` 参数。

## `HudRenderCallback` {#hudrendercallback}

::: warning

以前，Fabric 提供了 `HudRenderCallback` 来渲染到 HUD。 由于 HUD 渲染的变更，这一事件受到诸多限制，并且自从 Fabric API 0.116 开始被弃用。 因此强烈不推荐使用。

:::

## `HudElementRegistry` {#hudelementregistry}

Fabric 提供 Hud API 以在 HUD 上渲染和布局元素。

首先，我们需要向 `HudElementRegistry` 注册一个监听器，用于注册你的元素。 每个元素都是一个 `HudElement`。 `HudElement` 实例通常是一个 lambda 表达式，它接受一个 `GuiGraphics` 和一个 `DeltaTracker` 实例作为参数。 有关如何使用该 API 的更多详细信息，请参阅 `HudElementRegistry` 及其相关的 Javadoc。

绘制上下文可用于访问游戏提供的各种渲染工具，以及原始矩阵堆栈。 你应该查看[绘制上下文](./draw-context)页面，了解更多关于绘制上下文的信息。

### Delta Tracker {#delta-tracker}

`DeltaTracker` 类允许你获取当前的 `gameTimeDeltaPartialTick` 值。 `gameTimeDeltaPartialTick` 是上一个游戏刻和下一个游戏刻之间的“过程”。

例如，如果我们假设 200 FPS 场景，游戏大约每 10 帧运行一次新的刻。 每一帧，`gameTimeDeltaPartialTick` 代表上一刻与下一刻之间的距离。 超过 11 帧时，你可能会看到：

|   帧  | `gameTimeDeltaPartialTick` |
| :--: | -------------------------- |
|  `1` | `1`：新的刻                    |
|  `2` | `1/10 = 0.1`               |
|  `3` | `2/10 = 0.2`               |
|  `4` | `3/10 = 0.3`               |
|  `5` | `4/10 = 0.4`               |
|  `6` | `5/10 = 0.5`               |
|  `7` | `6/10 = 0.6`               |
|  `8` | `7/10 = 0.7`               |
|  `9` | `8/10 = 0.8`               |
| `10` | `9/10 = 0.9`               |
| `11` | `1`：新的刻                    |

可以调用 `deltaTracker.getGameTimeDeltaPartialTick(false)` 以检索 `gameTimeDeltaPartialTick`，其中布尔值参数是 `ignoreFreeze`，这实际上只是允许忽略玩家使用 `/tick freeze` 命令的情况。

实际上，只有当动画依赖于 Minecraft 刻时，才应该使用 `gameTimeDeltaPartialTick`。 对于基于时间的动画，请使用 `Util.getMillis()`，它可以测量现实世界的时间。

在本例中，我们将使用 `Util.getMillis()` 线性插入要渲染到 HUD 的正方形的颜色。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![随着时间的推移对颜色进行插值](/assets/develop/rendering/hud-rendering-deltatick.webp)

试试使用 `gameTimeDeltaPartialTick` 并查看运行 `/tick freeze` 命令时动画会发生什么情况。 假设你已向`DeltaTracker#getGameTimeDeltaPartialTick`传递了`false`作为参数，则当`gameTimeDeltaPartialTick`为常数时，你应该会看到动画定住不动。
