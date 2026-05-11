---
title: Biome Creation
description: A guide to adding a custom biome via datagen.
authors:
  - Wind292
---

<!---->

::: info PREREQUISITES

Make sure you've completed the [datagen setup](./setup) process first.

:::

## Initialization {#initialization}

First we need to create a provider class for the custom biome and fill out the mandatory implements.

@[code lang=java transcludeWith=:::datagen-biome:provider-init](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBiomeProvider.java)

Next let's create a bootstrap method for initialization of the biome. Something like;

@[code lang=java transcludeWith=:::datagen-biome:bootstrap](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBiomeProvider.java)

Now we can tailor our biome to our needs.

## Customization {#customization}

We can add specific [features](./features) that spawn in our biome:

@[code lang=java transcludeWith=:::datagen-biome:features](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBiomeProvider.java)

Now we need to declare what mobs are to spawn in the biome:

@[code lang=java transcludeWith=:::datagen-biome:mob-spawning](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBiomeProvider.java)

Next we need to declare the "Special Effects" of the biome, things like the water color, foliage color etc.

@[code lang=java transcludeWith=:::datagen-biome:special-effects](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBiomeProvider.java)

Next is creating the biome object. It is to note that none of the modifiers are required. There are more modifiers than just the few listed.

@[code lang=java transcludeWith=:::datagen-biome:biome-obj](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBiomeProvider.java)

## Registration {#registration}

Lastly we have to register the biome:

@[code lang=java transcludeWith=:::datagen-biome:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModBiomeProvider.java)

Don't forget to add the provider to your Data Generation entrypoint:

@[code lang=java transcludeWith=:::datagen-world:biome-provider](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

@[code lang=java transcludeWith=:::datagen-world:biome-registries](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Running Datagen {#running-datagen}

Now, when you run datagen, you should see a `.json` file under `src/main/generated/data/example-mod/worldgen/biome/biomename.json` for the biome created. And when you run your game in the world creation screen you should be able to select `World Type: Single Biome` and click `Customize`. Then find your biome in the list and generate the world.

Your biome will NOT spawn in a normal world naturally yet, all we have done is created the biome.
