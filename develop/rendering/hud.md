---
title: Rendering in the Hud
description: Learn how to use the HudRenderCallback event to render to the hud.
authors:
  - IMB11
---

# Rendering in the Hud {#rendering-in-the-hud}

We already briefly touched on rendering things to the hud in the [Basic Rendering Concepts](./basic-concepts) page and [Using The Drawing Context](./draw-context), so on this page we'll stick to the `HudRenderCallback` event and the `tickDelta` parameter.

## HudRenderCallback {#hudrendercallback}

The `HudRenderCallback` event - provided by Fabric API - is called every frame, and is used to render things to the HUD.

To register to this event, you can simply call `HudRenderCallback.EVENT.register` and pass in a lambda that takes a `DrawContext` and a `float` (tickDelta) as parameters.

The draw context can be used to access the various rendering utilities provided by the game, and access the raw matrix stack.

You should check out the [Draw Context](./draw-context) page to learn more about the draw context.

### Tick Delta {#tickdelta}

`tickDelta` is the "progress" between the last game tick and the next game tick.

For example, if we assume a 60 FPS scenario, the game runs a new tick roughly every three frames. Each frame, `tickDelta` represents how far we are between the last tick and the next. Over 10 frames, you might see:

| Frame | tickDelta                                    |
|-------|----------------------------------------------|
| 1     | `1.0` (new tick)                             |
| 2     | `0.11 (1÷9)` - The next tick is in 9 frames. |
| 3     | `0.22 (2÷9)`                                 |
| 4     | `0.33 (3÷9)`                                 |
| 5     | `0.44 (4÷9)`                                 |
| 6     | `0.55 (5÷9)`                                 |
| 7     | `0.66 (6÷9)`                                 |
| 8     | `0.77 (7÷9)`                                 |
| 9     | `0.88 (8÷9)`                                 |
| 10    | `1.0 (9÷9)` (new tick)                       |

Practically, let's say you want to lerp a color over time. You can use the `tickDeltaManager` to get the tickDelta, and store it over time to lerp the color:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![Lerping a color over time](/assets/develop/rendering/hud-rendering-deltatick.webp)
