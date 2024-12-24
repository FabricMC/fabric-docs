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

Along with creating raw translations, translations from `Identifier`s, and copying them from an already existing file (by passing a `Path`), there are helper methods for translating items, blocks, tags, stats, entities, status effects, item groups, entity attributes, and enchantments. Simply call `add` on the `translationBuilder` with what you want to translate and what it should translate to:

@[code lang=java transcludeWith=:::datagen-translations:3](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceEnglishLangProvider.java)

## Using translations

Generated translations take the place of a lot of translations added in other tutorials, but you can also use them anywhere you use a `Text` object. In our example, if we wanted to allow resource packs to translate our greeting, we use `Text.translatable` instead of `Text.literal`:

```java
ChatHud chatHud = MinecraftClient.getInstance().inGameHud.getChatHud();
chatHud.addMessage(Text.literal("Hello there!")); // [!code --]
chatHud.addMessage(Text.translatable("text.fabric-docs-reference.greeting")); // [!code ++]
```