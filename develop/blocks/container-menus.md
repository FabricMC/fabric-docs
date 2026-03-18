---
title: Container Menus
description: A guide explaining how to create a simple menu for a container block.
authors:
  - CelDaemon
  - Tenneb22
resources:
  https://docs.neoforged.net/docs/inventories/menus: Menus - NeoForge Docs
---

<!---->

::: info PREREQUISITES

You should first read [Block Containers](./block-containers) to familiarize yourself with creating a container block entity.

:::

When opening a container, like a chest, mainly two things are needed to display the contents of it:

- a `Screen` which handles rendering the contents and background onto the display.
- a `Menu` that handles shift-clicking logic and syncing between the server and client.

In this guide, we will create a dirt chest with a 3x3 container that can be accessed by right-clicking and opening a screen.

## Creating the Block {#creating-the-block}

First, we want to create a block and block entity; read more in the [Block Containers](./block-containers#creating-the-block) guide.

@[code transcludeWith=:::block](@/reference/latest/src/main/java/com/example/docs/block/custom/DirtChestBlock.java)

@[code transcludeWith=:::be](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java)

### Opening the Menu {#opening-the-screen}

We want to be able to open the menu somehow, so we will handle that within the `useWithoutItem` method:

@[code transcludeWith=:::use](@/reference/latest/src/main/java/com/example/docs/block/custom/DirtChestBlock.java)

### Implementing MenuProvider {#implementing-menuprovider}

To add the menu functionality, we now need to implement `MenuProvider` in the block entity:

@[code transcludeWith=:::menu](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java)

The `getDisplayName` method returns the name of the block, which will be displayed at the top of the screen.

## Creating the Menu {#creating-the-menu}

`createMenu` wants us to return a menu, but we haven't created one for our block yet. To do this, we'll create a `DirtChestMenu` class which extends `AbstractContainerMenu`:

@[code transcludeWith=:::menu](@/reference/latest/src/main/java/com/example/docs/menu/custom/DirtChestMenu.java)

The client-side constructor gets called on the client when the server wants it to open a menu. It creates an empty container which then gets automatically synced with the actual container on the server.

The server-side constructor is called on the server, and because it knows the contents of the container, it can directly pass it as an argument.

`quickMoveStack` handles shift-clicking items within the menu. This example replicates the behavior of vanilla menus like chests and dispensers.

Then we need to register the menu in a new `ModMenuType` class:

@[code transcludeWith=:::registerMenu](@/reference/latest/src/main/java/com/example/docs/menu/ModMenuType.java)

We can now set the return value of `createMenu` in the block entity to use our menu:

@[code transcludeWith=:::providerImplemented](@/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java)

::: info

The `createMenu` method only gets called on the server, so we call the server-side constructor and pass in `this` (the block entity) as the container parameter.

:::

## Creating the Screen {#creating-the-screen}

To actually display the contents of the container on the client, we also need to create a screen for our menu.
We'll make a new class which extends `AbstractContainerScreen`:

@[code transcludeWith=:::screen](@/reference/latest/src/client/java/com/example/docs/rendering/screens/inventory/DirtChestScreen.java)

For this screen's background, we're just using the default Dispenser screen texture, because our dirt chest uses the same slot layout. You could alternatively provide your own texture for `CONTAINER_TEXTURE`.

Because this is a screen for a menu, we also need to register it on the client with the `MenuScreens#register()` method:

@[code transcludeWith=:::registerScreens](@/reference/latest/src/client/java/com/example/docs/ExampleModScreens.java)

Upon loading your game, you should now have a dirt chest which you can right-click to open a menu and store items in.

![Dirt Chest Menu in game](/assets/develop/blocks/container_menus_0.png)
