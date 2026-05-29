---
title: Custom Shields
description: Learn how to create your own shields and configure their properties.
authors:
  - cassiancc
---

<!--  -->

::: info PREREQUISITES

You must first understand how to [create a tool](./custom-tools).

:::

Shields can be used to defend one's self from attacks. To add a new Shield to the game, you'll need an `Item`, two item models, a client item, and a special renderer.

## Creating the Item {#item}

For this example, we will use the same repair item tag we will be using for armor and tools. We define the tag reference as follows:

@[code transcludeWith=:::repair_tag](@/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java)

Then, we register an item with the following components.

- **Banner Patterns**: Creates an item with no banner patterns.
- **Repairable**: Creates an item that can be repaired with the given item tag.
- **Equippable/Unswappable**: Shift-clicking the item will equip it to the offhand, but right clicking with it will not.
- **Blocks Attacks**: Creates an item that blocks attacks. This is a _delayed component_, meaning that it loads after the world is loaded, and it can reference tags. The parameters given match a vanilla shield.
- **Break Sound**: When the item breaks, it will play the specified sound.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#shield

## Creating the Special Renderer {#special-renderer}

<!-- TODO: BREAKDOWN THE RENDERER -->

First, we'll create a model layer location that points to where the shield model is, then two sprite identifiers that point to where the sprites we'll use are located - one for the normal texture, one for the banner texture.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldLayers.java#layer

Then, register the layer in your client initializer.

<<< @/reference/latest/src/client/java/com/example/docs/ExampleModClient.java#shield-layer

Then, we'll create a special renderer for the item. This one is based off of the vanilla shield renderer, with the sprites changed to use our mod's sprites. The renderer is complicated, so we'll break it down.

### Constructor {#constructor}

The constructor of the renderer is simple - it accepts a `SpriteGetter` that will retrieve we'll be using, and the model we'll be using, in this case a `ShieldModel`. A `SpriteGetter` is an interface that will provide sprites when the model requests them. The constructor stores the `SpriteGetter` and `ShieldModel`

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#renderer

### Extraction {#extraction}

Since we do not want the components of the item renderering to be able to change while in the rendering process, we store a copy of the stack's components via `extractArgument`.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#extract-argument

### Extents {#extents}

We'll also set the extents of the model, defining how it is rendered in the GUI. The model handles this for us.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#extents

### Submission {#submission}

The submission process handles the logic of _what_ to render. The shield render's logic does the following:

1. Check for and store any banner patterns on the shield, defaulting to an empty banner pattern list if not.
2. Check for and store where the shield has been dyed, defaulting to `null` if not.
3. If the shield has banner patterns or has been dyed, use the dyed texture. If not, use the standard texture. 
4. Submit the shield model to be rendered, using the provided parameters and texture.
5. If the shield has banner patterns, submit those as well.
6. If the shield is enchanted, submit the enchantment glint.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#submit

### Unbaked Models {#unbaked-models}

You'll also need an unbaked model, used to reference the model renderer and provide the sprites to the model.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#unbaked

## Creating the Model {#model}

<!-- TODO: ADD MODEL GENERATION, CURRENTLY COPIED FROM VANILLA -->

We'll be creating two item models and a client item for the shield.

- A normal item model, referencing the special renderer.
- A blocking item model, referencing the special renderer.
- A conditional client item that displays the normal item model normally and the blocking model when blocking.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#shield

## Creating the Recipes {#recipes}

<!-- TODO -->

Two recipes are usually needed to access the item in survival - a normal crafting recipe, and a shield decoration recipe to allow for banner patterns.
