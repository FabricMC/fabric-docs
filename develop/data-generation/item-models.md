---
title: Item Model Generation
description: A guide to generating item models via datagen.
authors:
  - CelDaemon
  - Fellteros
  - skycatminepokie
  - VatinMc
---

<!---->

::: info PREREQUISITES

Make sure you've completed the [datagen setup](./setup) and created your [first item](../items/first-item).

:::

For each item model we want to generate, we must create two separate JSON files:

1. An **item model**, which defines the textures, rotation and overall look of the item. It goes in the `generated/assets/example-mod/models/item` directory.
2. A **client item**, which defines which model should be used based on various criteria, such as components, interactions and more. It goes in the `generated/assets/example-mod/items` directory.

## Setup {#setup}

First, we will need to create our model provider.

::: tip

You can reuse the `FabricModelProvider` created in [Block Model Generation](./block-models#setup).

:::

Create a class that extends `FabricModelProvider`, and implement both abstract methods: `generateBlockStateModels` and `generateItemModels`.
Then, create a constructor matching `super`.

@[code transcludeWith=:::provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Register this class in your `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method.

@[code transcludeWith=:::datagen-models:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Built-In Item Models {#built-in}

For item models, we will be using the `generateItemModels` method. Its parameter `ItemModelGenerators itemModelGenerator` is responsible for generating the item models and also contains methods for doing so.

Here's a reference of the most commonly used item model generator methods.

### Simple {#simple}

Simple item models are the default, and they're what most Minecraft items use. Their parent model is `GENERATED`. They use their 2D texture in the inventory, and are rendered in 3D in-game. An example would be boats, candles or dyes.

::: tabs

== Source Code

@[code transcludeWith=:::generated](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== Client Item

`generated/assets/example-mod/items/ruby.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/ruby.json)

== Item Model

`generated/assets/example-mod/models/item/ruby.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/ruby.json)

You can find the exact default values for rotation, scaling and positioning of the model in the [`generated.json` file from the Minecraft assets](https://mcasset.cloud/1.21.11/assets/minecraft/models/item/generated.json).

== Texture

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/ruby_big.png" downloadURL="/assets/develop/data-generation/item-model/ruby.png">Ruby Texture</DownloadEntry>

:::

### Handheld {#handheld}

Handheld item models are generally used by tools and weapons (axes, swords, trident). They are rotated and positioned a little differently from the simple models to look more natural in hand.

::: tabs

== Source Code

@[code transcludeWith=:::handheld](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== Client Item

`generated/assets/example-mod/items/guidite_axe.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/guidite_axe.json)

== Item Model

`generated/assets/example-mod/models/item/guidite_axe.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/guidite_axe.json)

You can find the exact default values for rotation, scaling and positioning of the model in the [`handheld.json` file from the Minecraft assets](https://mcasset.cloud/1.21.11/assets/minecraft/models/item/handheld.json).

== Texture

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/guidite_axe_big.png" downloadURL="/assets/develop/data-generation/item-model/guidite_axe.png">Guidite Axe Texture</DownloadEntry>

:::

### Dyeable {#dyeable}

The method for dyeable items generates a simple item model and a client item which specifies the tint color. This method requires a default decimal color value, which is used when the item is not dyed. The default value for leather is `0xFFA06540`.

:::: tabs

== Source Code

@[code transcludeWith=:::dyeable](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

::: warning IMPORTANT

You have to add your item to the `ItemTags.DYEABLE` tag to be able to dye it in your inventory!

:::

== Client Item

`generated/assets/example-mod/items/leather_gloves.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/leather_gloves.json)

== Item Model

