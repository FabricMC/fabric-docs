---
title: Painting Variant
description: A guide to adding paintings with datagen.
authors:
  - NotNightSky
  - its-miroma
  - ChampionAsh5357
---

<!---->

::: info PREREQUISITES

Make sure you've completed the [datagen setup](./setup) process first.

:::

Paintings are decorative items that players can place on walls.

Each painting is defined by a painting variant, which specifies its dimensions, texture asset, and optionally a title and author.

This guide covers how to add new painting variants with data generation and how to make them placeable in-game.

## Setup {#setup}

First, we will need to create two providers:

::: tabs

== Painting Variant Provider

We will be adding all of our painting variants here:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModPaintingVariantProvider.java#setup

== Painting Tag Provider

We will be adding all of our painting tags here:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModPaintingTagProvider.java#setup

:::

Remember to add them all to your pack in your `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_painting_register

And add the `ExampleModPaintingVariantProvider`'s `bootstrap` method within the `buildRegistry` method:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_painting_bootstrap

## Adding a Painting Variant and Tag {#adding-a-painting-variant-and-tag}

In our `ExampleModPaintingVariantProvider`, we will make a new resource key for the painting variant:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModPaintingVariantProvider.java#resource_key

Now, we will need to create a new painting variant inside the `bootstrap` method:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModPaintingVariantProvider.java#variant

Here, the parameters width and height are in blocks (1–16), the identifier is the asset id used to locate the texture, and title/author are `Optional<Component>`.

After creating the variant we will need to register it into the `bootstrap`'s context:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModPaintingVariantProvider.java#register

Finally, in our `ExampleModPaintingTagProvider` we will add our variant to `PaintingVariantTags.PLACEABLE` tag. This tag is required for the painting to be placed with the Painting item, and it's also what adds the variant to the creative inventory.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModPaintingTagProvider.java#builder_entry

Now, simply run data generation, and your new painting variant will be available in-game without texture that is.

## Assets {#assets}

The texture of the variant is scaled to fill the painting size, so its aspect ratio must match `width:height` (for example, a 1×1 variant needs a 1:1 texture, or it will look stretched).

Now, after creating the painting variant, we will be adding the painting variant texture in `assets/example-mod/textures/painting/` directory with the same name as the asset id, which in this case will be `miroma.png`:

Example texture:

<DownloadEntry visualURL="/assets/develop/data-generation/miroma.png" downloadURL="/assets/develop/data-generation/miroma.png">Variant Texture</DownloadEntry>

In-game, the painting variant can be obtained by either cycling though all the variants or searching the painting title in the creative menu.

![Painting Variant](/assets/develop/data-generation/painting.png)

<!---->
