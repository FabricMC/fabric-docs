---
title: Custom Compostable
description: Learn how to create your own compostable items.
authors:
  - NotNightSky
  - its-miroma
---

Compostable items are those that convert to bone meal when they are placed in a composter. Let's see how we can create our own custom compostable.

## Creating the Item {#creating-the-item}

Let's create a compostable item called "Bone Marrow". We start by [creating an item](./first-item) as usual, then apply the `Compostable` component to it, either directly via `.component`, or via the `.compostable` helper if you're referring to a `ResourceKey<ContextIntProvider>`:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#bone_marrow_resource

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#bone_marrow

Alternatively, if we're not the ones registering the item, we can use the `DefaultItemComponentEvents.MODIFY` event from the Fabric Item API:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#compostable_item

As usual, don't forget to add a texture, translation, creative tab, yada yada. Here's an example texture:

<DownloadEntry visualURL="/assets/develop/items/bone_marrow_big.png" downloadURL="/assets/develop/items/bone_marrow.png">Texture</DownloadEntry>

Here's how it looks like when used in the composter:

<VideoPlayer src="/assets/develop/items/using_bone_marrow.webm">Using Bone Marrow as Compostable</VideoPlayer>