`generated/assets/example-mod/models/item/leather_gloves.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/leather_gloves.json)

== Texture

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/leather_gloves_big.png" downloadURL="/assets/develop/data-generation/item-model/leather_gloves.png">Leather Gloves Texture</DownloadEntry>

== Preview

![Dyeing leather gloves](/assets/develop/data-generation/item-model/leather_gloves_dyeing.png)

::::

### Conditional {#conditional}

Next, we'll look into generating item models that change their visual based when a specific condition, indicated by the second parameter `BooleanProperty`, is met. Here are some of them:

| Property        | Description                                                                    |
| --------------- | ------------------------------------------------------------------------------ |
| `IsKeybindDown` | True when a specified key is pressed.                                          |
| `IsUsingItem`   | True when the item is being used (e.g. when blocking with a shield).           |
| `Broken`        | True when the item has 0 durability (e.g. elytra changes texture when broken). |
| `HasComponent`  | True when the item has a certain component.                                    |

The third and fourth parameters are the models to be used when the property is `true` or `false`, respectively.

:::: tabs

== Source Code

@[code transcludeWith=:::condition](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

::: warning IMPORTANT

To obtain the `Identifier` that is passed in `ItemModelUtils.plainModel()`, always use `itemModelGenerator.createFlatItemModel()`, otherwise only the client items will be generated, not the item models!

:::

== Client Item

`generated/assets/example-mod/items/flashlight.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/flashlight.json)

== Item Models

`generated/assets/example-mod/models/item/flashlight.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/flashlight.json)

`generated/assets/example-mod/models/item/flashlight_lit.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/flashlight_lit.json)

== Textures

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/flashlight_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/flashlight_textures.zip">Flashlight Textures</DownloadEntry>

== Preview

<VideoPlayer src="/assets/develop/data-generation/item-model/flashlight_turning_on.webm">Flashlight turning on and off</VideoPlayer>

::::

### Composite {#composite}

Composite item models are composed of one or more textures layered on top of each other. There aren't any vanilla methods for this; you have to use the `itemModelGenerator`'s `itemModelOutput` field, and call `accept()` on it.

::: tabs

== Source Code

@[code transcludeWith=:::composite](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== Client Item

`generated/assets/example-mod/items/enhanced_hoe.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/enhanced_hoe.json)

== Item Models

`generated/assets/example-mod/models/item/enhanced_hoe.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/enhanced_hoe.json)

`generated/assets/example-mod/models/item/enhanced_hoe_plus.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/enhanced_hoe_plus.json)

== Textures

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/enhanced_hoe_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/enhanced_hoe_textures.zip">Enhanced Hoe Textures</DownloadEntry>

:::

### Select {#select}

Renders an item model based on the value of a specific property. These are some of them:

| Property            | Description                                                                                   |
| ------------------- | --------------------------------------------------------------------------------------------- |
| `ContextDimension`  | Renders an item model based on the dimension in which the player is (Overworld, Nether, End). |
| `MainHand`          | Renders an item model when the item is equipped in player's main hand.                        |
| `DisplayContext`    | Renders an item model based on where the item is (`ground`, `fixed`, `head`, ...).            |
| `ContextEntityType` | Renders an item model based on the entity holding the item.                                   |

In this example, the item changes texture when traveling between dimensions: it's green in the Overworld, red in the Nether, and black in the End.

::: tabs

== Source Code

@[code transcludeWith=:::select](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== Client Item

`generated/assets/example-mod/items/dimensional_crystal.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/dimensional_crystal.json)

== Item Models

`generated/assets/example-mod/models/item/dimensional_crystal_overworld.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/dimensional_crystal_overworld.json)

`generated/assets/example-mod/models/item/dimensional_crystal_nether.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/dimensional_crystal_nether.json)

