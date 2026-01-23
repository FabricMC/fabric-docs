---
title: Recipe Generation
description: A guide to setting up recipe generation with datagen.
authors:
  - CelDaemon
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
---

<!---->

::: info PREREQUISITES

Make sure you've completed the [datagen setup](./setup) process first.

:::

## Setup {#setup}

First, we'll need our provider. Make a class that extends `FabricRecipeProvider`. All our recipe generation will happen inside the `buildRecipes` method of our provider.

@[code lang=java transcludeWith=:::datagen-recipes:provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)

To finish setup, add this provider to your `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method.

@[code lang=java transcludeWith=:::datagen-recipes:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Shapeless Recipes {#shapeless-recipes}

Shapeless recipes are fairly straightforward. Just add them to the `buildRecipes` method in your provider:

@[code lang=java transcludeWith=:::datagen-recipes:shapeless](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)

## Shaped Recipes {#shaped-recipes}

For a shaped recipe, you define the shape using a `String`, then define what each `char` in the `String` represents.

@[code lang=java transcludeWith=:::datagen-recipes:shaped](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)

::: tip

There's a lot of helper methods for creating common recipes. Check out what `RecipeProvider` has to offer! Use <kbd>Alt</kbd>+<kbd>7</kbd> in IntelliJ to open the structure of a class, including a method list.

:::

## Other Recipes {#other-recipes}

Other recipes work similarly, but require a few extra parameters. For example, smelting recipes need to know how much experience to award.

@[code lang=java transcludeWith=:::datagen-recipes:other](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java)
