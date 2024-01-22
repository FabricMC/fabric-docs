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

## The `Tessellator`

The `Tessellator` is the main class used to render things in Minecraft. It is a singleton, meaning that there is only one instance of it in the game. You can get the instance using `Tessellator.getInstance()`.

## The `BufferBuilder`

The `BufferBuilder` is the class used to format and upload rendering data to OpenGL. It is used to create a buffer, which is then uploaded to OpenGL to draw.

The `Tessellator` is used to create a `BufferBuilder`, which is used to format and upload rendering data to OpenGL. You can create a `BufferBuilder` using `Tessellator.getBuffer()`.

### Initializing the `BufferBuilder`

Before you can write anything to the `BufferBuilder`, you must initialize it. This is done using `BufferBuilder.begin()`, which takes in a `VertexFormat` and a draw mode.

