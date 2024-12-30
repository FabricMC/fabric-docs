---
title: Block Entity Renderers
description: Learn how to spice rendering up with block entity renderers.
authors:
  - natri0
---

# Block Entity Renderers {#block-entity-renderers}

Sometimes, using Minecraft's model format is not enough. If you need to add dynamic rendering to it, you will need to use a `BlockEntityRenderer`.

For example, let's make the Counter Block from the [Block Entities article](../blocks/block-entities) show the number of clicks on its top side.

## Creating a BlockEntityRenderer {#creating-a-blockentityrenderer}

First, we need to create a `BlockEntityRenderer` for our `CounterBlockEntity`.

When creating a `BlockEntityRenderer` for the `CounterBlockEntity`, it's important to place the class in the appropriate source set, such as `src/client/`, if your project uses split source sets for client and server. Accessing rendering-related classes directly in the `src/main/` source set is not safe because those classes might be loaded on a server.

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

The new class has a constructor with `BlockEntityRendererFactory.Context` as a parameter. The `Context` has a few useful rendering utilities, like the `ItemRenderer` or `TextRenderer`.
Also, by including a constructor like this, it becomes possible to use the constructor as the `BlockEntityRendererFactory` functional interface itself:

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/FabricDocsBlockEntityRenderer.java)

Add the entrypoint to the `fabric.mod.json` file, so that the renderer is registered.

`BlockEntityRendererFactories` is a registry that maps each `BlockEntityType` with custom rendering code to its respective `BlockEntityRenderer`.

## Drawing on Blocks {#drawing-on-blocks}

Now that we have a renderer, we can draw. The `render` method is called every frame, and it's where the rendering magic happens.

### Moving Around {#moving-around}

First, we need to offset and rotate the text so that it's on the block's top side.

::: info
As the name suggests, the `MatrixStack` is a _stack_, meaning that you can push and pop transformations.
A good rule-of-thumb is to push a new one at the beginning of the `render` method and pop it at the end, so that the rendering of one block doesn't affect others.

More information about the `MatrixStack` can be found in the [Basic Rendering Concepts article](../rendering/basic-concepts).
:::

To make the translations and rotations needed easier to understand, let's visualize them. In this picture, the green block is where the text would be drawn, by default in the furthest bottom-left point of the block:

![Default rendering position](/assets/develop/blocks/block_entity_renderer_1.png)

So first we need to move the text halfway across the block on the X and Z axes, and then move it up to the top of the block on the Y axis:

![Green block in the topmost center point](/assets/develop/blocks/block_entity_renderer_2.png)

This is done with a single `translate` call:

```java
matrices.translate(0.5, 1, 0.5);
```

That's the _translation_ done, _rotation_ and _scale_ remain.

By default, the text is drawn on the XY plane, so we need to rotate it 90 degrees around the X axis to make it face upwards on the XZ plane:

![Green block in the topmost center point, facing upwards](/assets/develop/blocks/block_entity_renderer_3.png)

The `MatrixStack` does not have a `rotate` function, instead we need to use `multiply` and `RotationAxis.POSITIVE_X`:

```java
matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
```

Now the text is in the correct position, but it's too large. The `BlockEntityRenderer` maps the whole block to a `[-0.5, 0.5]` cube, while the `TextRenderer` uses Y coordinates of `[0, 9]`. As such, we need to scale it down by a factor of 18:

```java
matrices.scale(1/18f, 1/18f, 1/18f);
```

Now, the whole transformation looks like this:

@[code transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

### Drawing Text {#drawing-text}

As mentioned earlier, the `Context` passed into the constructor of our renderer has a `TextRenderer` that we can use to draw text. For this example we'll save it in a field.

The `TextRenderer` has methods to measure text (`getWidth`), which is useful for centering, and to draw it (`draw`).

@[code transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/blockentity/CounterBlockEntityRenderer.java)

The `draw` method takes a lot of parameters, but the most important ones are:

- the `Text` (or `String`) to draw;
- its `x` and `y` coordinates;
- the RGB `color` value;
- the `Matrix4f` describing how it should be transformed (to get one from a `MatrixStack`, we can use `.peek().getPositionMatrix()` to get the `Matrix4f` for the topmost entry).

And after all this work, here's the result:

![Counter Block with a number on top](/assets/develop/blocks/block_entity_renderer_4.png)
