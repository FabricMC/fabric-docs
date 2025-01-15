---
title: 在 Hud 中渲染
description: 学习如何使用 HudRenderCallback 事件来渲染Hud。
authors:
  - IMB11
---

# 渲染 Hud{#rendering-in-the-hud}

在 [基本渲染概念](./basic-concepts) 页面和[使用绘制上下文](./draw-context) 中，我们已经简要介绍了如何将内容渲染到 Hud，因此在本页中，我们将重点介绍 `HudRenderCallback` 事件和 `deltaTick` 参数。

## HudRenderCallback{#hudrendercallback}

由 Fabric API 提供的 `HudRenderCallback` 事件每帧都会被调用，用于向 HUD 渲染内容。

要注册此事件，只需调用 `HudRenderCallback.EVENT.register` 并传入一个以 `DrawContext` 和一个 `float` (deltaTick) 为参数的 lambda 表达式即可。

绘制上下文可用于访问游戏提供的各种渲染工具，并访问原始矩阵堆栈。

要了解有关绘制上下文的更多信息，应该查看[使用绘制上下文](./draw-context)页面。

### DeltaTick{#deltatick}

`deltaTick` 是指距上一帧的时间，单位为秒。 这可以用来制作动画和其他基于时间的效果。

例如，假设要让颜色随时间变化。 可以使用 `deltaTickManager` 获得 deltaTick，并随时间存储以变化颜色。

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![](/assets/develop/rendering/hud-rendering-deltatick.webp)
