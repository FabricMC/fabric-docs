---
title: Workstations
description: Learn how to create a workstation.
authors:
  - cassiancc
  - skippyall
  - ekulxam
---

<!---->

::: info PREREQUISITES

This workstation uses a custom recipe type, which can be found in [Custom Recipe Types](../recipes/custom-recipe-types).

:::

This tutorial will provide instructions on how to create a custom workstation. Unlike chests, workstations do not necessarily need to retain their inventory after the UI is closed (blocks such as the Crafting Table do not save their inventory, but other blocks, such as Furnaces, do). For demonstration purposes, we will not be using a BlockEntity.

## Creating a Menu {#creating-a-menu}

::: info

For more details on creating menus, see [Container Menus](./container-menus).

:::

To allow us to craft our recipe in the GUI, we will create a block with a Menu. To open the menu, we will need to override some methods in our `Block` class:

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/UpgradingBlock.java#openmenu

After that, we are ready to create the menu.

<<< @/reference/latest/src/main/java/com/example/docs/menu/custom/UpgradingMenu.java#menu

To go along with this menu, we'll also need a custom result `Slot`.

<<< @/reference/latest/src/main/java/com/example/docs/menu/custom/UpgradingResultSlot.java#slot

A lot to unpack here! This menu has two input slots and an output `UpgradingResultSlot`.

The input container is an anonymous subclass of `SimpleContainer`, which calls the menu's `slotsChanged` method when its items change. In `slotsChanged`, we then create an instance of our recipe input class, filling it with the two input slots.

In order to see if it matches any recipes, we'll first ensure we are on the server level, since clients do not know what recipes exist. Then, we'll retrieve the `RecipeManager` via `serverLevel.recipeAccess()`.

We'll call `serverLevel.recipeAccess().getRecipeFor` with our recipe input to get a recipe that matches the inputs. If a recipe was found, we can add or remove the result from the result container.

To detect when the user takes the result out, we use the `UpgradingResultSlot`'s `onTake` override. The `onTake` method of our menu then decrements the input items.

To ensure that the player is within interaction range from the block, we override `stillValid`.

::: warning

Ensure the `Block` you pass as an argument for `stillValid` is the block opening the menu! If you don't, the menu and screen might open and proceed to close themselves immediately.

:::

Finally, to prevent deleting items, it is important to drop the inputs back when the screen is closed, as shown in the `removed` method.

::: info

You may have noticed that multiple methods contain a `ContainerLevelAccess#execute` call. This a wrapper class used by Mojang to ensure the correct `Level` and position are in use when interactions occur, and prevents players from accessing containers they shouldn't be accessing. Note that the special `NULL` `ContainerLevelAccess` performs no action when `execute` is called on it.

:::

The `mayPlace` method of the `Slot` returns `false` so players cannot insert items into the result slot, and the `isFake` method tells the `Screen` that the stack it contains does not have an owner (yet).

You also need to add the menu to the registry:

<<< @/reference/latest/src/main/java/com/example/docs/recipe/ExampleModRecipes.java#upgrading_menu_registration

### Implementing `quickMoveStack` {#implementing-quick-move-stack}

::: info

See also: [Container Menus: Creating the Menu](./container-menus#creating-the-menu)

:::

Quick Move is called whenever a shift-click is performed in a Menu.

<<< @/reference/latest/src/main/java/com/example/docs/menu/custom/SuperiorUpgradingMenu.java#quickMove

Wow, that's a lot of code again. Let's try thinking through what's happening.

Usually, when quick-moving a stack from the inventory area, the menu first checks if the clicked slot is the result slot (with index 0). If so, the menu tries to move the result stack into the inventory, but if that fails, nothing happens.

Next, the menu checks to see if the slot clicked belongs to the inventory. If so, then the menu tries to move the stack into the inputs. If that failed, we try to move the stack within the inventory (slots clicked in the hotbar move their stacks into the other 27 slots of the inventory, and vice-versa).

If the clicked slot was not the result slot or within the inventory, the slot is then almost guaranteed to have been one of our two input slots, so we would want to move their stack back into the inventory.

### The Screen {#screen}

::: info

See also: [Container Menus: Creating the Screen](./container-menus#creating-the-screen)

:::

For now, we can just borrow the vanilla Anvil's background texture.

<<< @/reference/latest/src/client/java/com/example/docs/rendering/screens/inventory/UpgradingScreen.java#screen

Don't forget to bind your menu type to the screen in your `ClientModInitializer`, like so:

<<< @/reference/latest/src/client/java/com/example/docs/ExampleModRecipesClient.java#register_with_menu

## Recipe Remainders {#recipe-remainders}

Want to make a recipe that supports remainders? We recommend taking a look at `net.minecraft.world.inventory.ResultSlot#getRemainingItems`. The crafting table uses this as its result slot, so many similarities to the docs can be found, but there are also some differences.
