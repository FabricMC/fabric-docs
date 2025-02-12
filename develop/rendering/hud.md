---
title: Rendering in the Hud
description: Learn how to use the Fabric Hud API to render to the hud.
authors:
  - IMB11
  - kevinthegreat1
---

# Rendering in the Hud {#rendering-in-the-hud}

We already briefly touched on rendering things to the hud in the [Basic Rendering Concepts](./basic-concepts) page and [Using The Drawing Context](./draw-context), so on this page we'll stick to the Hud API and the `RenderTickCounter` parameter.

## `HudRenderCallback` {#hudrendercallback}

::: warning
Previously, Fabric provided `HudRenderCallback` to render to the hud. Due to changes to hud rendering, this event became extremely limited and is deprecated since Fabric API 0.116. Usage is strongly discouraged. Deprecated events may be removed in the future.
:::

## `HudLayerRegistrationCallback` {#hud-layer-registration-callback}

Fabric provides the Hud API to render and layer elements on the hud.

To start, we need to register a listener to `HudLayerRegistrationCallback` which registers your layers. Each layer takes a `DrawContext` and a `RenderTickCounter` instance as parameters. See the `HudLayerRegistrationCallback` and related Javadocs for more details on how to use the API.

The draw context can be used to access the various rendering utilities provided by the game, and access the raw matrix stack. You should check out the [Draw Context](./draw-context) page to learn more about the draw context.

### Render Tick Counter {#render-tick-counter}

The `RenderTickCounter` class allows you to retrieve the current `tickDelta` value.

`tickDelta` is the "progress" between the last game tick and the next game tick.

For example, if we assume a 200 FPS scenario, the game runs a new tick roughly every 10 frames. Each frame, `tickDelta` represents how far we are between the last tick and the next. Over 10 frames, you might see:

| Frame | tickDelta                                     |
|-------|-----------------------------------------------|
| 1     | `1.0` (new tick)                              |
| 2     | `0.1 (1÷10)` - The next tick is in 10 frames. |
| 3     | `0.2 (2÷10)`                                  |
| 4     | `0.3 (3÷10)`                                  |
| 5     | `0.4 (4÷10)`                                  |
| 6     | `0.5 (5÷10)`                                  |
| 7     | `0.6 (6÷10)`                                  |
| 8     | `0.7 (7÷10)`                                  |
| 9     | `0.8 (8÷10)`                                  |
| 10    | `0.9 (9÷10)`                                  |
| 11    | `1.0 (10÷10)` (new tick)                      |

Practically, you should only use `tickDelta` when your animations depend on Minecraft's ticks. For time-based animations, use `Util.getMeasuringTimeMs()`, which measures real-world time.

You can retrieve `tickDelta` using the `renderTickCounter.getTickDelta(false);` function, where the boolean parameter is `ignoreFreeze`, which essentially just allows you to ignore whenever players use the `/tick freeze` command.

In this example, we'll use `Util.getMeasuringTimeMs()` to linearly interpolate the color of a square that is being rendered to the HUD.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![Lerping a color over time](/assets/develop/rendering/hud-rendering-deltatick.webp)

Why don't you try use `tickDelta` and see what happens to the animation when you run the `/tick freeze` command? You should see the animation freeze in place as `tickDelta` becomes constant (assuming you have passed `false` as the parameter to `RenderTickCounter#getTickDelta`)
