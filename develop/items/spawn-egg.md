---
title: Spawn Eggs
description: Learn how to register a spawn egg item.
authors:
  - Earthcomputer
  - JaaiDead
  - cassiancc
  - Fellteros
  - skycatminepokie
  - VatinMc
---

<!---->

::: info PREREQUISITES

You must first understand [how to create an item](./first-item), which you can then turn into a spawn egg.

This article also references the Mini Golem entity from [Creating your First Entity](../entities/first-entity). If you have not followed that tutorial, you can use a vanilla entity like `EntityType.FROG` rather than `ModEntityTypes.MINI_GOLEM`.

:::

Spawn eggs are special items that, when used, spawn their corresponding mob. You can register one with the `register` method from your [items class](./first-item#preparing-your-items-class), by passing `SpawnEggItem::new` to it.

@[code transcludeWith=:::custom_entity_spawn_egg](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

There are still a few things to do before it's ready: you must add a texture, an item model, a client item, a name, and add the spawn egg to the appropriate creative tab.

## Adding a Texture {#adding-a-texture}

Create the 16x16 item texture in the `assets/example-mod/textures/item` directory, with the same file name as the id of the item: `mini_golem_spawn_egg.png`. An example texture is provided below.

<DownloadEntry visualURL="/assets/develop/entity/mini_golem_spawn_egg.png" downloadURL="/assets/develop/entity/mini_golem_spawn_egg_small.png">Texture</DownloadEntry>

## Adding a Model {#adding-a-model}

Create the item model in the `assets/example-mod/models/item` directory, with the same file name as the id of the item: `mini_golem_spawn_egg.json`.

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/mini_golem_spawn_egg.json)

## Creating the Client Item {#creating-the-client-item}

Create the client item JSON in the `assets/example-mod/items` directory, with the same file name as the id of the item model: `mini_golem_spawn_egg.json`.

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/mini_golem_spawn_egg.json)

![Spawn egg item with client item](/assets/develop/entity/mini_golem_spawned.png)

## Naming The Spawn Egg {#naming-the-spawn-egg}

To name the spawn egg, the translation key `item.example-mod.mini_golem_spawn_egg` must be assigned a value. This process is similar to [Naming The Item](./first-item#naming-the-item).

Create or edit JSON file at: `src/main/resources/assets/example-mod/lang/en_us.json` and put in the translation key, and its value:

```json
{
  "item.example-mod.mini_golem_spawn_egg": "Mini Golem Spawn Egg"
}
```

## Adding To A Creative Mode Tab {#adding-to-a-creative-mode-tab}

The spawn egg is added to the spawn egg `CreativeModeTab` in the `initialize()` method of the [items class](./first-item#preparing-your-items-class).

@[code transcludeWith=:::spawn_egg_creative_tab](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

![Spawn egg item with name and creative tab](/assets/develop/entity/spawn_egg_in_creative.png)

Check out [Adding the Item to a Creative Tab](./first-item#adding-the-item-to-a-creative-tab) for more detailed information.
