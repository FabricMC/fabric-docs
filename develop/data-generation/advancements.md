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

## One More Example {#one-more-example}

Just to get the hang of it, let's add one more advancement. We'll practice adding rewards, using multiple criterion, and assigning parents:

@[code lang=java transcludeWith=:::datagen-advancements:4](@/reference/latest/src/client/java/com/example/docs/datagen/FabricDocsReferenceAdvancementProvider.java)

Don't forget to generate them! Use the terminal command below or the run configuration in IntelliJ.

::: code-group

```sh [Windows]
gradlew runDatagen
```

```sh [Linux]
./gradlew runDatagen
```

:::

## Custom Criterion {#custom-criterion}

There are already many criterions. The Minecraft Wiki has them listed under "[List of triggers](https://minecraft.wiki/w/Advancement_definition#List_of_triggers)." You can also look at the source itself in the `net.minecraft.advancement.criterion` package to see what's already available. Generally, you'll only need a new criterion if you implement a custom mechanic into the game. If none of the existing ones fit your needs, read on!

First, we'll need a new mechanic to implement. Let's tell the player what tool they used every time they break a block.

@[code lang=java transcludeWith=:::datagen-advancements:5](@/reference/latest/src/main/java/com/example/docs/advancement/FabricDocsReferenceDatagenAdvancement.java)

Note that this code is really bad. The `HashMap` is not stored anywhere persistent, so it will be reset every time the game is restarted. It's just to show off `Criterion`s. Start the game and try it out!


## Custom Conditions {#custom-conditions}

### Predicates {#predicates}
