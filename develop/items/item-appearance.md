---
title: Item Appearance
description: Dynamically tinting items with custom tint sources.
authors:
  - dicedpixels
---

An item's appearance can be manipulated through its client item. A full list of vanilla modifications can be found [here in the Minecraft Wiki](https://minecraft.wiki/w/Items_model_definition#Items_model_types).

Out of these, a commonly used type is _Tint Sources_. Tint sources allow you to change the color of the item based on predefined conditions.

There are only a handful of [predefined tint sources](https://minecraft.wiki/w/Items_model_definition#Tint_sources_types). So, let's see how to create our own.

For this example, let's register an item. If you are unfamiliar with this process, please read the [page on item registration](./first-item) first.

@[code lang=java transcludeWith=:::item](@/reference/latest/src/main/java/com/example/docs/appearance/ExampleModAppearance.java)

Make sure to add:

- An [client item](./first-item#creating-the-client-item) (`/items/waxcap.json`)
- An [item model](./item-models) (`/models/item/waxcap.json`)
- A [texture](./first-item#adding-a-texture) (`/textures/item/waxcap.png`)

The item should appear in-game.

![Registered Item](/assets/develop/item-appearance/item_tint_0.png)

As you can see, we're using a grayscale texture. Let's add some color using a tint source.

## Item Tint Sources {#item-tint-sources}

Let's register a custom tint source to color our Waxcap item, so that when it rains, the item will look blue, otherwise it will look brown.

You'll first need to define a custom item tint source. This is done by implementing the `ItemTintSource` interface on a class or a record.

@[code lang=java transcludeWith=:::tint_source](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

Let's look at the code.

As this is a part of the client item definition, it's possible for a tint value to be changed through a resource pack. So you need to define a Map Codec that's capable of reading your tint definition. In our case, our tint source will have a `int` value describing the color it will have when raining. We can use the built-in `ExtraCodecs.RGB_COLOR_CODEC` to compose our Codec.

@[code lang=java transclude={17-20}](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

We can then return this Codec in `type()`.

@[code lang=java transclude={35-38}](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

Finally, we can provide an implementation for `calculate` that would decide what the tint color would be. The current `color` is the color that's read from the resource pack.

@[code lang=java transclude={26-33}](@/reference/latest/src/client/java/com/example/docs/appearance/RainTintSource.java)

We then need to register our item tint source. This is done in the **client initializer** using the `ID_MAPPER` declared in `ItemTintSources`.

@[code lang=java transcludeWith=:::item_tint_source](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

Once this is done, we can use our item tint source in an client item definition.

@[code lang=json transclude](@/reference/latest/src/main/generated/assets/example-mod/items/waxcap.json)

You can observe the item color change in-game.

![Item Tint In Game](/assets/develop/item-appearance/item_tint_1.png)
