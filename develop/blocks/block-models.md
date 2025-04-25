---
title: Block Models
description: A guide to writing and understanding block models.
authors:
  - Fellteros
---

This page will guide you through writing your own block models and understanding all their options and possibilities.

## What Are Block Models? {#what-are-block-models}

Block models are essentially the definition of a block's looks and visuals. They specify a texture, model translation, rotation, scale and other attributes.
Models are stored as JSON files in your ``resources`` folder.

## File Structure {#file-structure}

Every block model file has a defined structure that has to be followed. It all starts with empty curly brackets = the **root tag** of the model. Here's a brief scheme of how block models are structured:

```json
{
  "parent": "...",
  "ambientocclusion": "true/false",
  "display": {
    "<position>": {
      "rotation": [0, 0, 0],
      "translation": [0, 0, 0],
      "scale": [0, 0, 0]
    }
  },
  "textures": {
    "particle": "...",
    "<texture_variable>": "..."
  },
  "elements": [
    {
      "from": [0, 0, 0],
      "to": [0, 0, 0],
      "rotation": {
        "origin": [0, 0, 0],
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

Loads a different model with all its attributes from the given path, as a resource location (`namespace:path`).

Can be set to `builtin/generated` to use a model created out of the specified icon. Only the first layer is supported, and rotation can be achieved via [blockstates](./blockstates).

### Ambient Occlusion {#ambient-occlusion}

````json
{
  "ambientocclusion": "true/false"
}
````

This tag specifies whether to use [ambient occlusion](https://en.wikipedia.org/wiki/Ambient_occlusion). Defaults to ``true``.

### Display {#display}

```json
{
  "display": {
    "<position>": {
      "rotation": [0, 0, 0],
      "translation": [0, 0, 0],
      "scale": [0, 0, 0]
    }
  }
}
```

This tag is responsible for setting the model translation, rotation and scale in a specified position. The position object can be one of these eight strings and defines how the model looks in different positions:

| \<position>               | Description                                     |
|---------------------------|-------------------------------------------------|
| ``firstperson_righthand`` | Right hand when looking from first-person view. |
| ``firstperson_lefthand``  | Left hand when looking from first-person view.  |
| ``thirdperson_righthand`` | Right hand when looking from third-person view. |
| ``thirdperson_lefthand``  | Left hand when looking from third-person view.  |
| ``gui``                   | GUI, inventory.                                 |
| ``head``                  | When put above player's head (e.g., banner).    |
| ``ground``                | On the ground.                                  |
| ``fixed``                 | When put in item frame.                         |

Furthermore, each position can contain these three values, each in the form of an array of floats:

```json
{
  "rotation": [0, 0, 0],
  "translation": [0, 0, 0],
  "scale": [0, 0, 0]
}
```

1. ``rotation``: _Three floats_. Specifies the rotation of the model according to the scheme `[x, y, z]`.
2. ``translation``: _Three floats_. Specifies the translation of the model according to the scheme `[x, y, z]`. Values are limited to **-80** and **80**, anything exceeding these limits is displayed as them.
3. ``scale``: _Three floats_. Specifies the scale of the model according to the scheme `[x, y, z]`. The maximum value is **4**, anything bigger is displayed as 4.

### Textures {#textures}

```json
{
  "textures": {
    "particle": "...",
    "<texture_variable>": "..."
  }
}
```

The ``textures`` tag holds the textures of the model, in the form of a resource location or a texture variable. It contains three additional objects:

1. ``particle``: _String_. Defines the texture to load particles from. This texture is also used as an overlay if you are in a nether portal, and used for water and lava's still textures. Is also considered a texture variable that can be referenced as ``#particle``
2. ``<texture_variable>``: _String_. It creates a variable and assigns a texture. Can be further referenced with the ``#`` prefix (e.g., `"top": "namespace:path"` ⇒ ``#top``)

### Elements {#elements}

```json
{
  "elements": [
    {
      "from": [0, 0, 0],
      "to": [0, 0, 0],
      "rotation": {
        "origin": [0, 0, 0],
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
  "from": [0, 0, 0],
  "to": [0, 0, 0]
}
```

``from`` specifies the starting point of the cuboid according to the scheme `[x, y, z]` relative to the lower left corner. `to` specifies the ending point. A cuboid starting at `[0, 0, 0]` and ending at `[16, 16, 16]` is as big as a standard block.
The values of both must be between **-16** and **32**, which means that every block model can be at most 3x3 blocks big.

```json
{
  "rotation": {
    "origin": [0, 0, 0],
    "axis": "...",
    "angle": "...",
    "rescale": "true/false"
  }
}
```

`rotation` defines the rotation of an element. It furthermore contains four more values:

1. ``origin``: _Three floats_. Sets the center of the rotation according to the scheme `[x, y, z]`.
2. ``axis``: _String_. Specifies the direction of rotation, and may be one of these: `x`, `y` and `z`.
3. ``angle``: _Float_. Specifies the angle of rotation. Ranges from **-45** to **45** in 22.5 degree increments.
4. ``rescale``: _Boolean_. Specifies whether to scale the faces across the whole block by scaling the non-rotated faces by ``1 + 1 / (cos(angle) - 1 )``

```json
{
  "shade": "true/false"
}
```

``shade`` defines if shadows are rendered (``true`` - default) or not (``false``).

````json
{
  "light_emission": "..."
}
````

``light_emission`` defines the minimum light level that the element can receive. It can be in range from **0** to **15** and default to 0.

````json
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
````

``faces`` holds all the faces of the cuboid. If a face is left out, it does not render. Takes the form of a _string_, with these six options: ``down``, ``up``, ``north``, ``south``, ``west`` or ``east``. Each contains the properties of the specified face.
Additionally, it contains these five values:

1. ``uv``: _Four integers_. Defines the area of the texture to use according to the scheme ``[x1, y1, x2, y2]``. If unset, it defaults to values equal to xyz position of the element.
If the numbers of ``x1`` and ``x2`` are swapped (e.g., from ``0, 0, 16, 16`` to ``16, 0, 0, 16``), the texture flips. UV is optional, and if not supplied, it automatically generates based on the element's position.
2. ``texture``: _String_. Specifies the texture of the face in the form of a [texture variable](#textures) prepended with `#`.
3. ``cullface``: _String_. Can be: ``down``, ``up``, ``north``, ``south``, ``west``, or ``east``. Specifies whether a face does not need to be rendered when there is a block touching it in the specified position.
It also determines the side of the block to use the light level from for lighting the face, and if unset, defaults to the side.
4. ``rotation``: _Integer_. Rotates the texture by the specified number of degrees in 90 degree increments. Rotation does not affect which part of the texture is used.
Instead, it amounts to permutation of the selected texture vertices (selected implicitly, or explicitly though ``uv``).
5. ``tintidex``: _Integer_. Determines whether to tint the texture. The default value, ``-1``, indicates not to use the tint.
Any other number is provided to ``BlockColors`` to get the tint value corresponding to that index (returns white when the block doesn't have a tint index defined).

## Sources and Links {#sources-and-links}

You can visit Minecraft wiki's [block model page](https://minecraft.wiki/w/Model#Block_models) for more detailed walkthrough. The majority of information is from this page.
