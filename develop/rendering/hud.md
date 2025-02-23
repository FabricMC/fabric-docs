---
title: Rendering in the Hud
description: Learn how to use the HudRenderCallback event to render to the hud.
authors:
  - IMB11
---

We already briefly touched on rendering things to the hud in the [Basic Rendering Concepts](./basic-concepts) page and [Using The Drawing Context](./draw-context), so on this page we'll stick to the `HudRenderCallback` event and the `tickDelta` parameter.

## HudRenderCallback {#hudrendercallback}

The `HudRenderCallback` event - provided by Fabric API - is called every frame, and is used to render things to the HUD.

To register to this event, you can simply call `HudRenderCallback.EVENT.register` and pass in a lambda that takes a `DrawContext` and a `RenderTickCounter` instance as parameters.

The draw context can be used to access the various rendering utilities provided by the game, and access the raw matrix stack.

You should check out the [Draw Context](./draw-context) page to learn more about the draw context.

### Render Tick Counter {#render-tick-counter}

The `RenderTickCounter` class allows you to retrieve the current `tickDelta` value.

`tickDelta` is the "progress" between the last game tick and the next game tick.

For example, if we assume a 200 FPS scenario, the game runs a new tick roughly every 10 frames. Each frame, `tickDelta` represents how far we are between the last tick and the next. Over 10 frames, you might see:

| Frame | tickDelta     |
|:-----:|---------------|
|  `1`  | `1`: New tick |
|  `2`  | `1/9 ~ 0.11`  |
|  `3`  | `2/9 ~ 0.22`  |
|  `4`  | `3/9 ~ 0.33`  |
|  `5`  | `4/9 ~ 0.44`  |
|  `6`  | `5/9 ~ 0.55`  |
|  `7`  | `6/9 ~ 0.66`  |
|  `8`  | `7/9 ~ 0.77`  |
|  `9`  | `8/9 ~ 0.88`  |
| `10`  | `1`: New tick |

Practically, you should only use `tickDelta` when your animations depend on Minecraft's ticks. For time-based animations, use `Util.getMeasuringTimeMs()`, which measures real-world time.

You can retrieve `tickDelta` using the `renderTickCounter.getTickDelta(false);` function, where the boolean parameter is `ignoreFreeze`, which essentially just allows you to ignore whenever players use the `/tick freeze` command.

In this example, we'll use `Util.getMeasuringTimeMs()` to linearly interpolate the color of a square that is being rendered to the HUD.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![Lerping a color over time](/assets/develop/rendering/hud-rendering-deltatick.webp)

Why don't you try use `tickDelta` and see what happens to the animation when you run the `/tick freeze` command? You should see the animation freeze in place as `tickDelta` becomes constant (assuming you have passed `false` as the parameter to `RenderTickCounter#getTickDelta`)
