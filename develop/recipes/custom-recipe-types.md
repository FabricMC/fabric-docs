---
title: Custom Recipe Types
description: Learn how to create a custom recipe type.
authors:
  - cassiancc
  - ekulxam
  - skippyall
resources:
  https://docs.neoforged.net/docs/resources/server/recipes/custom/: Custom Recipes - NeoForge Docs
  https://docs.neoforged.net/docs/resources/server/recipes/ingredients/: Ingredients - NeoForge Docs
---

<!---->

::: warning

If you are trying to add a custom recipe type for a Vanilla workstation, please see [Extending Vanilla Recipes](./extending-vanilla-recipes).

:::

Custom recipe types are a way to create data-driven recipes for your mod's custom crafting mechanics. As an example, we will create a recipe type for an upgrader block, similar to a Smithing Table.

## Creating a Recipe Input Class {#creating-your-recipe-input-class}

Before you can start creating our recipe, you need an implementation of `RecipeInput` that can hold the input items in our block's inventory. We want an upgrading recipe to have two input items: a base item to be upgraded, and the upgrade itself.

<<< @/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipeInput.java#recipe_input

::: info

Alternatively, we could replace the two `ItemStack`s with a `List`. This applies to the other classes that use the two `Ingredient`s or `ItemStack`s.

:::

## Creating the Recipe Class {#creating-the-recipe-class}

Now that we have a way to store the input items, we can create our `Recipe` implementation. Implementations of this class represent a singular recipe defined in a datapack. They are responsible for checking the ingredients and requirements of the recipe and assembling it into a result.

Let's start by defining the result and the ingredients of the recipe.

<<< @/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java#base_class

Notice how we're using `Ingredient` objects for our inputs. This allows our recipe to accept multiple items interchangeably. (See also: the Ingredients page from the NeoForge Docs in the resources section.)

## Implementing the Methods {#implementing-the-methods}

Next, let's implement the methods from the recipe interface. The interesting ones are `matches` and `assemble`. The `matches` method tests if the input items from our `RecipeInput` implementation match our ingredients. The `assemble` method then creates the resulting `ItemStack`.

To test if the ingredients match, we can use the `test` method of our ingredients.

<<< @/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java#implementing

## Creating a Recipe Serializer {#creating-a-recipe-serializer}

The recipe serializer uses a [`MapCodec`](../serialization/codecs/#mapcodec) to read the recipe from JSON and a `StreamCodec` to send it over the network.

We'll use `RecordCodecBuilder#mapCodec` to build a map codec for our recipe. It allows us to combine Minecraft's existing codecs into our own:

<<< @/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java#map_codec

The stream codec can be created in a similar way using `StreamCodec#composite`:

<<< @/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java#stream_codec

Now we'll register the recipe serializer as well as a recipe type. You can do this in your mod's initializer, or in a separate class, with a method invoked by your mod's initializer:

<<< @/reference/latest/src/main/java/com/example/docs/recipe/ExampleModRecipes.java#registration

Back to our recipe class, we can now add the methods that return the objects we just registered:

<<< @/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java#implement_registry_objects

To complete our custom recipe type, we just need to implement the remaining `placementInfo`, `showNotification`, `group`, and `recipeBookCategory` methods, which are used by the recipe book to place our recipe in a screen. For now, we'll just return `PlacementInfo.NOT_PLACEABLE` and `null`, as the recipe book cannot be easily expanded to modded workstations. We'll also override `isSpecial` to return true, to prevent some other recipe-book-related logic from running and logging errors.

<<< @/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java#recipe_book

## Creating a Recipe {#creating-a-recipe}

Our recipe type is now working, but we are still missing two important things: a recipe for our recipe type, and a way to craft it.

First, let's create a recipe. In your `resources` folder, create a file in `data/example-mod/recipe`, with file extension `.json`. Each recipe json file has a `"type"` key referring to the recipe serializer of the recipe. The other keys are defined by the codec of that recipe serializer.

In our case, a valid recipe file looks like this:

<<< @/reference/latest/src/main/resources/data/example-mod/recipe/upgrading/diamond_pickaxe.json

## Recipe Synchronization {#recipe-synchronization}

::: info

This section is optional, and only needed if you need clients to know about recipes.

:::

As mentioned earlier, recipes are handled entirely on the logical server. However, in some cases a client may need to know what recipes exist - an example from vanilla is Stonecutters, which have to display the available recipe options for a given ingredient. Also, the plugins of certain recipe viewers, including [JEI](https://modrinth.com/mod/jei), run on the logical client, requiring you to use Fabric's recipe synchronization API.

To synchronize your recipes, simply call `RecipeSynchronization.synchronizeRecipeSerializer` in your mod initializer, and provide your mod's recipe serializer:

<<< @/reference/latest/src/main/java/com/example/docs/recipe/ExampleModRecipes.java#recipe_sync

Once synchronized, recipes can be retrieved at any point from the client level's recipe manager:

<<< @/reference/latest/src/client/java/com/example/docs/ExampleModRecipesClient.java#recipe_sync_client

## Workstations {#workstations}

Want to create a custom workstation to go along with your recipe type? See [Workstations](../blocks/workstations).
