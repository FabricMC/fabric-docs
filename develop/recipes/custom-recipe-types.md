---
title: Custom Recipe Types
description: Learn how to create a custom recipe type.
authors:
  - cassiancc
  - skippyall
---

Custom recipe types are a way to create data-driven recipes for your mod's custom crafting mechanics. As an example, we will create a recipe type for an upgrader block, similar to a Smithing Table.

::: info An Aside: Making Recipes for Vanilla Workstations

If you are attempting to add a recipe to an existing workstation, such as a Smithing Table, Crafting Table, or Stonecutter, you likely only need to [create a recipe class](#creating-the-recipe-class), [implement its methods](#implementing-the-methods), [register the serializer](#creating-a-recipe-serializer), and [create the recipe JSON(s)](#creating-a-recipe), as the Block, Menu, and Screen logic have all been completed for you (by Mojang).

Each Vanilla workstation has its own `RecipeType`, defined in the very same place. Each workstation expects a certain subtype of `Recipe` to function.

For instance, the Smithing Table expects any implementation of the `SmithingRecipe` interface, which returns `RecipeTypes.SMITHING`. When making a new `SmithingRecipe`, one might simply create a new class and implement `SmithingRecipe`, but another valid way is to extend `SimpleSmithingRecipe`, a vanilla class, which already implements `SmithingRecipe`.

A similar situation occurs when making a new crafting recipe. The expected type is the interface `CraftingRecipe`, and if `ShapedRecipe` and `ShapelessRecipe` aren't enough, then we recommend you extend `CustomRecipe` instead. We encourage you to look through the subtypes of the recipe interface of your target workstation to see if you can find one that fits your needs.

Stonecutter recipes are separated from other `Recipe`s in the `RecipeManager`/`RecipeAccess` because the Stonecutter needs to display and select any/all of its valid recipes given its one input (menus with recipe books are handled through `ClientRecipeBook`, where the server gives the necessary recipes to the client). Simply extending `StonecutterRecipe` (unlike the others, this is not an interface!) and overriding the `assemble` method should work for most use cases outside just making a stonecutting recipe JSON.

Note that unless you modify the underlying menu, your recipes are limited by the inputs and outputs that the menu has to offer. For example, a Smithing Table has three inputs and one output (in Vanilla, these are usually an `Optional<Ingredient> template`, `Ingredient base`, `Optional<Ingredient> addition`, and `ItemStackTemplate result`). However, within the `Recipe` class, you have a lot of freedom in configuring the inputs to make the outputs. A few ideas (for smithing recipes) include changing the `Optional<Ingredient>`s to `Ingredient`s (which would force all JSONs using this recipe type to define all (note that this would remove your ability to make your custom recipe JSONs that omit one or both of the template/addition)) or overriding `assemble` and having your `result` actually be a data component map (to apply arbitrary item components based on the JSON).

Don't forget to create and register a serializer when you're done!

:::

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

Notice how we're using `Ingredient` objects for our inputs. This allows our recipe to accept multiple items interchangeably.

## Implementing the Methods {#implementing-the-methods}

Next, let's implement the methods from the recipe interface. The interesting ones are `matches` and `assemble`. The `matches` method tests if the input items from our `RecipeInput` implementation match our ingredients. The `assemble` method then creates the resulting `ItemStack`.

To test if the ingredients match, we can use the `test` method of our ingredients.

<<< @/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java#implementing

## Creating a Recipe Serializer {#creating-a-recipe-serializer}

The recipe serializer uses a [`MapCodec`](./codecs/#mapcodec) to read the recipe from JSON and a `StreamCodec` to send it over the network.

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

## Workstations

Want to create a custom workstation to go along with your recipe type? See [Workstations](./blocks/workstations).
