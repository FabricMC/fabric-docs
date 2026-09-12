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

Shields can be used to defend oneself from attacks. To add a new shield to the game, you'll need an `Item`, two item models, a client item, recipes, item tags, and a special renderer.

## Creating the Item {#item}

::: info PREREQUISITES

For more information, see the documentation on [creating items](./first-item).

:::

For this example, we will use the same repair item tag that we used in the [Custom Armor](./custom-armor) and [Custom Tools](./custom-tools) pages. We define the tag reference as follows:

<<< @/reference/latest/src/main/java/com/example/docs/item/armor/GuiditeArmorMaterial.java#repair_tag

Then, we create an item id and register an item with the following components.

- [**Banner Patterns**](https://minecraft.wiki/w/Data_component_format/banner_patterns): Creates an item with an empty set of banner patterns.
- [**Repairable**](https://minecraft.wiki/w/Data_component_format/repairable): Creates an item that can be repaired with the given item tag.
- [**Equippable/Unswappable**](https://minecraft.wiki/w/Data_component_format/equippable): In the GUI, shift-clicking the item will equip it to the offhand. In the world, right-clicking with it will not equip the item.
- [**Blocks Attacks**](https://minecraft.wiki/w/Data_component_format/blocks_attacks): Creates an item that blocks attacks. This example uses values from the vanilla shield.
  - This is a _delayed component_, meaning that it loads after the world is loaded, allowing it to reference datapack objects like tags.
- [**Break Sound**](https://minecraft.wiki/w/Data_component_format/break_sound): When the item breaks, it will play the specified sound.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#shield
<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#shield

Remember to add it to a creative tab if you want to access it from the creative inventory!

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#add_guidite_shield_to_create_tab

## Creating the Special Renderer {#special-renderer}

We'll be using a special renderer to render the shield, rather than the normal item model.

First, we'll create a model layer location that points to where the shield model is:

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldLayers.java#layer

Then, register the layer in your client initializer:

<<< @/reference/latest/src/client/java/com/example/docs/ExampleModClient.java#shield_layer

Then, we'll create a special renderer for the item. This one is based off of the vanilla [`ShieldSpecialRenderer`](https://mcsrc.dev/1/26.2/net/minecraft/client/renderer/special/ShieldSpecialRenderer), with changes made to allow it to take in custom sprites from the client item. We'll provide those sprites to the renderer in the next section.

The renderer is complicated, so we'll break it down.

### Constructor {#constructor}

The constructor of the renderer accepts four parameters:

- A `SpriteGetter` interface that can provide sprites from `Identifier`s.
- The model we'll be using, in this case a `ShieldModel`.
- The base white texture (provided in the client item), provided as a `SpriteId`.
- The texture used when no dye or banner patterns are present, provided as a `SpriteId`.

The constructor stores all four parameters as fields so that we can use them later on.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#renderer

### Extraction {#extraction}

When extracting data to be rendered, we need an immutable copy of the data that only contains the information needed to render the item. We can retrieve that from the `ItemStack` by converting its `DataComponentMap` to an immutable one in `extractArgument`:

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#extract_argument

### Extents {#extents}

We'll also set the extents of the model, defining the model's bounding box, which is used for rendering and animations in the model:

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#extents

### Submission {#submission}

The submission process handles the logic of _what_ to render. The shield render's logic does the following:

1. Retrieve the shield's banner patterns and store them in `patterns`. If the shield has no banner patterns, it is set to `BannerPatternLayers.EMPTY`.
2. Retrieve the shield's dye color and store it in `baseColor`. If the shield has no dye color, this variable is set to `null`.
3. If the shield has banner patterns or has been dyed, use the `base` texture. If not, use the `base_nopattern` texture.
4. Submit the shield model to be rendered, using the provided parameters and texture.
5. If the shield has banner patterns, submit those as well.
6. If the shield is enchanted, submit the enchantment glint.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#submit

### Unbaked Models {#unbaked-models}

You'll also need an [unbaked model](https://docs.neoforged.net/docs/resources/client/models/modelsystem), used to reference the model renderer and provide the sprites to the model.

<<< @/reference/latest/src/client/java/com/example/docs/item/shield/GuiditeShieldSpecialRenderer.java#unbaked

## Creating the Model {#model}

::: info PREREQUISITES

For more information, see the documentation on generating [item models](../data-generation/item-models).

:::

We'll be creating two item models - one for the normal state, and one for when the shield is blocking - and a conditional client item for the shield, with our custom textures:

:::: tabs

== Source Code

::: info

These models are data-generated. For more information, see the documentation on generating [item models](../data-generation/item-models).

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

<DownloadEntry visualURL="/assets/develop/items/guidite_shield_base_hd.png" downloadURL="/assets/develop/items/guidite_shield_base.png">Guidite Shield Base Texture</DownloadEntry>

<DownloadEntry visualURL="/assets/develop/items/guidite_shield_base_nopattern_hd.png" downloadURL="/assets/develop/items/guidite_shield_base_nopattern.png">Guidite Shield Base (No Banner Patterns) Texture</DownloadEntry>

::::

## Creating the Decorated Shield Recipe {#recipe}

::: info PREREQUISITES

For more information, see the documentation on generating [recipes](../data-generation/recipes).

:::

There are two ways to obtain our shield in survival: either crafting a normal shield, or decorating one with banner patterns.

The crafting recipe for the base shield can be whatever you want. On the other hand, the decorated shield recipe can be created in the recipe provider like this:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#shield_decoration

With this recipe defined, you can now put banner patterns on your shield:

![Applying banner patterns in the crafting grid](/assets/develop/items/shield_banner_example.png)

## Tagging Shield Items {#tags}

::: info PREREQUISITES

For more information, see the documentation on generating [item tags](../data-generation/tags).

:::

You should also place your shield in the appropriate item tags:

- `ItemTags.DURABILITY_ENCHANTABLE`, to allow it to be enchanted with Mending and Unbreaking,
- `ConventionalItemTags.SHIELD_TOOLS`, which can be used by modders for shield-specific behavior, like custom shield enchantments.

In your item tag provider, add the following lines to `addTags`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#shield_tags

That's pretty much it! If you go in-game you should see your shield in the "Combat" tab of the creative inventory menu.

![Finished shield in-game](/assets/develop/items/shield_use.png)
