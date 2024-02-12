---
title: Basic Rendering Concepts
description: Learn about the basic concepts of rendering using Minecraft's rendering engine.
authors:
  - IMB11
  - "0x3C50"
---

# Basic Rendering Concepts

::: warning
Although Minecraft is built using OpenGL, as of version 1.17+ you cannot use legacy OpenGL methods to render your own things. Instead, you must use the new `BufferBuilder` system, which formats rendering data and uploads it to OpenGL to draw.

To summarize, you have to use Minecraft's rendering system, or build your own that utilizes `GL.glDrawElements()`.
:::

This page will cover the basics of rendering using the new system, going over key terminology and concepts.

Although much of rendering in Minecraft is abstracted through the various `DrawContext` methods, and you'll likely not need to touch anything mentioned here, it's still important to understand the basics of how rendering works.

## The `Tessellator`

The `Tessellator` is the main class used to render things in Minecraft. It is a singleton, meaning that there is only one instance of it in the game. You can get the instance using `Tessellator.getInstance()`.

## The `BufferBuilder`

The `BufferBuilder` is the class used to format and upload rendering data to OpenGL. It is used to create a buffer, which is then uploaded to OpenGL to draw.

The `Tessellator` is used to create a `BufferBuilder`, which is used to format and upload rendering data to OpenGL. You can create a `BufferBuilder` using `Tessellator.getBuffer()`.

### Initializing the `BufferBuilder`

Before you can write anything to the `BufferBuilder`, you must initialize it. This is done using `BufferBuilder.begin(...)`, which takes in a `VertexFormat` and a draw mode.

#### Vertex Formats

The `VertexFormat` defines the elements that we include in our data buffer and outlines how these elements should be transmitted to OpenGL.

The following `VertexFormat` elements are available:

| Element                                       | Format                                                                                  |
| --------------------------------------------- | --------------------------------------------------------------------------------------- |
| `BLIT_SCREEN`                                 | `{ position (3 floats: x, y and z), uv (2 floats), color (4 ubytes) }`                  |
| `POSITION_COLOR_TEXTURE_LIGHT_NORMAL`         | `{ position, color, texture uv, texture light (2 shorts), texture normal (3 sbytes) }`  |
| `POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL` | `{ position, color, texture uv, overlay (2 shorts), texture light, normal (3 sbytes) }` |
| `POSITION_TEXTURE_COLOR_LIGHT`                | `{ position, texture uv, color, texture light }`                                        |
| `POSITION`                                    | `{ position }`                                                                          |
| `POSITION_COLOR`                              | `{ position, color }`                                                                   |
| `LINES`                                       | `{ position, color, normal }`                                                           |
| `POSITION_COLOR_LIGHT`                        | `{ position, color, light }`                                                            |
| `POSITION_TEXTURE`                            | `{ position, uv }`                                                                      |
| `POSITION_COLOR_TEXTURE`                      | `{ position, color, uv }`                                                               |
| `POSITION_TEXTURE_COLOR`                      | `{ position, uv, color }`                                                               |
| `POSITION_COLOR_TEXTURE_LIGHT`                | `{ position, color, uv, light }`                                                        |
| `POSITION_TEXTURE_LIGHT_COLOR`                | `{ position, uv, light, color }`                                                        |
| `POSITION_TEXTURE_COLOR_NORMAL`               | `{ position, uv, color, normal }`                                                       |

#### Draw Modes

The draw mode defines how the data is drawn. The following draw modes are available:

| Draw Mode                   | Description                                                                                                                           |
| --------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- |
| `DrawMode.LINES`            | Each element is made up of 2 vertices and is represented as a single line.                                                            |
| `DrawMode.LINE_STRIP`       | The first element requires 2 vertices. Additional elements are drawn with just 1 new vertex, creating a continuous line.              |
| `DrawMode.DEBUG_LINES`      | Similar to `DrawMode.LINES`, but the line is always exactly one pixel wide on the screen.                                             |
| `DrawMode.DEBUG_LINE_STRIP` | Same as `DrawMode.LINE_STRIP`, but lines are always one pixel wide.                                                                   |
| `DrawMode.TRIANGLES`        | Each element is made up of 3 vertices, forming a triangle.                                                                            |
| `DrawMode.TRIANGLE_STRIP`   | Starts with 3 vertices for the first triangle. Each additional vertex forms a new triangle with the last two vertices.                |
| `DrawMode.TRIANGLE_FAN`     | Starts with 3 vertices for the first triangle. Each additional vertex forms a new triangle with the first vertex and the last vertex. |
| `DrawMode.QUADS`            | Each element is made up of 4 vertices, forming a quadrilateral.                                                                       |

