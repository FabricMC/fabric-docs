---
title: Spawn Egg Model Generation
description: A guide to generating spawn egg item models via datagen.
authors:
  - VatinMc
---

::: info PREREQUISITES
Make sure you've completed the [datagen setup](./setup) process first.

If you haven't registered your spawn egg item yet, check out [Spawn Egg Item](../items/spawn-egg).
:::

## Setup {#setup}

First, we need a `ModelProvider`. Create a class that `extends FabricModelProvider`. Implement both abstract methods: `generateBlockStateModels` and `generateItemModels`.
Lastly, create a constructor matching super.

@[code lang=java transcludeWith=:::datagen-model:provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Register this class in your `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method.

## Spawn Egg Item Model {#spawn-egg-item-model}

Spawn eggs don't have a block model, so only `generateItemModels(...)` is relevant.

``` java
public void generateItemModels(ItemModelGenerator itemModelGenerator) {

}
```

Since spawn eggs use a template model, we can use the method `registerSpawnEgg(...)` of the `ItemModelGenerator` instance, to assign the colors and link the model to the `SpawnEggItem`.

@[code transcludeWith=:::datagen-model:spawn-egg](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

::: info
Minecraft uses signed 32-bit Integers to store ARGB color values. Java is able to convert RGB hexadecimal color values like shown above.

If you don't want to worry about the conversion, you can use tools like [Spawn Egg Color Picker](https://vatinmc.github.io/spawn-egg-color-picker/).
:::

![spawn egg item with model](/assets/develop/data-generation/spawn_egg.png)