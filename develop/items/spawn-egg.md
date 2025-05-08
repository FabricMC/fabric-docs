---
title: Spawn Egg Item
description: Learn how to register a spawn egg item, and configure its colors.
authors:
  - VatinMc
---

::: info PREREQUISITES
Visit [Creating Your First Item](./first-item) to understand the references made here. If this is your first item, you must complete the steps listed there.
:::

Spawn eggs are items, which are used to spawn the corresponding mob. When creating spawn eggs, the following questions needs to be answered:
- Which `MobEntity` should be spawned?
- What colors should shell and dots be?
- What should your spawn egg item be named?

## Simplify Registering Spawn Egg Items {#simplify-registering-spawn-egg-items}

When registering spawn egg items, an `EntityType` is needed.

Create a method that accepts a `String`, an `EntityType` and `Item.Settings` to create the `SpawnEggItem` instance. The method should be located in the [item class](./first-item#preparing-your-items-class).

::: info
Check out the [item registration method](./first-item#preparing-your-items-class) for more information.
:::

@[code transcludeWith=:::spawn_egg_register_method](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## Registering Your Spawn Egg Item {#registering-your-spawn-egg-item}

The `registerSpawnEgg(...)` method can now be used, to register custom spawn eggs.

Change the name and `EntityType` as desired.

@[code transcludeWith=:::spawn_egg_register_item](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

![spawn egg item without texture](/assets/develop/items/spawn_egg_1.png)

The spawn egg item is now registered and can spawn frog entities. Texture, name and item group are still missing.

## Creating The Item Model Description {#creating-the-item-model-description}

Since spawn eggs utilize a template model, there is no need to create an item model.

Minecraft uses the item model description to apply item models to items. In version 1.21.4 the item model description is also used, to apply colors to spawn eggs.

::: info
The values represent the colors of shell and dots. To get the correct color value, a hexadecimal ARGB value (e.g. 0xffd07444) is converted into a signed 32-bit integer.

If you don't want to worry about the conversion, you can use tools like [Spawn Egg Color Picker](https://vatinmc.github.io/spawn-egg-color-picker/).
:::

Create the item description JSON in the `assets/mod-id/items` directory, with the same file name as the id of the item: `custom_spawn_egg.json`.

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/items/custom_spawn_egg.json)

![spawn egg item with model description](/assets/develop/items/spawn_egg_2.png)

The spawn egg item is now textured. Name and item group are still missing.

## Naming The Spawn Egg{#naming-the-spawn-egg}

To name the spawn egg, the translation key `item.mod_id.custom_spawn_egg` must be assigned a value. This process is similar to [Naming The Item](./first-item#naming-the-item).

Create or edit JSON file at: `src/main/resources/assets/mod-id/lang/en_us.json` and put in the translation key, and its value:

```json
{
  "item.fabric-docs-reference.custom_spawn_egg": "Custom Spawn Egg"
}
```

## Adding To An Item Group{#adding-to-an-item-group}

Check out [Adding The Item To An Item Group](./first-item#adding-the-item-to-an-item-group) for more detailed information.

The spawn egg is added to the spawn egg `ItemGroup`, in the `initialize()` method of the [item class](./first-item#preparing-your-items-class).

@[code transcludeWith=:::spawn_egg_item_group](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

![spawn egg item with name and item group](/assets/develop/items/spawn_egg_3.png)

## Troubleshooting{#troubleshooting}

::: info
If this is your first item you need some parts of [Creating Your First Item](./first-item) to make this work.
:::

### Not Registered

If you aren't able to access your spawn egg ingame, you most likely didn't statically initialize the item class. Check out [Registering Your First Item](first-item#registering-an-item) to get the needed information.

### Texture Missing

- Minecraft uses Identifiers to handle information like textures of items. It is very important, to stay constant with naming items, folders, and so on. When using the mod-id it must be the same as specified in your `fabric.mod.json` file.
- JSON files are very sensitive, e.g. a misplaced comma  will corrupt them. Check the [Item Model Description](#creating-the-item-model-description) for errors. This concept for spawn eggs only works for version 1.21.4.

::: tip
If you plan on adding multiple spawn eggs, consider using [data generation](../data-generation/spawn-egg-model) to avoid making mistakes.
:::