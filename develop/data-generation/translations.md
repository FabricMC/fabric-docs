---
title: Translation Generation
description: A guide to setting up translation generation with datagen.
authors:
  - skycatminepokie
---

# Translation Generation

::: info PREQUISITES
Make sure you've completed the [datagen setup](./setup) process first.
:::

## Setup

First, we'll make our **provider**. Remember, providers are what actually generate data for us. Create a class that `extends FabricLanguageProvider` and fill out the base methods:

@[code lang=java transcludeWith=:::datagen-translations:1](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceEnglishLangProvider.java)

::: info NOTE
You will need a different provider for each langauge you want to generate (eg. one `ExampleEnglishLangProvider` and one `ExamplePirateLangProvider`).
:::

To finish setup, add this provider to your `DataGeneratorEntrypoint`.

@[code lang=java transcludeWith=:::datagen-translations:2](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceLangGenerator.java)

## Creating translations

%%TODO%%

@[code lang=java transcludeWith=:::datagen-tags:3](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceItemTagProvider.java)

## Using translations

%%TODO%%