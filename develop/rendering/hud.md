---
title: Rendering in the Hud
description: Learn how to use the HudRenderCallback event to render to the hud.
authors:
  - IMB11
---

# Rendering in the Hud

We already briefly touched on rendering things to the hud in the [Basic Rendering Concepts](./basic-concepts.md) page and [Using The Drawing Context](./draw-context.md), so on this page we'll stick to the `HudRenderCallback` event and the `tickDelta` parameter.

## HudRenderCallback

The `HudRenderCallback` event - provided by Fabric API - is called every frame, and is used to render things to the HUD.

To register to this event, you can simply call `HudRenderCallback.EVENT.register` and pass in a lambda that takes a `DrawContext` and a `float` (tickDelta) as parameters.

The draw context can be used to access the various rendering utilities provided by the game, and access the raw matrix stack.

You should check out the [Draw Context](./draw-context.md) page to learn more about the draw context.

### tickDelta

The `tickDelta` parameter is the decimal of the time since the last frame, in seconds (it is always a value between 0 and 1). This can be used to make animations and other time-based effects.

#### Example: Lerping a Color Over Time

Let's say you want to lerp a color over time. You can use the `tickDelta` parameter to do this.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![Lerping a color over time](/assets/develop/rendering/hud-rendering-deltatick.webp)
