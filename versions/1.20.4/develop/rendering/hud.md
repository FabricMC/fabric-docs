---
title: Rendering in the Hud
description: Learn how to use the HudRenderCallback event to render to the hud.
authors:
  - IMB11

search: false
---

# Rendering in the Hud {#rendering-in-the-hud}

We already briefly touched on rendering things to the hud in the [Basic Rendering Concepts](./basic-concepts) page and [Using The Drawing Context](./draw-context), so on this page we'll stick to the `HudRenderCallback` event and the `deltaTick` parameter.

## HudRenderCallback {#hudrendercallback}

The `HudRenderCallback` event - provided by Fabric API - is called every frame, and is used to render things to the HUD.

To register to this event, you can simply call `HudRenderCallback.EVENT.register` and pass in a lambda that takes a `DrawContext` and a `float` (deltaTick) as parameters.

The draw context can be used to access the various rendering utilities provided by the game, and access the raw matrix stack.

You should check out the [Draw Context](./draw-context) page to learn more about the draw context.

### DeltaTick {#deltatick}

The `deltaTick` parameter is the time since the last frame, in seconds. This can be used to make animations and other time-based effects.

For example, let's say you want to lerp a color over time. You can use the `deltaTick` parameter to do this:

@[code lang=java transcludeWith=:::1](@/reference/1.20.4/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![Lerping a color over time](/assets/develop/rendering/hud-rendering-deltatick.webp)
