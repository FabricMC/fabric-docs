---
title: Repetitive Item Generation
description: Learn how to make repetitive item model generation easier
authors:
  - JonyBoy19
---

When adding several different tools and material varients of those tools to your item model data generation it can get
quite repetetive and create significant clutter.
Due to this fact it would be significantly easier to do this if you only had to change a few lines.

## Creating Example Repetitive Items {#creating-example-repetitive-items}

You will need to create four different basic items as shown in [creating your first item](./first-item) that we'll be
using in this example.

The items we will be adding can be show below:

@[code transcludeWith=:::repetitive-items](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

For example purposes, you may use the following textures for this example:

<DownloadEntry downloadURL="/assets/develop/items/repetitive_example_item_textures.zip">Repetitive Item Model Textures</DownloadEntry>

## Adding Repetitive Generation {#adding-repetitive-generation}

Once you have your items created you begin adding your repetitive generation the the `ModelProvider`.

You'll start by adding the `getModItemByName` method to your class.

The `getModItemByName` method will allow you to convert normal strings into registered items by matching the item
identifier with the inputed strings.

The method we'll be using is provided below:

@[code transcludeWith=:::by-item-name](@reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

We also need to add the list that contain the material names of the items that will be generated and the tool names or
any other items like an ingot or in this case a mallet and a coin:

@[code transcludeWith=:::item-lists](@reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Now we create the loop inside of your `ItemModels` method, this loop will be what actualy runs through all of the repetitive items and creates the model
json's for all of the items and their varients.

We'll be using the loop show below and make sure it is lower in the code that the lists or the methods created above:

@[code transcludeWith=:::item-loop](@reference/latest/src/client/java/com/example/docs/datagen/ExampleModModelProvider.java)

Now everything is complete, when you run data generation it should create all four model json's and the textures will all
be visible in game assuming you followed all instructions correctly.
