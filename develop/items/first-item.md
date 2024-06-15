---
title: Creating Your First Item
description: Learn how to register a simple item and how to texture, model and name it.
authors:
  - IMB11
---

# Creating Your First Item

This page will introduce you into some key concepts relating to items, and how you can register, texture, model and name them.

If you aren't aware, everything in Minecraft is stored in registries, and items are no exception to that.

## Preparing Your Items Class

To simplify the registering of items, you can create a method that accepts an instance of an item and a string identifier.

You can put this method in a class called `ModItems` (or whatever you want to name the class!).

Mojang does this with their items as well! Check out the `Items` class for inspiration.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## Registering an Item

You can now register an item using the method now.

The item constructor takes in an instance of the `Items.Settings` class as a parameter. This class allows you to:

- Assign the item's `ItemGroup`.
- Make the item edible by passing a `FoodComponent`.
- Set the item's durability.
- ...and other miscellaneous properties.

::: tip
If you want to change your item's stack size, you can use the `maxCount` method in the `Items.Settings`/`FabricItemSettings` class.

This will not work if you've marked the item as damageable, as the stack size is always 1 for damageable items to prevent duplication exploits.
:::

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

However, when you go in-game, you can see that our item doesn't exist! This is because you don't statically initialize the class.

To do this, you can add a public static initialize method to your class and call it from your `ModInitializer` class. Currently, this method doesn't need anything inside it.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/FabricDocsReferenceItems.java)

Calling a method on a class statically initializes it if it hasn't been previously loaded - this means that all `static` fields are evaluated. This is what this dummy `initialize` method is for.

## Adding the Item to an Item Group

::: info
If you want to add the item to a custom `ItemGroup`, checkout the [Custom Item Groups](./item-groups.md) page for more information.
:::

For example purposes, we will add this item to the ingredients `ItemGroup`, you will need to use Fabric API's item group events - specifically `ItemGroupEvents.modifyEntriesEvent`

This can be done in the `initialize` method of your items class.

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Loading into the game, you can see that our item has been registered, and is in the Ingredients item group:

![Item in the ingredients group](/assets/develop/items/first_item_0.png)

However, it's missing the following:

- Item Model
- Texture
- Translation (name)

## Naming The Item

The item currently doesn't have a translation, so you will need to add one. The translation key has already been provided by Minecraft: `item.mod_id.suspicious_substance`.

Create a new JSON file at: `src/main/resources/assets/<mod id here>/lang/en_us.json` and put in the translation key, and it's value:

```json
{
    "item.mod_id.suspicious_substance": "Suspicious Substance"
}
```

You can either restart the game or build your mod and press <kbd>F3</kbd> + <kbd>T</kbd> to apply changes.

## Adding a Texture and Model

To give your item a texture and model, simply create a 16x16 texture image for your item and save it in the `assets/<mod id here>/textures/item` folder. Name the texture file the same as the item's identifier, but with a `.png` extension.

For example purposes, you can use this example texture for `suspicious_substance.png`

<DownloadEntry type="Texture" visualURL="/assets/develop/items/first_item_1.png" downloadURL="/assets/develop/items/first_item_1_small.png" />

When restarting/reloading the game - you should see that the item still has no texture, that's because you will need to add a model that uses this texture.

You're going to create a simple `item/generated` model, which takes in an input texture and nothing else.

Create the model JSON in the `assets/<mod id here>/models/item` folder, with the same name as the item; `suspicious_substance.json`

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/item/suspicious_substance.json)

![Item with correct model](/assets/develop/items/first_item_2.png)

## Making the Item Compostable or a Fuel

Fabric API provides various registries that can be used to add additional properties to your item.

For example, if you want to make your item compostable, you can use the `CompostableItemRegistry`:

@[code transcludeWith=:::_10](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Alternatively, if you want to make your item a fuel, you can use the `FuelRegistry` class:

@[code transcludeWith=:::_11](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

## Adding a Basic Crafting Recipe

<!-- In the future, an entire section on recipes and recipe types should be created. For now, this suffices. -->

If you want to add a crafting recipe for your item, you will need to place a recipe JSON file in the `data/<mod id here>/recipes` folder.

For more information on the recipe format, checkout these resources:

- [Recipe JSON Generator](https://crafting.thedestruc7i0n.ca/)
- [Minecraft Wiki - Recipe JSON](https://minecraft.wiki/w/Recipe#JSON_Format)

## Custom Tooltips

If you want your item to have a custom tooltip, you will need to create a class that extends `Item` and override the `appendTooltip` method.

::: info
This example uses the `LightningStick` class created in the [Custom Item Interactions](./custom-item-interactions.md) page.
:::

@[code lang=java transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/custom/LightningStick.java)

Each call to `add()` will add one line to the tooltip.

![Tooltip Showcase](/assets/develop/items/first_item_3.png)
