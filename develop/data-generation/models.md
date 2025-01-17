---
title: Model Generation
description: A guide to generating models, item models and blockstates via datagen
authors:
  - Fellteros
---

# Model Generation {#model-generation}

::: info PREREQUISITES
Make sure you've completed the [datagen setup](./setup) process first.
:::

## Setup {#setup}

First, we will need to create our ModelProvider. Create a plain Java class that `extends FabricModelProvider`. Implement both abstract methods, `generateBlockStateModels` & `generateItemModels`.
Lastly, create constructor matching super.

:::tip
If you're developing mods in JetBrains' Intellij Idea, you can press `Alt+Shift+Enter` while hovering over the problem to bring up a quick fix menu!
:::

@[code lang=java transcludeWith=:::datagen-model:provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceModelProvider.java)

Register this class in your `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method.

## Blockstates and block models {#blockstates-and-block-models}

::: info PREREQUISITES
You'll have to register the blocks, to which you want the data to be generated.
:::

This part of the tutorial will happen entirely in the `generateBlockStateModels` method. Notice the parameter `BlockStateModelGenerator blockStateModelGenerator` - this class will be responsible for generating all the JSON files.
Here are some examples you can use to generate your desired models:
