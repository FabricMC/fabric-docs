---
title: Extending Vanilla Recipes
description: Learn how to make custom recipes for pre-existing workstations.
authors:
  - SkyNotTheLimit
---

If you are attempting to add a recipe to an existing workstation, such as a Smithing Table, Crafting Table, or Stonecutter, you likely only need to [create a recipe class](#creating-the-recipe-class), [implement its methods](#implementing-the-methods), [register the serializer](#creating-a-recipe-serializer), and [create the recipe JSON(s)](#creating-a-recipe), as the Block, Menu, and Screen logic have all been completed for you (by Mojang).

Each Vanilla workstation has its own `RecipeType`, defined in the very same place. Each workstation expects a certain subtype of `Recipe` to function.

For instance, the Smithing Table expects any implementation of the `SmithingRecipe` interface, which returns `RecipeTypes.SMITHING`. When making a new `SmithingRecipe`, one might simply create a new class and implement `SmithingRecipe`, but another valid way is to extend `SimpleSmithingRecipe`, a vanilla class, which already implements `SmithingRecipe`.

A similar situation occurs when making a new crafting recipe. The expected type is the interface `CraftingRecipe`, and if `ShapedRecipe` and `ShapelessRecipe` aren't enough, then we recommend you extend `CustomRecipe` instead. We encourage you to look through the subtypes of the recipe interface of your target workstation to see if you can find one that fits your needs.

Stonecutter recipes are separated from other `Recipe`s in the `RecipeManager`/`RecipeAccess` because the Stonecutter needs to display and select any/all of its valid recipes given its one input (menus with recipe books are handled through `ClientRecipeBook`, where the server gives the necessary recipes to the client). Simply extending `StonecutterRecipe` (unlike the others, this is not an interface!) and overriding the `assemble` method should work for most use cases outside just making a stonecutting recipe JSON.

Note that unless you modify the underlying menu, your recipes are limited by the inputs and outputs that the menu has to offer. For example, a Smithing Table has three inputs and one output (in Vanilla, these are usually an `Optional<Ingredient> template`, `Ingredient base`, `Optional<Ingredient> addition`, and `ItemStackTemplate result`). However, within the `Recipe` class, you have a lot of freedom in configuring the inputs to make the outputs. A few ideas (for smithing recipes) include changing the `Optional<Ingredient>`s to `Ingredient`s (which would force all JSONs using this recipe type to define all (note that this would remove your ability to make your custom recipe JSONs that omit one or both of the template/addition)) or overriding `assemble` and having your `result` actually be a data component map (to apply arbitrary item components based on the JSON).

Don't forget to create and register a serializer when you're done!
