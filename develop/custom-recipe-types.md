---
title: Custom Recipe Types
description: Learn how to create a custom recipe type.
authors:
  - skippyall
---

Custom recipe types are a way to create data-driven recipes for your mod's crafting mechanics. As an example, we will create a recipe type for an upgrader block.

## Creating a Recipe Input Class {#creating-your-recipe-input-class}

Before you can start creating our recipe, you need an implementation of `RecipeInput` that can hold the input items in our block's inventory. Our upgrading recipe should have two input items - a base item that should be upgraded and the upgrade itself.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipeInput.java)

## Creating the Recipe Class {#creating-the-recipe-class}

Now that we have a way to store the input items, we can create our `Recipe` implementation. Implementations of this class represent a singular recipe defined in a datapack. They are responsible for checking the ingredients and requirements of the recipe and applying it.

Let's start by defining the result and the ingredients of the recipe.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java)

Notice how we're using `Ingredient` objects for our inputs. This allows our recipe to accept multiple items interchangeably.

## Implementing the Methods {#implementing-the-methods}

Next, let's implement the methods from the recipe interface. The interesting ones are `matches` and `assemble`. The `matches` method tests if the input items from our `RecipeInput` implementation match our ingredients. The `assemble` method then creates the resulting `ItemStack`.

For testing if the ingredients match, we can use the `test` method of our ingredients.

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java)

## Creating a Recipe Serializer {#creating-a-recipe-serializer}

The recipe serializer uses a [MapCodec](../codecs/#mapcodec) to read the recipe from JSON and a `StreamCodec` to send it over the network.

We'll use `RecordCodecBuilder#mapCodec` to build a map codec for our recipe. It allows combining Minecraft's existing codecs into your own codec.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipeSerializer.java)

The stream codec can be created in a similar way using `StreamCodec#composite`.

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipeSerializer.java)

Now we'll register the recipe serializer as well as a recipe type and a recipe book entry. You can do this in your mod's initializer or in a separate class with a method invoked by your mod's initializer.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/recipe/ExampleModRecipes.java)

Back to our recipe class, we can now the methods that return the objects we just registered.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java)

To complete our custom recipe type, we just need to implement the remaining `placementInfo` method, which is used by the recipe book to place our recipe in a screen. For now, we'll just return `PlacementInfo.NOT_PLACEABLE`. We'll also override `isSpecial` to return true to prevent some other recipe book related logic.

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java)

Our recipe type is now working, but we are still missing two important things: Recipes and a way to use them. First, let's create a recipe. In your `resources` folder, create a file in `data/<your mod id>/recipe` with `.json` as the file name extension. Each recipe json file has a `"type"` key referring to the recipe serializer of the recipe. The other keys are defined by the codec of that recipe serializer. In our case, a valid recipe file looks like this:

@[code](@/reference/latest/src/main/resources/data/example-mod/recipe/upgrading/diamond_pickaxe.json)
