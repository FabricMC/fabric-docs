---
title: Rendering in the World
description: Create and use custom render pipelines when vanilla pipelines don't suit your needs.
authors:
  - kevinthegreat1
---

::: tip
It is recommended to read [Rendering Concepts](./basic-concepts.md) first. This page builds on those concepts and discusses how to render objects in the world.

This page also contains more modern rendering concepts, and you can read this page to get a better understanding of the "extraction" and "drawing/rendering" split.
:::

To render custom objects in the world, you have two choices. You can inject into existing vanilla rendering and add your code, but that limits you to existing vanilla render pipelines. If existing vanilla render pipelines don't suit your needs, you need a custom render pipeline.

Before we get into custom render pipelines, let's look at vanilla rendering.

## The Extraction and Drawing Phases {#the-extraction-and-drawing-phases}

As mentioned in [Rendering Concepts](./basic-concepts.md), Minecraft rendering is in the process of being split into "extraction" and "rendering" phases.

All data needed for rendering is collected during the "extraction" phase. This includes, for example, writing to the buffered builder. When calling a render method, such as `VertexRedering.drawFilledBox`, it is writing vertices to the buffered builder, and is part of the "extraction" phase. You should add all elements you want to render during this phase.

When the "extraction" phase is done, the "drawing/rendering" phase starts, and the buffered builder is built. During this phase, the buffered builder is drawn to the screen. The ultimate goal of this "extraction" and "drawing" split is to allow for drawing the previous frame in parallel to extracting the next frame, improving performance.

Now let's look at how to create a custom render pipeline, with these two phases in mind.

## Custom Render Pipelines {#custom-render-pipelines}

Let's say we want to render waypoints, which should appear through walls. But the closest pipeline in vanilla is `RenderPipelines#DEBUG_FILLED_BOX`, which doesn't render through walls. So we will need a custom render pipeline.

### Defining a Custom Render Pipeline {#defining-a-custom-render-pipeline}

We define a custom render pipeline in a class:

@[code lang=java transcludeWith=:::custom-pipelines:define-pipeline](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

### Extraction Phase {#extraction-phase}

We first implement the "extraction" phase. We can call this method during the "extraction" phase to add a waypoint to be rendered.

@[code lang=java transcludeWith=:::custom-pipelines:extraction-phase](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

Note that the size used in the `BufferAllocator` constructor depends on the render pipeline you are using. In our case, it is `RenderLayer.CUTOUT_BUFFER_SIZE`.

::: info
If you want to render multiple waypoints, call this method multiple times. Make sure you do so during the "extraction" phase BEFORE the "drawing/rendering" phase starts, at which point the buffer builder is built.
:::

### Render States {#render-states}

Note that in the above code we are saving the `BufferBuilder` in a field. This is because we need it in the "rendering" phase. In this case, the `BufferBuilder` is our "render state" or "extracted data". If you need additional data during the "rendering" phase, you should create a custom render state class to hold the `BufferedBuilder` and any additional rendering data you need.

### Rendering Phase {#rendering-phase}

Now we implement the "drawing/rendering" phase. This should be called after all waypoints you want to render have been added to the `BufferBuilder` during the "extraction" phase.

@[code lang=java transcludeWith=:::custom-pipelines:drawing-phase](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

### Cleaning up {#cleaning-up}

Finally, we need to clean up our resources when the game renderer is closed. This method should be called from `GameRenderer#close`. As of writing, you need to mixin inject into `GameRenderer#close` to do this.

@[code lang=java transcludeWith=:::custom-pipelines:clean-up](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)
@[code lang=java](@/reference/latest/src/client/java/com/example/docs/mixin/client/GameRendererMixin.java)

### Final Code {#final-code}

Combining all the steps from above, we get a simple class that renders a waypoint at `(0, 100, 0)` through walls.

@[code lang=java](@/reference/latest/src/client/java/com/example/docs/rendering/CustomRenderPipeline.java)

Don't forget the `GameRendererMixin` right above as well. Here is our result:

![A waypoint rendering through walls](/assets/develop/rendering/world-rendering-custom-render-pipeline-waypoint.png)