### Writing to the `BufferBuilder`

Once the `BufferBuilder` is initialized, you can write data to it.

The `BufferBuilder` allows us to construct our buffer, vertex by vertex. To add a vertex, we use the `buffer.vertex(matrix, float, float, float)` method. The `matrix` parameter is the transformation matrix, which we'll discuss in more detail later. The three float parameters represent the (x, y, z) coordinates of the vertex position.

This method returns a vertex builder, which we can use to specify additional information for the vertex. It's crucial to follow the order of our defined `VertexFormat` when adding this information. If we don't, OpenGL might not interpret our data correctly. After we've finished building a vertex, we call the `.next()` method. This finalizes the current vertex and prepares the builder for the next one.

It's also worth understanding the concept of culling. Culling is the process of removing faces of a 3D shape that aren't visible from the viewer's perspective. If the vertices for a face are specified in the wrong order, the face might not render correctly due to culling.

#### What Is A Transformation Matrix?

A transformation matrix is a 4x4 matrix that is used to transform a vector. In Minecraft, the transformation matrix is just transforming the coordinates we give into the vertex call. The transformations can scale our model, move it around and rotate it.

It's sometimes referred to as a position matrix, or a model matrix.

It's usually obtained via the `MatrixStack` class, which can be obtained via the `DrawContext` object:

```java
drawContext.getMatrices().peek().getPositionMatrix();
```

#### A Practical Example: Rendering a Triangle Strip

It's easier to explain how to write to the `BufferBuilder` using a practical example. Let's say we want to render something using the `DrawMode.TRIANGLE_STRIP` draw mode and the `POSITION_COLOR` vertex format.

We're going to draw vertices at the following points on the HUD (in order):

```txt
(20, 20)
(5, 40)
(35, 40)
(20, 60)
```

This should give us a lovely diamond - since we're using the `TRIANGLE_STRIP` draw mode, the renderer will perform the following steps:

![Four steps that show the placement of the vertices on the screen to form two triangles.](/assets/develop/rendering/concepts-practical-example-draw-process.png)

Since we're drawing on the HUD in this example, we'll use the `HudRenderCallback` event:

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

This results in the following being drawn on the HUD:

![Final Result](/assets/develop/rendering/concepts-practical-example-final-result.png)

::: tip
Try mess around with the colors and positions of the vertices to see what happens! You can also try using different draw modes and vertex formats.
:::

## The `MatrixStack`

After learning how to write to the `BufferBuilder`, you might be wondering how to transform your model - or even animate it. This is where the `MatrixStack` class comes in.

The `MatrixStack` class has the following methods:

- `push()` - Pushes a new matrix onto the stack.
- `pop()` - Pops the top matrix off the stack.
- `peek()` - Returns the top matrix on the stack.
- `translate(x, y, z)` - Translates the top matrix on the stack.
- `scale(x, y, z)` - Scales the top matrix on the stack.

You can also multiply the top matrix on the stack using quaternions, which we will cover in the next section.

Taking from our example above, we can make our diamond scale up and down by using the `MatrixStack` and the `tickDelta` - which is the time that has passed since the last frame.

::: warning
You must push and pop the matrix stack when you're done with it. If you don't, you'll end up with a broken matrix stack, which will cause rendering issues.

Make sure to push the matrix stack before you get a transformation matrix!
:::

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

![A video showing the diamond scaling up and down.](/assets/develop/rendering/concepts-matrix-stack.webp)

## Quaternions (Rotating Things)

Quaternions are a way of representing rotations in 3D space. They are used to rotate the top matrix on the `MatrixStack` via the `multiply(Quaternion, x, y, z)` method.

It's highly unlikely you'll need to ever use a Quaternion class directly, since Minecraft provides various pre-built Quaternion instances in it's `RotationAxis` utility class.

Let's say we want to rotate our diamond around the z-axis. We can do this by using the `MatrixStack` and the `multiply(Quaternion, x, y, z)` method.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/RenderingConceptsEntrypoint.java)

The result of this is the following:

![A video showing the diamond rotating around the z-axis.](/assets/develop/rendering/concepts-quaternions.webp)