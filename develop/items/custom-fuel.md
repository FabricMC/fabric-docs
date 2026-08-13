---
title: Custom Fuel
description: Learn how to create your own fuel items.
authors:
  - NotNightSky
---

Fuels are a core aspect of Minecraft, You need fuel to smelt items in a furnace and cook food, and you can create your own fuel items to add to the game.

Unless you're making an overpowered fuel source, you should consider:

- How long your fuel item will burn for.
- Is it early-game or endgame accessible?

## Creating the Item {#creating-the-item}

To create a fuel item, we do not need to add any special components to the item.
So, this will be the minimum properties we need to create a fuel item:

```java
new Item.Properties()
```

Now, let's create a fuel item called `Quark Gluon Plasma` with the following properties:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#quark_gluon_plasma_resource

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#quark_gluon_plasma

The `stacksTo`, `rarity`, and `lore` properties and components are optional, but they can be used to make your fuel item more unique and interesting.

After creating the item, we will be using the `FuelValueEvents.BUILD` event from the fabric registry API to make the fuel.

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#fuel_item

Now, create a 16x16 texture for your fuel item and place it in the `resources/assets/<modid>/textures/item` folder with the filename same as the resource key. An example texture is provided below:

<DownloadEntry visualURL="/assets/develop/items/quark_gluon_plasma_big.png" downloadURL="/assets/develop/items/quark_gluon_plasma.png">Texture</DownloadEntry>

And add the item to the creative menu and add the translations as shown in [First Item](./first-item).

Now the item will look like this in the creative menu:

![Fuel in Furnace](/assets/develop/items/quark_gluon_plasma_ss.png)

And this is how it will look in the furnace when used as fuel:

<VideoPlayer src="/assets/develop/items/fuel_in_furnace.webm">Using Quark Gluon Plasma as Fuel</VideoPlayer>
