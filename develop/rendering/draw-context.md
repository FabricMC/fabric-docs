---
title: Using The Drawing Context
description: Learn how to use the DrawContext class to render various shapes, text and textures.
authors:
  - IMB11
---

# Using The Drawing Context

This page assumes you've taken a look at the [Basic Rendering Concepts](./basic-concepts.md) page.

The `DrawContext` class is the main class used for rendering in the game. It is used for rendering shapes, text and textures, and as previously seen, used to manipulate `MatrixStack`s and use `BufferBuilder`s.

## Drawing Shapes

The `DrawContext` class can be used to easily draw **square-based** shapes. If you want to draw triangles, or any non-square based shape, you will need to use a `BufferBuilder`.

### Drawing Rectangles

You can use the `DrawContext.fill(...)` method to draw a filled rectangle.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![A rectangle.](/assets/develop/rendering/draw-context-rectangle.png)

### Drawing Outlines/Borders

Let's say we want to outline the rectangle we just drew. We can use the `DrawContext.drawBorder(...)` method to draw an outline.

@[code lang=java transcludeWith=:::2](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Rectangle with border.](/assets/develop/rendering/draw-context-rectangle-border.png)

### Drawing Individual Lines

We can use the `DrawContext.drawHorizontalLine(...)` and `DrawContext.drawVerticalLine(...)` methods to draw lines.

@[code lang=java transcludeWith=:::3](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Lines](/assets/develop/rendering/draw-context-lines.png)

## The Scissor Manager

The `DrawContext` class has a built-in scissor manager. This allows you to easily clip your rendering to a specific area. This is useful for rendering things like tooltips, or other elements that should not be rendered outside of a specific area.

### Using The Scissor Manager

::: tip
Scissor regions can be nested! But make sure that you disable the scissor manager the same amount of times as you enabled it.
:::

To enable the scissor manager, simply use the `DrawContext.enableScissor(...)` method. Likewise, to disable the scissor manager, use the `DrawContext.disableScissor()` method.

@[code lang=java transcludeWith=:::4](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Scissor region in action.](/assets/develop/rendering/draw-context-scissor.png)

As you can see, even though we tell the game to render the gradient across the entire screen, it only renders within the scissor region.

## Drawing Textures

There is no one "correct" way to draw textures onto a screen, as the `drawTexture(...)` method has many different overloads. This section will go over the most common use cases.

### Drawing an Entire Texture

Generally, it's recommended that you use the overload that specifies the `textureWidth` and `textureHeight` parameters. This is because the `DrawContext` class will assume these values if you don't provide them, which can sometimes be wrong.

@[code lang=java transcludeWith=:::5](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Drawing whole texture example.](/assets/develop/rendering/draw-context-whole-texture.png)

### Drawing a Portion of a Texture

This is where `u` and `v` come in. These parameters specify the top-left corner of the texture to draw, and the `regionWidth` and `regionHeight` parameters specify the size of the portion of the texture to draw.

Let's take this texture as an example.

![Recipe Book Texture](/assets/develop/rendering/draw-context-recipe-book-background.png)

If we want to only draw a region that contains the magnifying glass, we can use the following `u`, `v`, `regionWidth` and `regionHeight` values:

@[code lang=java transcludeWith=:::6](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Region Texture](/assets/develop/rendering/draw-context-region-texture.png)

## Drawing Text

The `DrawContext` class has various self-explanatory text rendering methods - for the sake of brevity, they will not be covered here.

Let's say we want to draw "Hello World" onto the screen. We can use the `DrawContext.drawText(...)` method to do this.

@[code lang=java transcludeWith=:::7](@/reference/latest/src/client/java/com/example/docs/rendering/DrawContextExampleScreen.java)

![Drawing text](/assets/develop/rendering/draw-context-text.png)