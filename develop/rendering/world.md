---
title: Rendering in the World
description: Create and use custom render pipelines when vanilla pipelines don't suit your needs.
authors:
  - kevinthegreat1
---

::: info PREREQUISITES
Make sure you've read [Rendering Concepts](./basic-concepts) first. This page builds on those concepts and discusses how to render objects in the world.

This page explores some more modern rendering concepts. You'll learn more about the two split phases of rendering: "extraction" (or "preparation") and "drawing" (or "rendering"). In this guide, we will refer to the "extraction/preparation" phase as the "extraction" phase and the "drawing/rendering" phase as the "drawing" phase.
:::

To render custom objects in the world, you have two choices. You can inject into existing vanilla rendering and add your code, but that limits you to existing vanilla render pipelines. If existing vanilla render pipelines don't suit your needs, you need a custom render pipeline.

Before we get into custom render pipelines, let's look at vanilla rendering.

## The Extraction and Drawing Phases {#the-extraction-and-drawing-phases}

As mentioned in [Rendering Concepts](./basic-concepts), recent Minecraft updates are working on splitting rendering into two phases: "extraction" and "drawing".

All data needed for rendering is collected during the "extraction" phase. This includes, for example, writing to the buffered builder. Calling a render method, such as `VertexRedering.drawFilledBox`, writes vertices to the buffered builder, and is part of the "extraction" phase. Note that even though many methods are prefixed with `draw` or `render`, they should be called during the "extraction" phase. You should add all elements you want to render during this phase.

When the "extraction" phase is done, the "drawing" phase starts, and the buffered builder is built. During this phase, the buffered builder is drawn to the screen. The ultimate goal of this "extraction" and "drawing" split is to allow for drawing the previous frame in parallel to extracting the next frame, improving performance.

Now, with these two phases in mind, let's look at how to create a custom render pipeline.

## Custom Render Pipelines {#custom-render-pipelines}

Let's say we want to render waypoints, which should appear through walls. The closest vanilla pipeline for that would be `RenderPipelines#DEBUG_FILLED_BOX`, but it doesn't render through walls, so we will need a custom render pipeline.

### Defining a Custom Render Pipeline {#defining-a-custom-render-pipeline}

We define a custom render pipeline in a class:

@[code lang=java transcludeWith=:::custom-pipelines:define-pipeline](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

### Extraction Phase {#extraction-phase}

We first implement the "extraction" phase. We can call this method during the "extraction" phase to add a waypoint to be rendered.

@[code lang=java transcludeWith=:::custom-pipelines:extraction-phase](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

Note that the size used in the `BufferAllocator` constructor depends on the render pipeline you are using. In our case, it is `RenderLayer.CUTOUT_BUFFER_SIZE`.

If you want to render multiple waypoints, call this method multiple times. Make sure you do so during the "extraction" phase, BEFORE the "drawing" phase starts, at which point the buffer builder is built.

### Render States {#render-states}

Note that in the above code we are saving the `BufferBuilder` in a field. This is because we need it in the "drawing" phase. In this case, the `BufferBuilder` is our "render state" or "extracted data". If you need additional data during the "drawing" phase, you should create a custom render state class to hold the `BufferedBuilder` and any additional rendering data you need.

### Drawing Phase {#drawing-phase}

Now we'll implement the "drawing" phase. This should be called after all waypoints you want to render have been added to the `BufferBuilder` during the "extraction" phase.

@[code lang=java transcludeWith=:::custom-pipelines:drawing-phase](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

### Cleaning up {#cleaning-up}

Finally, we need to clean up resources when the game renderer is closed. `GameRenderer#close` should call this method, and for that you currently need to inject into `GameRenderer#close` with a mixin.

@[code lang=java transcludeWith=:::custom-pipelines:clean-up](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)
@[code lang=java](@/reference/latest/src/client/java/com/example/docs/mixin/client/GameRendererMixin.java)

### Final Code {#final-code}

Combining all the steps from above, we get a simple class that renders a waypoint at `(0, 100, 0)` through walls.

@[code lang=java](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

Don't forget the `GameRendererMixin` as well! Here is the result:

![A waypoint rendering through walls](/assets/develop/rendering/world-rendering-custom-render-pipeline-waypoint.png)
