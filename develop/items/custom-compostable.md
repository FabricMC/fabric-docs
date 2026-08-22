---
title: Custom Compostable
description: Learn how to create your own compostable items.
authors:
  - NotNightSky
---

Compostable items are a core aspect of Minecraft, You need the bone meal yielded from composting to grow crops, and you can create your own compostable items to add to the game.

## Creating the Item {#creating-the-item}

To create a compostable item, we do not need to add any special components to the item.
So, this will be the minimum properties we need to create a compostable item:

```java
new Item.Properties()
```

Now, let's create a compostable item called `Bone Marrow` with the following properties:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#bone_marrow_resource

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#bone_marrow

The`rarity` and `lore` properties and components are optional, but they can be used to make your compostable item more unique and interesting.

After creating the item, we will be using the `CompostableRegistry.INSTANCE` registry from the fabric registry API to make the compostable.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#compostable_item

Now, create a 16x16 texture for your compostable item and place it in the `resources/assets/<modid>/textures/item` folder with the filename same as the resource key. An example texture is provided below:

<DownloadEntry visualURL="/assets/develop/items/bone_marrow_big.png" downloadURL="/assets/develop/items/bone_marrow.png">Texture</DownloadEntry>

And add the item to the creative menu and add the translations as shown in [First Item](./first-item).

Now the item will look like this in the creative menu:

![Bone Marrow in Creative Menu](/assets/develop/items/bone_marrow_ss.png)

And this is how it will look like in the furnace when used as fuel:

<VideoPlayer src="/assets/develop/items/using_bone_marrow.webm">Using Bone Marrow as Compostable</VideoPlayer>
