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

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_provider

To finish setup, add this provider to your `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_recipes_register

## Shapeless Recipes {#shapeless-recipes}

Shapeless recipes are fairly straightforward. Just add them to the `buildRecipes` method in your provider:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_shapeless

### Dye Recipes {#dye-recipes}

Dye recipes are used to dye items in your inventory.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_dye

## Shaped Recipes {#shaped-recipes}

For a shaped recipe, you define the shape using a `String`, then define what each `char` in the `String` represents.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_shaped

::: tip

There's a lot of helper methods for creating common recipes. Check out what `RecipeProvider` has to offer! Use <kbd>Alt</kbd>+<kbd>7</kbd> in IntelliJ to open the structure of a class, including a method list.

:::

## Other Recipes {#other-recipes}

Other recipes work similarly, but require a few extra parameters. For example, smelting recipes need to know how much experience to award.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_smelting

Smoking is a little different, it does not use the same recipe generator as smelter-like blocks do.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_smoking

## Resource Conditions {#resource-conditions}

To apply a [resource condition](../resource-conditions) to a data generated recipe, wrap the output with `withConditions` and provide any resource conditions you want to apply. This will then generate a recipe and an advancement that has resource conditions applied:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModRecipeProvider.java#datagen_recipes_conditions
