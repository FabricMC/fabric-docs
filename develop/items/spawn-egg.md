---
title: Spawn Egg Items
description: Learn how to register a spawn egg item, and configure its colors.
authors:
  - cassiancc
  - Fellteros
  - VatinMc
  - skycatminepokie

---

::: info PREREQUISITES
Visit [Creating Your First Item](./first-item) to understand the references made here. If this is your first item, you must complete the steps listed there first.
:::

Spawn eggs are items that are used to spawn the corresponding mob. When creating spawn eggs, the following questions need to be answered:

- Which `Mob` should be spawned?
- What texture should your spawn egg use?
- What should your spawn egg item be named?

## Simplify Registering Spawn Egg Items {#simplify-registering-spawn-egg-items}

When registering spawn egg items, an `EntityType` is needed.

Create a method that accepts a `String`, an `EntityType` and `Item.Properties` to create the `SpawnEggItem` instance. The method should be located in the [item class](./first-item#preparing-your-items-class).

::: info
Check out the [item registration method](./first-item#preparing-your-items-class) for more information.
:::

@[code transcludeWith=:::spawn_egg_register_method](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## Registering Your Spawn Egg Item {#registering-your-spawn-egg-item}

The `registerSpawnEgg(...)` method can now be used, to register custom spawn eggs.

Change the name and `EntityType` as desired.

@[code transcludeWith=:::spawn_egg_register_item](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

![spawn egg item without texture](/assets/develop/items/spawn_egg_1.png)

The spawn egg item is now registered and can spawn Frogs, but it still needs to be textured, named and placed in a creative mode tab.

## Adding an Client Item, Texture and Model {#adding-a-client-item-texture-and-model}

Since 1.21.5, spawn eggs, like any other item, require a texture, item model, and client item to apply item models to items.

### Adding a Texture {#adding-a-texture}

Create the 16x16 item texture in the `assets/example-mod/textures/item` directory, with the same file name as the id of the item: `custom_spawn_egg.png`. An example texture is provided below.

<DownloadEntry visualURL="/assets/develop/items/spawn_egg.png" downloadURL="/assets/develop/items/spawn_egg_small.png">Texture</DownloadEntry>

### Adding a Model {#adding-a-model}

Create the item model in the `assets/example-mod/models/item` directory, with the same file name as the id of the item: `custom_spawn_egg.json`.

@[code](@/reference/latest/src/main/generated/assets/example-mod/models/item/custom_spawn_egg.json)

### Creating the Client Item {#creating-the-client-item}

Create the client item JSON in the `assets/example-mod/items` directory, with the same file name as the id of the item model: `custom_spawn_egg.json`.

@[code](@/reference/latest/src/main/generated/assets/example-mod/items/custom_spawn_egg.json)

![spawn egg item with client item](/assets/develop/items/spawn_egg_2.png)

The spawn egg item is now textured, but it still needs to be named and placed in a creative mode tab.

## Naming The Spawn Egg {#naming-the-spawn-egg}

To name the spawn egg, the translation key `item.example-mod.custom_spawn_egg` must be assigned a value. This process is similar to [Naming The Item](./first-item#naming-the-item).

Create or edit JSON file at: `src/main/resources/assets/example-mod/lang/en_us.json` and put in the translation key, and its value:

```json
{
  "item.example-mod.custom_spawn_egg": "Custom Spawn Egg"
}
```

## Adding To A Creative Mode Tab {#adding-to-a-creative-mode-tab}

Check out [Adding The Item To An Creative Mode Tab](./first-item#adding-the-item-to-an-item-group) for more detailed information.

The spawn egg is added to the spawn egg `CreativeModeTab`, in the `initialize()` method of the [item class](./first-item#preparing-your-items-class).

@[code transcludeWith=:::spawn_egg_item_group](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

![spawn egg item with name and item group](/assets/develop/items/spawn_egg_3.png)

## Troubleshooting {#troubleshooting}

::: info
If this is your first item, you need some parts of [Creating Your First Item](./first-item) to make this work.
:::

### Not Registered {#not-registered}

If you aren't able to access your spawn egg ingame, you most likely didn't statically initialize the item class. Check out [Registering Your First Item](first-item#registering-an-item) for more information.

### Texture Missing {#texture-missing}

- Minecraft uses Resource Locations to handle information like textures of items. It is necessary to stay consistent with naming items, folders, and so on. Your namespace should be the same as the one specified in your `fabric.mod.json` file.
- JSON files are very sensitive, e.g., a misplaced comma will corrupt them. Check the [Client item](first-item#creating-the-client-item) for errors.

::: tip
If you plan on adding multiple spawn eggs, consider using [data generation](../data-generation/spawn-egg-model) to avoid making mistakes.
:::
