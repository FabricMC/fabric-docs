---
title: Item Models
description: A guide to writing and understanding item models.
authors:
  - Fellteros
  - VatinMc
---

This page will guide you through writing your own item models and understanding all their options and possibilities.

## What Are Item Models? {#what-are-item-models}

Item models are essentially the definition of an item's looks and visuals. They specify a texture, model translation, rotation, scale and other attributes.

Models are stored as JSON files in your `resources` folder.

## File Structure {#file-structure}

Every item model file has a defined structure that has to be followed. It starts with empty curly brackets, which represent the **root tag** of the model. Here's a brief scheme of how item models are structured:

```json
{
  "parent": "...",
  "display": {
    "<position>": {
      "rotation": [0.0, 0.0, 0.0],
      "translation": [0.0, 0.0, 0.0],
      "scale": [0.0, 0.0, 0.0]
    }
  },
  "textures": {
    "<layerN>": "...",
    "particle": "...",
    "<texture_variable>": "..."
  },
  "gui_light": "...",
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
        "<face>": {
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

### Parent {#parent}

```json
{
  "parent": "..."
}
```

Loads a different model with all its attributes from the given path, as an identifier (`namespace:path`).

Set this tag to `item/generated` to use a model created from the specified icon, or set it to `builtin/generated` to use the model without any translation, rotation, or scaling.

### Display {#display}

```json
{
  "display": {
    "<position>": {
      "rotation": [0.0, 0.0, 0.0],
      "translation": [0.0, 0.0, 0.0],
      "scale": [0.0, 0.0, 0.0]
    }
  }
}
```

This tag is responsible for setting the model translation, rotation and scale in a specified position.

The position object can be one of the following strings, which define what the model will look like in different positions:

| Value                   | Description                                         |
|-------------------------|-----------------------------------------------------|
| `firstperson_righthand` | Right hand, as seen in first-person                 |
| `firstperson_lefthand`  | Left hand, as seen in first-person                  |
| `thirdperson_righthand` | Right hand, as seen in third-person (<kbd>F5</kbd>) |
| `thirdperson_lefthand`  | Left hand, as seen in third-person (<kbd>F5</kbd>)  |
| `gui`                   | When in a GUI, for example the inventory            |
| `head`                  | When put on the player's head, for example a banner |
| `ground`                | When on the ground                                  |
| `fixed`                 | When put in an item frame                           |

Furthermore, each position can contain these three values, in the form of an array of floats:

```json
{
  "rotation": [0.0, 0.0, 0.0],
  "translation": [0.0, 0.0, 0.0],
  "scale": [0.0, 0.0, 0.0]
}
```

1. `rotation`: _Three floats_. Specifies the rotation of the model according to the scheme `[x, y, z]`.
2. `translation`: _Three floats_. Specifies the translation of the model according to the scheme `[x, y, z]`. Values must be between `-80` and `80`; anything outside of this range is set to the closest extremum.
3. `scale`: _Three floats_. Specifies the scale of the model according to the scheme `[x, y, z]`. The maximum value is `4`, bigger values are treated as `4`.

### Textures {#textures}

```json
{
  "textures": {
    "<layerN>": "...",
    "particle": "...",
    "<texture_variable>": "..."
  }
}
```

The `textures` tag holds the textures of the model, in the form of an identifier or a texture variable. It contains three additional objects:

1. `<layerN>`: _String_. It specifies the icon of the item used in inventory. There can be more than one layer (e.g., spawn eggs), and at most 3.
2. `particle`: _String_. Defines the texture to load particles from. If not defined, uses the `layer0`.
3. `<texture_variable>`: _String_. It creates a variable and assigns a texture. Can be later referenced with the `#` prefix (e.g., `"top": "namespace:path"` ⇒ `#top`)

:::warning IMPORTANT
`<layerN>` only works if `parent` is set as `item/generated`!
:::

### GUI Light {#gui-light}

```json
{
  "gui_light": "..."
}
```

This tag defines the direction from which the item model is illuminated. Can be either `front` or `side`, with the latter being the default. If set to `front`, the model is rendered like a flat item, if set `side`, the item is rendered like a block.

### Elements {#elements}

```json
{
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
        "<face>": {
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

Contains all the elements of the model, which can only be cubic. If both `parent` and `elements` tags are set, this file's `elements` tag overrides the parent's one.

```json
{
  "from": [0.0, 0.0, 0.0],
  "to": [0.0, 0.0, 0.0]
}
```

`from` specifies the starting point of the cuboid according to the scheme `[x, y, z]`, relative to the lower left corner. `to` specifies the ending point. A cuboid as big as a standard block would start at `[0, 0, 0]` and end at `[16, 16, 16]`.
The values of both must be between **-16** and **32**, which means that every item model can be at most 3×3 blocks big.

```json
{
  "rotation": {
    "origin": [0.0, 0.0, 0.0],
    "axis": "...",
    "angle": "...",
    "rescale": "true/false"
  }
}
```

`rotation` defines the rotation of an element. It contains four more values:

1. `origin`: _Three floats_. Sets the center of the rotation according to the scheme `[x, y, z]`.
2. `axis`: _String_. Specifies the direction of rotation, and must be one of these: `x`, `y` and `z`.
3. `angle`: _Float_. Specifies the angle of rotation. Ranges from **-45** to **45** in 22.5 degree increments.
4. `rescale`: _Boolean_. Specifies whether to scale the faces across the whole block. Defaults to `false`.

```json
{
  "shade": "true/false"
}
```

`shade` defines if shadows are rendered. Defaults to `true`.

```json
{
  "light_emission": "..."
}
```

`light_emission` defines the minimum light level that the element can receive. It can range between **0** and **15**. Defaults to 0.

```json
{
  "faces": {
    "<face>": {
      "uv": [0, 0, 0, 0],
      "texture": "...",
      "cullface": "...",
      "rotation": 0,
      "tintindex": 0
    }
  }
}
```

`faces` holds all the faces of the cuboid. If a face is not set, it will not be rendered. Its keys can be one of: `down`, `up`, `north`, `south`, `west` or `east`. Each keys contains the properties for that face:

1. `uv`: _Four integers_. Defines the area of the texture to use according to the scheme `[x1, y1, x2, y2]`. If unset, it defaults to values equal to xyz position of the element.
  Flipping the values of `x1` and `x2` (for example from `0, 0, 16, 16` to `16, 0, 0, 16`) flips the texture. UV is optional, and if not supplied, it's automatically generated based on the element's position.
2. `texture`: _String_. Specifies the texture of the face in the form of a [texture variable](#textures), prepended with `#`.
3. `cullface`: _String_. Can be: `down`, `up`, `north`, `south`, `west`, or `east`. Specifies whether a face does not need to be rendered when there is a block touching it in the specified position.
  It also determines the side of the block to use the light level from for lighting the face, and if unset, defaults to the side.
4. `rotation`: _Integer_. Rotates the texture by the specified number of degrees in 90 degree increments. Rotation does not affect which part of the texture is used.
  Instead, it amounts to permutation of the selected texture vertices (selected implicitly, or explicitly though `uv`).
5. `tintidex`: _Integer_. Tints the texture on that face using a tint value referenced from the item model definition. If no tint color (or white) is provided, the texture isn't tinted.

## Sources and Links {#sources-and-links}

You can visit Minecraft Wiki's [Item Models page](https://minecraft.wiki/w/Model#Item_models) for a more detailed walkthrough. A lot of information here is from that page.
