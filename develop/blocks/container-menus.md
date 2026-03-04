---
title: Container Menus
description: A guide explaining how to create a simple container menu for a container block.
authors:
  - Tenneb22
---

::: info PREREQUISITES

You should first read [Block Containers](./container-menus.md) to familiarize yourself with creating a container block entity.

:::

When opening for example a chest, mainly two things are needed to display the contents of it:
- a `Screen` which handles rendering the items and background onto the display.
- a `Menu` that handles the container logic and syncing between the server and client.

In this guide, we will create a dirt chest with a 3x3 container that can be accessed via a screen.

## Creating the Block {#creating-the-block}

First, we want to create a block and block entity corresponding to the [Block Containers](./container-menus.md) guide.

@[code transcludeWith=:::block](@/reference/latest/src/main/java/com/example/docs/block/custom/DirtChestBlock.java)

@[code transcludeWith=:::be](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java)

::: info Note
As we want a 3x3 container, we need to set the size of items to 9.
:::

### Opening the Screen
We somehow want to open the screen later, so we will handle that within the `useWithoutItem` method.

@[code transcludeWith=:::use](@/reference/latest/src/main/java/com/example/docs/block/custom/DirtChestBlock.java)


### Implementing MenuProvider {#implementing-menuprovider}

For adding the menu functionality, we now need to implement `MenuProvider` in the block entity.

@[code transcludeWith=:::menu](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java)

The `getDisplayName` method returns the name of the block, which will be displayed at the top of the screen.

`createMenu` wants us to return a menu, but we haven't created one for our block yet, so let's do that.


## Creating the Menu {#creating-the-menu}

We'll create a new class called `DirtChestMenu`, which extends `AbstractContainerMenu`.

@[code transcludeWith=:::menu](@/reference/latest/src/main/java/com/example/docs/menu/custom/DirtChestMenu.java)

Then we need to register the menu in a new `ModMenuType` class as following:

@[code transcludeWith=:::registerMenu](@/reference/latest/src/main/java/com/example/docs/menu/ModMenuType.java)

We can now set the return value of `createMenu` in our block entity to use our menu:

@[code transcludeWith=:::providerImplemented](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java)

## Creating the Screen {#creating-the-screen}

To actually display the container on the client, we also need to create a Screen for our menu.
We'll make a new class which extends `AbstractContainerScreen`.

@[code transcludeWith=:::screen](@/reference/latest/src/client/java/com/example/docs/rendering/screens/inventory/DirtChestScreen.java)

For this screen, we just use the default Dispenser texture for the background, because our dirt chest uses the same layout. You can also optionally use your own texture for the `CONTAINER_TEXTURE`.

We also need to register this screen on the client with the `MenuScreens.register()` method.

@[code transcludeWith=:::registerScreens](@/reference/latest/src/client/java/com/example/docs/ExampleModScreens.java)

Upon loading your game, you should now have a dirt chest which you can right click to open a menu and store items in.

![Dirt Chest Menu in game](/assets/develop/blocks/container_menus_0.png)
