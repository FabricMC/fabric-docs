---
title: Creating Your First Item
description: Learn how to register a simple item and how to texture, model and name it.
---

# Creating Your First Item

This page will introduce you into some key concepts relating to items, and how you can register, texture, model and name them.

If you aren't aware, everything in Minecraft is stored in registries, and items are no exception to that.

## Preparing your items class

To simplify the registering of items, you can create a method that accepts an instance of an item and a string identifier.

You can put this method in a class called `ModItems` (or whatever you want to name the class!). 

Mojang does this with their items as well! Check out the `Items` class for inspiration.

```java
public class ModItems {
    // We can use generics to make it so we dont need to 
    // cast to an item when using this method.
    public static <T extends Item> T register(T item, String ID) {
        // Create the identifier for the item.
        Identifier itemID = new Identifier("mod_id", ID);
        
        // Register the item.
        T registeredItem = Registry.register(Registries.ITEM, itemID, item);

        // Return the registered item!
        return registeredItem;
    }
}
```

## Registering an item

You can now register an item using the method now.

The item constructor takes in an instance of the `Items.Settings` class as a parameter. This class allows you to:

- Assign the item's `ItemGroup`.
- Make the item edible by passing a `FoodComponent`.
- Set the item's durability.
- ...and other miscellaneous properties.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

However, when you go in-game, you can see that our item doesn't exist! This is because you don't statically initialize the class.

To do this, you can add a public static initialize method to your class and call it from your `ModInitializer` class. Currently, this method doesn't need anything inside it.

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/item/FabricDocsReferenceItems.java)

Calling a method on a class statically initializes it if it hasn't been used before - this means that all `final` fields are evaluated.

## Adding the item to an item group

::: info
If you want to add the item to a custom `ItemGroup`, checkout the [Custom Item Groups](./item-groups.md) page for more information.
:::

For example purposes, we will add this item to the ingredients `ItemGroup`, you will need to use Fabric API's item group events - specifically `ItemGroupEvents.modifyEntriesEvent`

This can be done in the `initialize` method of your items class.

@[code transcludeWith=:::4](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Loading into the game, you can see that our item has been registered, and is in the Ingredients item group:

![](/assets/develop/items/first_item_0.png)

However, it's missing the following:

- Item Model
- Texture
- Translation (name)

## Naming the item

The item currently doesn't have a translation, so you will need to add one. The translation key has already been provided by Minecraft: `item.mod_id.suspicious_substance`.

Create a new JSON file at: `src/main/resources/assets/<mod id here>/lang/en_us.json` and put in the translation key and it's value:

```json
{
    "item.mod_id.suspicious_substance": "Suspicious Substance"
}
```

You can either restart the game or build your mod and press <kbd>F3</kbd> + <kbd>T</kbd> to apply changes.

## Adding a texture and model

The item will still have the placeholder purple and black checkerboard texture until you create a texture and model for it.

To do this, place a 16x16 texture in the `assets/<mod id here>/textures/item` folder that has the same name of the item you've just registered.

For the example, the texture is called `suspicious_substance.png`; It's just the poop emoji for now, you can change this to something more... suitable if you wish.

<DownloadEntry type="Texture" visualURL="/assets/develop/items/first_item_1.png" downloadURL="/assets/develop/items/first_item_1_small.png" />

When restarting/reloading the game - you should see that the item still has no texture, that's because you will need to add a model that uses this texture.

You're going to create a simple `item/generated` model, which takes in an input texture and nothing else.

Create the model JSON in the `assets/<mod id here>/models/item` folder, with the same name as the item; `suspicious_substance.json`

@[code](@/reference/latest/src/main/resources/assets/fabric-docs-reference/models/item/suspicious_substance.json)

If you now reload the game (<kbd>F3</kbd> + <kbd>T</kbd>) you can now see your item texture!

![](/assets/develop/items/first_item_2.png)