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

## Advancement Structure {#advancement-structure}

An advancement is made up a few different components. Along with the requirements, called "criterion," it may have:

- An `AdvancementDisplay` that tells the game how to show the advancement to players,
- `AdvancementRequirements`, which are different from criterion (we'll go over these later), %% TODO: What are these? %%
- `AdvancementRewards`, which the player recives for completing the advancement.
- A `CriterionMerger`, which tells the advancement how to handle multiple criterion, and
- A parent `Advancement`, which organizes the heirarchy you see on the "Advancements" screen.

## Simple Advancements {#simple-advancements}

Here's a simple advancement for getting a dirt block:

@[code lang=java transcludeWith=:::datagen-advancements:3](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceAdvancementProvider.java)

::: details JSON Output
@[code lang=json](@/reference/latest/src/main/generated/data/minecraft/advancement/fabric-docs-reference/get_dirt.json)
:::

## Custom Criterion {#custom-criterion}

## Custom Conditions {#custom-conditions}

### Predicates {#predicates}
