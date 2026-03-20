---
title: Custom Recipe Types
description: Learn how to create a custom recipe type.
authors:
  - skippyall
---

Custom recipe types are a way to create data-driven recipes for your mod's crafting mechanics. As an example, we will create a recipe type for an upgrader block.

## Creating a Recipe Input Class {#creating-your-recipe-input-class}

Before you can start creating our recipe, you need an implementation of `RecipeInput` that can hold the input items in our block's inventory. Our upgrading recipe should have two input items - a base item that should be upgraded and the upgrade itself.

@[code transcludeWith=:::recipeInput](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipeInput.java)

## Creating the Recipe Class {#creating-the-recipe-class}

Now that we have a way to store the input items, we can create our `Recipe` implementation. Implementations of this class represent a singular recipe defined in a datapack. They are responsible for checking the ingredients and requirements of the recipe and applying it.

Let's start by defining the result and the ingredients of the recipe.

@[code transcludeWith=:::baseClass](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java)

Notice how we're using `Ingredient` objects for our inputs. This allows our recipe to accept multiple items interchangeably.

## Implementing the Methods {#implementing-the-methods}

Next, let's implement the methods from the recipe interface. The interesting ones are `matches` and `assemble`. The `matches` method tests if the input items from our `RecipeInput` implementation match our ingredients. The `assemble` method then creates the resulting `ItemStack`.

For testing if the ingredients match, we can use the `test` method of our ingredients.

@[code transcludeWith=:::implementing](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java)

## Creating a Recipe Serializer {#creating-a-recipe-serializer}

The recipe serializer uses a [MapCodec](codecs/#mapcodec) to read the recipe from JSON and a `StreamCodec` to send it over the network.

We'll use `RecordCodecBuilder#mapCodec` to build a map codec for our recipe. It allows combining Minecraft's existing codecs into your own codec.

@[code transcludeWith=:::mapCodec](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipeSerializer.java)

The stream codec can be created in a similar way using `StreamCodec#composite`.

@[code transcludeWith=:::streamCodec](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipeSerializer.java)

Let's use these codecs to implement the methods from `RecipeSerializer`

@[code transcludeWith=:::implementing](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipeSerializer.java)

Now we'll register the recipe serializer as well as a recipe type and a recipe book entry. You can do this in your mod's initializer or in a separate class with a method invoked by your mod's initializer.

@[code transcludeWith=:::registration](@/reference/latest/src/main/java/com/example/docs/recipe/ExampleModRecipes.java)

Back to our recipe class, we can now add the methods that return the objects we just registered.

@[code transcludeWith=:::implementRegistryObjects](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java)

To complete our custom recipe type, we just need to implement the remaining `placementInfo` method, which is used by the recipe book to place our recipe in a screen. For now, we'll just return `PlacementInfo.NOT_PLACEABLE`. We'll also override `isSpecial` to return true to prevent some other recipe book related logic.

@[code transcludeWith=:::recipeBook](@/reference/latest/src/main/java/com/example/docs/recipe/UpgradingRecipe.java)

## Creating a Recipe {#creating-a-recipe}

Our recipe type is now working, but we are still missing two important things: A recipe for our recipe type and a way to craft it. First, let's create a recipe. In your `resources` folder, create a file in `data/<your mod id>/recipe` with `.json` as the file name extension. Each recipe json file has a `"type"` key referring to the recipe serializer of the recipe. The other keys are defined by the codec of that recipe serializer. In our case, a valid recipe file looks like this:

@[code](@/reference/latest/src/main/resources/data/example-mod/recipe/upgrading/diamond_pickaxe.json)

## Creating a Menu {#creating-a-menu}

To craft our recipe, we will create a block with a [Menu](blocks/container-menus).

@[code transcludeWith=:::menu](@/reference/latest/src/main/java/com/example/docs/menu/custom/UpgradingMenu.java)

This menu has two input slot and an output slot. The input container is an anonymous subclass of `SimpleContainer` which calls the menu's `slotsChanged` method when its items change. We then create an instance of our recipe input class and pass it to the `getRecipeFor` method of the `RecipeManager` to get a recipe that matches the inputs. After checking if a recipe was found, we can add or remove the result from the result container.

To detect when the user takes the result, we create an anonymous subclass of `Slot`. The `onTake` method of our menu then removes the input items.

To prevent deleting items, it is important to give back the inputs when the screen is closed, as shown in the `removed` method.
