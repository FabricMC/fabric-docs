---
title: Tag Generation
description: A guide to setting up tag generation with datagen.
authors:
  - skycatminepokie
  - IMB11
  - Spinoscythe
authors-nogithub:
  - mcrafterzz
---

# Tag Generation {#tag-generation}

::: info PREREQUISITES
Make sure you've completed the [datagen setup](./setup) process first.
:::

## Setup {#setup}

First, create your own class that `extends FabricTagProvider<T>`, where `T` is the type of thing you'd like to provide a tag for. This is your **provider**. Here we'll show how to create `Item` tags, but the same principle applies for other things. Let your IDE fill in the required code, then replace the `registryKey` constructor parameter with the `RegistryKey` for your type:

@[code lang=java transcludeWith=:::datagen-tags:provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceItemTagProvider.java)

::: info NOTE
You will need a different provider for each type of tag (eg. one `FabricTagProvider<EntityType<?>>` and one `FabricTagProvider<Item>`).
:::

To finish setup, add this provider to your `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method.

@[code lang=java transclude={29-29}](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

## Creating a Tag {#creating-a-tag}

Now that you've created a provider, let's add a tag to it. First, create a `TagKey<T>`:

@[code lang=java transcludeWith=:::datagen-tags:tag-key](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceItemTagProvider.java)

Next, call `getOrCreateTagBuilder` inside your provider's `configure` method. From there, you can add individual items, add other tags, or make this tag replace pre-existing tags.

If you want to add a tag, use `addOptionalTag`, as the tag's contents may not be loaded during datagen. If you are certain the tag is loaded, call `addTag`.

To forcefully add a tag and ignore the broken format, use `forceAddTag`.

@[code lang=java transcludeWith=:::datagen-tags:build](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceItemTagProvider.java)
