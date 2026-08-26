---
title: Custom Fuel
description: Learn how to create your own fuel items.
authors:
  - NotNightSky
  - its-miroma
---

Fuels are the items that can be used in a furnace to smelt ores and cook food. Let's see how we can create our own custom fuel.

## Creating the Item {#creating-the-item}

Let's create a fuel item called "Quark-Gloun Plasma". We start by [creating an item](./first-item) as usual, then apply the `CookingFuel` component to it, either directly via `.component`, or via the `.cookingFuel` helper if you're referring to a `ResourceKey<ContextIntProvider>`:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#quark_gluon_plasma_resource

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#quark_gluon_plasma

Alternatively, if we're not the ones registering the item, we can use the `DefaultItemComponentEvents.MODIFY` event from the Fabric Item API:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#fuel_item

As usual, don't forget to add a texture, translation, creative tab, yada yada. Here's an example texture:

<DownloadEntry visualURL="/assets/develop/items/quark_gluon_plasma_big.png" downloadURL="/assets/develop/items/quark_gluon_plasma.png">Texture</DownloadEntry>

Here's how it looks like when used as fuel in the furnace:

<VideoPlayer src="/assets/develop/items/fuel_in_furnace.webm">Using Quark-Gluon Plasma as Fuel</VideoPlayer>
