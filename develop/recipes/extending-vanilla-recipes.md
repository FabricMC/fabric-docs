---
title: Extending Vanilla Recipes
description: Learn how to make custom recipes for pre-existing workstations.
authors:
  - ekulxam
resources:
  https://docs.neoforged.net/docs/resources/server/recipes/builtin/: Built-In Recipe Types - NeoForge Docs
---

If you are attempting to add a recipe to an existing workstation, such as a Smithing Table, Crafting Table, or Stonecutter, you likely only need to [create a recipe class](./custom-recipe-types#creating-the-recipe-class), [implement its methods](./custom-recipe-types#implementing-the-methods), [register the serializer](./custom-recipe-types#creating-a-recipe-serializer), and [create the recipe JSON(s)](./custom-recipe-types#creating-a-recipe), as the Block, Menu, and Screen logic have all been completed for you (by Mojang).  Let's take a look at some examples.

## Overview {#overview}

Each Vanilla workstation has its own `RecipeType`, defined in the `RecipeType` interface. Each workstation expects a certain subtype of `Recipe` to function.

::: warning

Note that unless you modify the underlying menu, your recipes are limited by the inputs and outputs that the menu has to offer. For example, a Smithing Table has three inputs and one output (in Vanilla, these are usually an `Optional<Ingredient> template`, `Ingredient base`, `Optional<Ingredient> addition`, and `ItemStackTemplate result`). However, within the `Recipe` class, you have a lot of freedom in configuring the inputs to make the outputs.

:::

## Smithing Table {#smithing-table}

Let's create a new type of smithing recipe that applies enchantments to the base input to produce the output.

The Smithing Table expects any implementation of the `SmithingRecipe` interface, which returns `RecipeTypes.SMITHING`. When making a new `SmithingRecipe`, one might simply create a new class and implement `SmithingRecipe`, but another valid way is to extend `SimpleSmithingRecipe`, a vanilla class, which already implements `SmithingRecipe`.

<<< @/reference/latest/src/main/java/com/example/docs/recipe/extending/EnchantingSmithingRecipe.java#enchanting_smithing

Wow, a lot of text again. This seems to be a common reoccurrence in the recipe docs (haha). Let's try to figure out what's going on.

The first few lines include our `Codec`s, `MapCodec`s, and `StreamCodec`s for serializing and syncing the recipe's details. We use an `Object2IntOpenHashMap` for the enchantments so we can map arbitrary enchantments to a level.

Following the serialization section, we have the aforementioned `template`, `base`, and `addition`, but instead of an `ItemStackTemplate result`, we have an `Object2IntOpenHashMap enchantments`.

The `assemble` method is the core of the custom recipe, and provides the output `ItemStack` for when the recipe is crafted. In this case, we use a helper method from `EnchantmentHelper` to apply the enchantments from our map.

Our `PlacementInfo` mainly assists in recipe placing via the Recipe Book, while our `RecipeDisplay` helps display the recipes in the Recipe Book.

::: info An Aside: Slot Displays

If you tried to make your own override of `display`, you would quickly notice that you wouldn't be able to make a `SlotDisplay` of your result, because you have a dynamic result based off your `base`, which is an `Ingredient` that you can't easily get `ItemStack`s from. However, we have given a valid override of `display` in our recipe class. What's going on?

<<< @/reference/latest/src/main/java/com/example/docs/recipe/extending/EnchantingSmithingDemoSlotDisplay.java#enchanting_smithing

We have created a custom implementation of `SlotDisplay`. This particular implementation allows for displaying the result with the desired enchantments.

In our `resolve` method, we first create a `RandomSource` and a `BinaryOperator<ItemStack>`, then we pass both into `SlotDisplay.applyDemoTransformation` (it's static but private so we need a mixin invoker).

<<< @/reference/latest/src/main/java/com/example/docs/mixin/SlotDisplayAccessor.java#demo_invoker

`applyDemoTransformation` allows for applying changes to the `ItemStack` being displayed in the `SlotDisplay`. It takes a `BinaryOperator<ItemStack>` so one can modify the `base`'s data based on the `material`. This is useful for recipes such as trim recipes, where the trim color of the result varies depending on the material. However, we directly apply our enchantments to the base stack while ignoring the material (the recipe simply checks if the correct material is present before allowing the assembly), so we can actually omit the `material` field in our `SlotDisplay` implementation (`SlotDisplay.Empty.INSTANCE` would then be passed into `applyDemoTransformation` in place of the `material`).

:::

Finally, we need to register our recipe serializer and slot display type.

<<< @/reference/latest/src/main/java/com/example/docs/recipe/ExampleModRecipes.java#enchanting_smithing_registration

## Crafting Table {#crafting-table}

A similar situation occurs when making a new crafting recipe. The expected type is the interface `CraftingRecipe`, and if `ShapedRecipe` and `ShapelessRecipe` aren't enough, then we recommend you extend `CustomRecipe` instead. We encourage you to look through the subtypes of the recipe interface of your target workstation to see if you can find one that fits your needs.

## Stonecutter {#stonecutter}

Stonecutter recipes are separated from other `Recipe`s in the `RecipeManager`/`RecipeAccess` because the Stonecutter needs to display and select any/all of its valid recipes given its one input (menus with recipe books are handled through `ClientRecipeBook`, where the server gives the necessary recipes to the client). Simply extending `StonecutterRecipe` (unlike the others, this is not an interface!) and overriding the `assemble` method should work for most use cases outside just making a stonecutting recipe JSON.

