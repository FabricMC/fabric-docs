---
title: 容器菜单
description: 讲解如何为容器方块创建简单菜单的指南。
authors:
  - bluebear94
  - cassiancc
  - ChampionAsh5357
  - CelDaemon
  - Tenneb22
resources:
  https://docs.neoforged.net/docs/inventories/menus: Menus - NeoForge 文档
---

<!---->

:::info 前置条件

你 应该首先阅读[方块容器](./block-containers)以熟悉如何创建容器方块实体。

:::

打开容器（例如箱子）时，主要需要两样东西才能显示其中的内容：

- 一个负责将内容和背景渲染到显示器上的 `Screen`。
- 一个处理 Shift 点击逻辑以及服务器和客户端之间同步的 `Menu`。

在本指南中，我们将创建一个泥土箱子，其中包含一个 3x3 的容器，可以通过右键单击并打开屏幕来访问容器。

## 创建方块 {#creating-the-block}

首先，我们要创建一个方块和方块实体；请阅读[方块容器](./block-containers#creating-the-block)指南进一步了解。

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DirtChestBlock.java#block

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java#be

除了普通的方块实体方法之外，我们还需要重写 `stillValid` 方法。 此方法将在每个游戏帧调用一次，以检查是否应强制玩家退出菜单。
我们将使用 `ContainerHelper` 中此方法的默认实现，它会检查我们的方块实体是否仍存在以及玩家是否在交互范围内。

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java#container_still_valid

菜单实现后，当玩家被推离时，菜单将自动关闭。

<VideoPlayer src="/assets/develop/blocks/menu_still_valid.webm">当玩家超出范围时容器菜单关闭</VideoPlayer>

### 打开菜单 {#opening-the-screen}

我们希望能够以某种方式打开菜单，因此我们将在 `useWithoutItem` 方法中处理这个问题：

<<< @/reference/latest/src/main/java/com/example/docs/block/custom/DirtChestBlock.java#use

### 实现 MenuProvider {#implementing-menuprovider}

为了添加菜单功能，我们现在需要在方块实体中实现 `MenuProvider`：

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java#menu

`getDisplayName` 方法返回方块的名称，将显示在屏幕顶部。

## 创建菜单 {#creating-the-menu}

`createMenu` 要求我们返回一个菜单，但我们还没有为我们的方块创建菜单。 为此，我们将创建一个继承自 `AbstractContainerMenu` 的 `DirtChestMenu` 类：

<<< @/reference/latest/src/main/java/com/example/docs/menu/custom/DirtChestMenu.java#menu

当服务器想要打开菜单时，客户端构造函数就会被调用。 它会创建一个空容器，然后该容器会自动与服务器上的实际容器同步。

服务端构造函数在服务器上被调用，因为它知道容器的内容，所以可以直接将其作为参数传递。

`quickMoveStack` 处理菜单中按住 Shift 键点击的物品。 这个示例复现了原版菜单（如箱子和发射器）的行为。

然后我们需要在一个新的 `ModMenuType` 类中注册菜单：

<<< @/reference/latest/src/main/java/com/example/docs/menu/ModMenuType.java#register_menu

现在我们可以将方块实体中的 `createMenu` 的返回值设置为使用我们的菜单：

<<< @/reference/latest/src/main/java/com/example/docs/block/entity/custom/DirtChestBlockEntity.java#provider_implemented

::: info

`createMenu` 方法只会在服务端调用，所以我们调用服务端构造函数，并将 `this`（方块实体）作为容器参数传递。

:::

## 创建屏幕 {#creating-the-screen}

为了在客户端实际显示容器的内容，我们还需要为菜单创建一个屏幕。
我们将创建一个继承自 `AbstractContainerScreen` 的新类：

<<< @/reference/latest/src/client/java/com/example/docs/rendering/screens/inventory/DirtChestScreen.java#screen

对于这个屏幕的背景，我们直接使用了默认的发射器屏幕纹理，因为我们的泥土箱子使用相同的槽位布局。 或者，你 也可以为 `CONTAINER_TEXTURE` 提供自己的纹理。

因为这是一个菜单屏幕，所以我们还需要使用 `MenuScreens#register()` 方法在客户端注册它：

<<< @/reference/latest/src/client/java/com/example/docs/ExampleModScreens.java#register_screens

游戏加载完毕后，你应该会看到一个泥土箱子，右键点击即可打开菜单并将物品存放其中。

![游戏内泥土箱子菜单](/assets/develop/blocks/container_menus_0.png)
