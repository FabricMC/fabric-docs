---
title: Tag Generation
description: A guide to setting up tag generation with datagen.
authors:
  - skycatminepokie
---

# Tag Generation

::: info PREQUISITES
Make sure you've completed the [datagen setup](./setup) process first.
:::

## Setup
First, create your own class that `extends FabricTagProvider<T>`, where `T` is the type of thing you'd like to provide a tag for. This is your **provider**. Here we'll show how to create `Item` tags, but the same principal applies for other things. Let your IDE fill in the required code, then replace the `registryKey` constructor parameter with the `RegistryKey` for your type:

@[code lang=java transcludeWith=:::datagen-tags:1](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceItemTagProvider.java)

::: info NOTE
You will need a different provider for each type of tag (eg. one `FabricTagProvider<EntityType<?>>` and one `FabricTagProvider<Item>`).
:::

Now add this provider to your `DataGeneratorEntrypoint`, and you're ready to start creating tags!

@[code lang=java transcludeWith=:::datagen-tags:2](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceItemTagGenerator.java)

## Creating a Tag

## Adding to a Tag
