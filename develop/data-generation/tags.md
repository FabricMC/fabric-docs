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

Now that you've created a provider, let's add a tag to it. Create a `TagKey<T>`, then call `getOrCreateTagBuilder` inside your provider's `configure` method. Then, you can add individual items, add other tags, or make this tag replace pre-existing tags.

@[code lang=java transcludeWith=:::datagen-tags:3](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceItemTagProvider.java)

If you want to add a tag, use `addOptionalTag`, as the tag's contents may not be loaded during datagen. If you are certain the tag is loaded, call `addTag`.

To forcefully add a tag and ignore the broken format, use `forceAddTag`.