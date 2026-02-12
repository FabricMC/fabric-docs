---
title: Block Models
description: A guide to writing and understanding block models.
authors:
  - Fellteros
  - its-miroma
---

<!-- markdownlint-disable search-replace -->

This page will guide you through writing your own block models and understanding all their options and possibilities.

## What Are Block Models? {#what-are-block-models}

Block models are essentially the definition of a block's looks and visuals. They specify a texture, model translation, rotation, scale and other attributes.

Models are stored as JSON files in your `resources` folder.

## File Structure {#file-structure}

Every block model file has a defined structure that has to be followed. It starts with empty curly brackets, which represent the **root tag** of the model. Here's a brief scheme of how block models are structured:

```json
{
  "parent": "...",
  "ambientocclusion": "true/false",
  "display": {
    "<position>": {
      "rotation": [0.0, 0.0, 0.0],
      "translation": [0.0, 0.0, 0.0],
      "scale": [0.0, 0.0, 0.0]
    }
  },
  "textures": {
    "particle": "...",
    "<texture_variable>": "..."
  },
  "elements": [
    {
      "from": [0.0, 0.0, 0.0],
      "to": [0.0, 0.0, 0.0],
      "rotation": {
        "origin": [0.0, 0.0, 0.0],
        "axis": "...",
        "angle": "...",
        "rescale": "true/false"
      },
      "shade": "true/false",
      "light_emission": "...",
      "faces": {
        "<key>": {
          "uv": [0, 0, 0, 0],
          "texture": "...",
          "cullface": "...",
          "rotation": "...",
          "tintindex": "..."
        }
      }
    }
  ]
}
```

<!--@include: ../items/item-models.md#parent-->

Set this tag to `builtin/generated` to use a model created from the specified icon. Rotation can be achieved via [blockstates](./blockstates).

### Ambient Occlusion {#ambient-occlusion}

```json
{
  "ambientocclusion": "true/false"
}
```

This tag specifies whether to use [ambient occlusion](https://en.wikipedia.org/wiki/Ambient_occlusion). Defaults to `true`.

<!--@include: ../items/item-models.md#display-->

### Textures {#textures}

```json
{
  "textures": {
    "particle": "...",
    "<texture_variable>": "..."
  }
}
```

The `textures` tag holds the textures of the model, in the form of an identifier or a texture variable. It contains three additional objects:

1. `particle`: _String_. Defines the texture to load particles from. This texture is also used as an overlay if you are in a nether portal, and used for water and lava's still textures. Is also considered a texture variable that can be referenced as `#particle`.
2. `<texture_variable>`: _String_. Creates a variable and assigns a texture. Can be later referenced with the `#` prefix (e.g., `"top": "namespace:path"` ⇒ `#top`)

<!--@include: ../items/item-models.md#elements-->

<!--@include: ../items/item-models.md#from-->

`from` specifies the starting point of the cuboid according to the scheme `[x, y, z]`, relative to the lower left corner. `to` specifies the ending point. A cuboid as big as a standard block would start at `[0, 0, 0]` and end at `[16, 16, 16]`.
The values of both must be between **-16** and **32**, which means that every block model can be at most 3×3 blocks big.

<!--@include: ../items/item-models.md#rotation-->

`rotation` defines the rotation of an element. It contains four more values:

1. `origin`: _Three floats_. Sets the center of the rotation according to the scheme `[x, y, z]`.
2. `axis`: _String_. Specifies the direction of rotation, and must be one of these: `x`, `y` and `z`.
3. `angle`: _Float_. Specifies the angle of rotation. Ranges from **-45** to **45**.
4. `rescale`: _Boolean_. Specifies whether to scale the faces across the whole block. Defaults to `false`.

<!--@include: ../items/item-models.md#shade-to-faces-->

1. `uv`: _Four integers_. Defines the area of the texture to use according to the scheme `[x1, y1, x2, y2]`. If unset, it defaults to values equal to xyz position of the element.
   Flipping the values of `x1` and `x2` (for example from `0, 0, 16, 16` to `16, 0, 0, 16`) flips the texture. UV is optional, and if not supplied, it's automatically generated based on the element's position.
2. `texture`: _String_. Specifies the texture of the face in the form of a [texture variable](#textures), prepended with `#`.
3. `cullface`: _String_. Can be: `down`, `up`, `north`, `south`, `west`, or `east`. Specifies whether a face does not need to be rendered when there is a block touching it in the specified position.
   It also determines the side of the block to use the light level from for lighting the face, and if unset, defaults to the side.
4. `rotation`: _Integer_. Rotates the texture clockwise by the specified number of degrees in 90 degree increments. Rotation does not affect which part of the texture is used.
   Instead, it amounts to permutation of the selected texture vertices (selected implicitly, or explicitly though `uv`).
5. `tintidex`: _Integer_. Tints the texture on that face using a tint value. The default value, `-1`, indicates not to use the tint.
   Any other number is provided to `BlockColors` to get the tint value corresponding to that index (returns white when the block doesn't have a tint index defined).

## Sources and Links {#sources-and-links}

You can visit Minecraft Wiki's [Block Models page](https://minecraft.wiki/w/Model#Block_models) for a more detailed walkthrough. A lot of information here is from that page.
