---
title: Recipe Generation
description: A guide to setting up recipe generation with datagen.
authors:
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - mcrafterzz
  - jmanc3
---

# Recipe Generation {#recipe-generation}

::: info PREQUISITES
Make sure you've completed the [datagen setup](./setup) process first.
:::

## Setup {#setup}

First, we'll need our provider. Make a class that `extends FabricRecipeProvider`. All our recipe generation will happen inside the `generate` method of our provider.

@[code lang=java transcludeWith=:::datagen-recipes:provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceRecipeProvider.java)

And add it to your pack in your generator:

@[code lang=java transcludeWith=:::datagen-recipes:generator](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceRecipeGenerator.java)

## Shapeless Recipes {#shapeless-recipes}

Shapeless recipes are fairly straightforward. Just add them to the `generate` method in your provider:

@[code lang=java transcludeWith=:::datagen-recipes:shapeless](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceRecipeProvider.java)

## Shaped Recipes {#shaped-recipes}

For a shaped recipe, you define the shape using a `String`, then define what each `char` in the `String` represents.

@[code lang=java transcludeWith=:::datagen-recipes:shaped](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceRecipeProvider.java)

::: tip
There's a lot of helper methods for creating common recipes. Check out what `RecipeProvider` has to offer! Use `Alt + 7` in IntelliJ to open the structure of a class, including a method list.
:::

## Other Recipes {#other-recipes}

Other recipes work similarly, but require a few extra parameters. For example, smelting recipes need to know how much experience to award.

@[code lang=java transcludeWith=:::datagen-recipes:other](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceRecipeProvider.java)

## Custom Recipe Types {#custom-recipe-types}

