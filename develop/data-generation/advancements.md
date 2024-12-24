---
title: Advancement Generation
description: A guide to setting up advancement generation with datagen.
authors:
  - skycatminepokie
---

# Advancement Generation {#advancement-generation}

::: info PREQUISITES
Make sure you've completed the [datagen setup](./setup) process first.
:::

## Setup {#setup}

First, we need to make our provider. Create a class that `extends FabricAdvancementProvider` and fill out the base methods:

@[code lang=java transcludeWith=:::datagen-advancements:1](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceAdvancementProvider.java)

To finish setup, add this provider to your `DataGeneratorEntrypoint`.

@[code lang=java transcludeWith=:::datagen-advancements:2](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceAdvancementGenerator.java)

## Creating Advancements {#creating-advancements}

## Advancement Structure {#advancement-structure}

## Custom Criterion {#custom-criterion}

## Custom Conditions {#custom-conditions}

### Predicates {#predicates}
