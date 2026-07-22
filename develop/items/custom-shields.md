---
title: Custom Shields
description: Learn how to create your own shields and configure their properties.
authors:
  - cassiancc
  - ChampionAsh5357
resources:
  https://mcsrc.dev/1/26.2/net/minecraft/client/renderer/special/ShieldSpecialRenderer: Vanilla Shield Renderer
  https://minecraft.wiki/w/Data_component_format/blocks_attacks: Blocks Attacks Data Component
---

<!--  -->

::: info PREREQUISITES

You must first understand how to [create a tool](./custom-tools). This guide also references data generation for [recipes](../data-generation/recipes), [item models](../data-generation/item-models), and [item tags](../data-generation/tags).

:::

Shields can be used to defend one's self from attacks. To add a new Shield to the game, you'll need an `Item`, two item models, a client item, recipes, item tags, and a special renderer.

## Creating the Item {#item}

::: info PREREQUISITES

For more information, see the documentation on registering [items](first-item).

:::

For this example, we will use the same repair item tag we will be using for armor and tools. We define the tag reference as follows:

<<< @/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java#repair_tag

Then, we register an item with the following components.

- [**Banner Patterns**](https://minecraft.wiki/w/Data_component_format/banner_patterns): Creates an item with no banner patterns.
- [**Repairable**](https://minecraft.wiki/w/Data_component_format/repairable): Creates an item that can be repaired with the given item tag.
- [**Equippable/Unswappable**](https://minecraft.wiki/w/Data_component_format/equippable): In the GUI, shift-clicking the item will equip it to the offhand. In the world, right-clicking with it will not equip the item.
- [**Blocks Attacks**](https://minecraft.wiki/w/Data_component_format/blocks_attacks): Creates an item that blocks attacks. This is a _delayed component_, meaning that it loads after the world is loaded, allowing it to reference datapack objects like tags. This example uses the same parameters as a vanilla shield, but individual numbers can be easily tweaked for your mod's needs.
- [**Break Sound**](https://minecraft.wiki/w/Data_component_format/break_sound): When the item breaks, it will play the specified sound.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#shield
<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#shield

## Creating the Special Renderer {#special-renderer}

As shields are more complicated than a standard item model, we'll be using a special renderer to render the shield rather than the normal item model. This special renderer is based off the vanilla [`ShieldSpecialRenderer`](https://mcsrc.dev/1/26.2/net/minecraft/client/renderer/special/ShieldSpecialRenderer), modified to accept textures for shields other than the vanilla shield.

First, we'll create a model layer location that points to where the shield model is.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldLayers.java#layer

Then, register the layer in your client initializer.

<<< @/reference/latest/src/client/java/com/example/docs/ExampleModClient.java#shield_layer

Then, we'll create a special renderer for the item. This one is based off of the vanilla shield renderer, with changes made to allow it to take in custom sprites from the client item. We'll provide those sprites to the renderer in the next section. The renderer is complicated, so we'll break it down.

### Constructor {#constructor}

The constructor of the renderer is simple - it accepts a `SpriteGetter` that will retrieve we'll be using, and the model we'll be using, in this case a `ShieldModel`. A `SpriteGetter` is an interface that will provide sprites from `Identifier`s. The constructor stores the `SpriteGetter` and `ShieldModel` as fields.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#renderer

### Extraction {#extraction}

When extracting data to be rendered, we need a immutable copy of the data that contains only the information needed to render the item. We can retrieve that from the `ItemStack` by converting its `DataComponentMap` to an immutable one in `extractArgument`.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#extract_argument

### Extents {#extents}

We'll also set the extents of the model, defining the model's bounding box, which is used for rendering and animations. The model handles this for us.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#extents

### Submission {#submission}

The submission process handles the logic of _what_ to render. The shield render's logic does the following:

1. Retrieve the shield's banner patterns and store them in a variable. If the shield has no banner patterns, this variable is set to `BannerPatternLayers.EMPTY`.
2. Retrieve the shield's dye colour and store it in a variable, `baseColor`. If the shield has no dye colour, this variable is set to `null`.
3. If the shield has banner patterns or has been dyed, use the `base` texture. If not, use the `base_nopattern` texture.
4. Submit the shield model to be rendered, using the provided parameters and texture.
5. If the shield has banner patterns, submit those as well.
6. If the shield is enchanted, submit the enchantment glint.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#submit

### Unbaked Models {#unbaked-models}

You'll also need an unbaked model, used to reference the model renderer and provide the sprites to the model.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#unbaked

## Creating the Model {#model}

::: info PREREQUISITES

For more information, see the documentation on generating [item models](../data-generation/item-models).

:::

We'll be creating two item models and a client item for the shield. Add the following lines to your model generator.

- A normal item model which uses the vanilla shield as a parent.
- A blocking item model which uses the vanilla shield's blocking model as a parent.
- A conditional client item that displays the normal item model normally and the blocking model when blocking. This client item uses the special model renderer we created earlier, and supplies it with the textures we'll be using, `guidite_shield_base` (used when the shield has a banner pattern), and `guidite_shield_base_nopattern`.

:::: tabs

== Source Code

::: info

These models can be data generated. For more information, see the documentation on generating [item models](../data-generation/item-models).

:::

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java#shield

== Client Item

`generated/assets/example-mod/items/guidite_shield.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/items/guidite_shield.json

== Item Models

`generated/assets/example-mod/models/item/guidite_shield.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_shield.json

`generated/assets/example-mod/models/item/guidite_shield_blocking.json`

<<< @/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_shield_blocking.json

== Textures

<DownloadEntry visualURL="/assets/develop/items/shield_hd.png" downloadURL="/assets/develop/items/shield.png">Guidite Shield Texture</DownloadEntry>

<DownloadEntry visualURL="/assets/develop/items/shield_base_hd.png" downloadURL="/assets/develop/items/shield_base.png">Guidite Shield Base Texture</DownloadEntry>

::::

## Creating the Recipes {#recipes}

::: info PREREQUISITES

For more information, see the documentation on generating [recipes](../data-generation/recipes).

:::

Two recipes are usually needed to access the item in survival - a normal crafting recipe, and a shield decoration recipe to allow for banner patterns.

You can make the crafting recipe for your shield whatever you want, so we'll only focus on the decoration recipe. We'll use this line in our recipe provider (`ExampleModRecipeProvider#buildRecipes`) to generate a shield decoration recipe.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#shield_decoration

With this recipe defined, you can now put banner patterns on your shield.

<!-- TODO IMAGE -->

## Tagging Shield Items {#tags}

::: info PREREQUISITES

For more information, see the documentation on generating [item tags](../data-generation/tags).

:::

It's also recommended to place your shield in the appropriate item tags. For a shield, that would be usually be `ItemTags.DURABILITY_ENCHANTABLE` to allow it to be enchanted with Mending and Unbreaking, and `ConventionalItemTags.SHIELD_TOOLS`, which modders will be using to look for shields.

In your item tag provider, add the following lines to `addTags`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#shield_tags
