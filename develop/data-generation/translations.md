---
title: Translation Generation
description: A guide to setting up translation generation with datagen.
authors:
  - skycatminepokie
  - MattiDragon
  - IMB11
  - Spinoscythe
authors-nogithub:
  - sjk1949
  - mcrafterzz
  - jmanc3
---

# Translation Generation {#translation-generation}

::: info PREREQUISITES
Make sure you've completed the [datagen setup](./setup) process first.
:::

## Setup {#setup}

First, we'll make our **provider**. Remember, providers are what actually generate data for us. Create a class that `extends FabricLanguageProvider` and fill out the base methods:

@[code lang=java transcludeWith=:::datagen-translations:provider](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceEnglishLangProvider.java)

::: info NOTE
You will need a different provider for each langauge you want to generate (eg. one `ExampleEnglishLangProvider` and one `ExamplePirateLangProvider`).
:::

To finish setup, add this provider to your `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method.

@[code lang=java transclude={27-27}](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceDataGenerator.java)

## Creating Translations {#creating-translations}

Along with creating raw translations, translations from `Identifier`s, and copying them from an already existing file (by passing a `Path`), there are helper methods for translating items, blocks, tags, stats, entities, status effects, item groups, entity attributes, and enchantments. Simply call `add` on the `translationBuilder` with what you want to translate and what it should translate to:

@[code lang=java transcludeWith=:::datagen-translations:build](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceEnglishLangProvider.java)

## Using Translations {#using-translations}

Generated translations take the place of a lot of translations added in other tutorials, but you can also use them anywhere you use a `Text` object. In our example, if we wanted to allow resource packs to translate our greeting, we use `Text.translatable` instead of `Text.of`:

```java
ChatHud chatHud = MinecraftClient.getInstance().inGameHud.getChatHud();
chatHud.addMessage(Text.literal("Hello there!")); // [!code --]
chatHud.addMessage(Text.translatable("text.fabric_docs_reference.greeting")); // [!code ++]
```
