---
title: Tag Generation
description: A guide to setting up tag generation with datagen.
authors:
  - CelDaemon
  - IMB11
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - mcrafterzz
---

<!---->

::: info PREREQUISITES

Make sure you've completed the [datagen setup](./setup) process first.

:::

## Setup {#setup}

Here we'll show how to create `Item` tags, but the same principle applies for other things.

Fabric provides several helper tag providers including one for items; `FabricTagsProvider.ItemTagsProvider`. We will use this helper class for this example.

You can create your own class that extends `FabricTagsProvider<T>`, where `T` is the type of thing you'd like to provide a tag for. This is your **provider**.

Let your IDE fill in the required code, then replace the `resourceKey` constructor parameter with the `ResourceKey` for your type:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#datagen-tags--provider

::: tip

You will need a different provider for each type of tag (eg. one `FabricTagsProvider<EntityType<?>>` and one `FabricTagsProvider<Item>`).

:::

To finish setup, add this provider to your `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen-tags--register

## Creating a Tag {#creating-a-tag}

Now that you've created a provider, let's add a tag to it. First, create a `TagKey<T>`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#datagen-tags--tag-key

Next, call `valueLookupBuilder` inside your provider's `configure` method. From there, you can add individual items, add other tags, or make this tag replace pre-existing tags.

If you want to add a tag, use `addOptionalTag`, as the tag's contents may not be loaded during datagen. If you are certain the tag is loaded, call `addTag`.

To forcefully add a tag and ignore the broken format, use `forceAddTag`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModItemTagProvider.java#datagen-tags--build