`generated/assets/example-mod/models/item/dimensional_crystal_end.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/dimensional_crystal_end.json)

== Textures

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/dimensional_crystal_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/dimensional_crystal_textures.zip">Dimensional Crystal Textures</DownloadEntry>

== Preview

![Dimensional crystal changing texture based on dimension](/assets/develop/data-generation/item-model/crystal.png)

:::

### Range Dispatch {#range-dispatch}

Renders an item model based on the value of a numeric property. Takes in an item and list of variants, each paired with a value. Examples include the compass, the bow, and the brush.

There are quite a few supported properties, here are some examples:

| Property      | Description                                                                  |
| ------------- | ---------------------------------------------------------------------------- |
| `Cooldown`    | Renders an item model based on the item's remaining cooldown.                |
| `Count`       | Renders an item model based on the stack size.                               |
| `UseDuration` | Renders an item model based on how long the item is being used.              |
| `Damage`      | Renders an item model based on attack damage (`minecraft:damage` component). |

This example uses the `Count`, changing the texture from one knife up to three based on the stack size.

::: tabs

== Source Code

@[code transcludeWith=:::range-dispatch](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

== Client Item

`generated/assets/example-mod/items/throwing_knives.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/throwing_knives.json)

== Item Models

`generated/assets/example-mod/models/item/throwing_knives_one.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/throwing_knives_one.json)

`generated/assets/example-mod/models/item/throwing_knives_two.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/throwing_knives_two.json)

`generated/assets/example-mod/models/item/throwing_knives_three.json`

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/throwing_knives_three.json)

== Textures

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/throwing_knives_textures_big.png" downloadURL="/assets/develop/data-generation/item-model/throwing_knives_textures.zip">Throwing Knives Textures</DownloadEntry>

== Preview

![Throwing knives changing texture based on count](/assets/develop/data-generation/item-model/throwing_knives_example.png)

:::

## Custom Item Models {#custom}

Generating item models doesn't have to be done with vanilla methods only; you can, of course, create your own. In this section, we will create a custom model for a balloon item.

All fields and methods for this part of the tutorial are declared in a static inner class called `CustomItemModelGenerator`.

::: details Show `CustomItemModelGenerator`

@[code transcludeWith=:::custom-item-model-generator:::](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

:::

### Creating a Custom Parent {#custom-parent}

First, let's create a parent item model that defines how the item looks in-game. Let's say we want the balloon to look like simple item models, but scaled up.

To do this, we'll create `resources/assets/example-mod/models/item/scaled2x.json`, set the parent to be the `item/generated` model, and then override the scaling.

@[code](@/reference/latest/src/main/resources/assets/example-mod/models/item/scaled2x.json)

This will make the model visually twice as big as the simple ones.

### Creating the `ModelTemplate` {#custom-item-model}

Next, we need to create an instance of the `ModelTemplate` class. It will represent the actual [parent item model](#custom-parent) inside our mod.

@[code transcludeWith=:::custom-item-model:::](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

The `item()` method creates a new `ModelTemplate` instance, pointing to the `scaled2x.json` file we created earlier.

TextureSlot `LAYER0` represents the `#layer0` texture variable, which will then be replaced by an identifier pointing to a texture.

### Adding a Custom Datagen Method {#custom-datagen-method}

The last step is creating a custom method, which will be called in the `generateItemModels()` method and will be responsible for generating our item models.

@[code transcludeWith=:::custom-item-datagen-method](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Let's go over what the parameters are for:

1. `Item item`: The item, for which we are generating the models.
2. `ItemModelGenerators generator`: the same that get passed into the `generateItemModels()` method. Used for its fields.

First, we get the `Identifier` of the item with `SCALED2X.create()`, passing in a `TextureMapping` and the `modelOutput` from our `generator` parameter.

Then, we'll use another of its fields, the `itemModelOutput` (which essentially works as a consumer), and use the `accept()` method, so that the models are actually generated.

### Calling the Custom Method {#custom-call}

Now, we only need to call our method in the `generateItemModels()` method.

@[code transcludeWith=:::custom-balloon](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Don't forget to add a texture file!

<DownloadEntry visualURL="/assets/develop/data-generation/item-model/balloon_big.png" downloadURL="/assets/develop/data-generation/item-model/balloon.png">Balloon Texture</DownloadEntry>

## Sources and Links {#sources-and-links}

You can view the example tests in [Fabric API](https://github.com/FabricMC/fabric/blob/1.21.11/fabric-data-generation-api-v1/src/), this documentation's [Example Mod](https://github.com/FabricMC/fabric-docs/tree/main/reference) for more information.
