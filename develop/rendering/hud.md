---
title: Rendering In The Hud
description: Learn how to use the HudRenderCallback event to render to the hud.
authors:
  - IMB11
---

# Rendering In The Hud

We already briefly touched on rendering things to the hud in the [Basic Rendering Concepts](./basic-concepts.md) page and [Using The Drawing Context](./draw-context.md), so on this page we'll stick to the `HudRenderCallback` event and the `deltaTick` parameter.

## HudRenderCallback

The `HudRenderCallback` event - provided by Fabric API - is called every frame, and is used to render things to the HUD.

To register to this event, you can simply call `HudRenderCallback.EVENT.register` and pass in a lambda that takes a `DrawContext` and a `float` (deltaTick) as parameters.

The draw context can be used to access the various rendering utilities provided by the game, and access the raw matrix stack.

You should checkout the [Draw Context](./draw-context.md) page to learn more about the draw context.

### DeltaTick

The `deltaTick` parameter is the time since the last frame, in seconds. This can be used to make animations and other time-based effects.

#### Example: Lerping A Color Over Time

Let's say you want to lerp a color over time. You can use the `deltaTick` parameter to do this.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![](/assets/develop/rendering/hud-rendering-deltatick.webp)