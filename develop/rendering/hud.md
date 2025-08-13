---
title: Rendering in the HUD
description: Learn how to use the Fabric Hud API to render to the HUD.
authors:
  - IMB11
  - kevinthegreat1
---
<!-- TODO: Enable this paragraph once the reference mod is fixed. -->
<!-- We already briefly touched on rendering things to the HUD in the [Basic Rendering Concepts](./basic-concepts) page and [Using The Drawing Context](./draw-context), so on this page we'll stick to the Hud API and the `RenderTickCounter` parameter. -->

We already briefly touched on rendering things to the HUD in [Using The Drawing Context](./draw-context), so on this page we'll stick to the Hud API and the `RenderTickCounter` parameter.

## `HudRenderCallback` {#hudrendercallback}

::: warning
Previously, Fabric provided `HudRenderCallback` to render to the HUD. Due to changes to HUD rendering, this event became extremely limited and is deprecated since Fabric API 0.116. Usage is strongly discouraged.
:::

## `HudElementRegistry` {#hudelementregistry}

Fabric provides the Hud API to render and layer elements on the HUD.

To start, we need to register a listener to `HudElementRegistry` which registers your elements. Each element is an `HudElement`. A `HudElement` instance is usually a lambda that takes a `DrawContext` and a `RenderTickCounter` instance as parameters. See `HudElementRegistry` and related Javadocs for more details on how to use the API.

The draw context can be used to access the various rendering utilities provided by the game, and access the raw matrix stack. You should check out the [Draw Context](./draw-context) page to learn more about the draw context.

### Render Tick Counter {#render-tick-counter}

The `RenderTickCounter` class allows you to retrieve the current `tickProgress` value. `tickProgress` is the "progress" between the last game tick and the next game tick.

For example, if we assume a 200 FPS scenario, the game runs a new tick roughly every 10 frames. Each frame, `tickDelta` represents how far we are between the last tick and the next. Over 11 frames, you might see:

| Frame | `tickProgress` |
|:-----:|----------------|
|  `1`  | `1`: New tick  |
|  `2`  | `1/10 = 0.1`   |
|  `3`  | `2/10 = 0.2`   |
|  `4`  | `3/10 = 0.3`   |
|  `5`  | `4/10 = 0.4`   |
|  `6`  | `5/10 = 0.5`   |
|  `7`  | `6/10 = 0.6`   |
|  `8`  | `7/10 = 0.7`   |
|  `9`  | `8/10 = 0.8`   |
| `10`  | `9/10 = 0.9`   |
| `11`  | `1`: New tick  |

In practice, you should only use `tickProgress` when your animations depend on Minecraft's ticks. For time-based animations, use `Util.getMeasuringTimeMs()`, which measures real-world time.

You can retrieve `tickProgress` by calling `renderTickCounter.getTickProgress(false)`, where the boolean parameter is `ignoreFreeze`, which essentially just allows you to ignore whenever players use the `/tick freeze` command.

In this example, we'll use `Util.getMeasuringTimeMs()` to linearly interpolate the color of a square that is being rendered to the HUD.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/HudRenderingEntrypoint.java)

![Lerping a color over time](/assets/develop/rendering/hud-rendering-deltatick.webp)

Why don't you try use `tickDelta` and see what happens to the animation when you run the `/tick freeze` command? You should see the animation freeze in place as `tickDelta` becomes constant (assuming you have passed `false` as the parameter to `RenderTickCounter#getTickDelta`)
