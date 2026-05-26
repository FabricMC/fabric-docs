---
title: Repetitive Item Generation
description: Learn how to make repetitive item model generation easier
authors:
  - JonyBoy19
---

When adding several different tools and material variants of those tools to your item model data generation, it can get
quite repetitive and creates significant clutter.
Due to this fact, it would be significantly easier to do this if you only had to change a few lines.

## Creating Example Repetitive Items {#creating-example-repetitive-items}

You will need to create four different basic items, as shown in [creating your first item](./first-item), that we'll be
using in this example.

The items we will be adding can be shown below:

@[code transcludeWith=:::repetitive-items](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

For example purposes, you may use the following textures for this example:

<DownloadEntry downloadURL="/assets/develop/items/repetitive_example_item_textures.zip">Repetitive Item Model Textures</DownloadEntry>

## Adding Repetitive Generation {#adding-repetitive-generation}

Once you have created your items, you begin adding your repetitive generation to the `ModelProvider`.

You'll start by adding the `getModItemByName` method to your class.

The `getModItemByName` method will allow you to convert normal strings into registered items by matching the item
identifier with the input strings.

The method we'll be using is provided below:

@[code transcludeWith=:::by-item-name](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

We also need to add the list that contains the material names of the items that will be generated, and the tool names or
any other items like an ingot or, in this case, a mallet and a coin:

@[code transcludeWith=:::item-lists](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Now we create the loop inside of your `ItemModels` method. This loop will be what actually runs through all of the repetitive items and creates the model
JSONs for all of the items and their variants.

We'll be using the loop shown below and make sure it is lower in the code than the lists or the methods created above:

@[code transcludeWith=:::item-loop](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Now everything is complete. When you run data generation, it should create all four model JSONs, and the textures will all
be visible in the game, assuming you followed all instructions correctly.
