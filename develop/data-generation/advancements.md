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

## Custom Criteria {#custom-criteria}

::: warning
While datagen can be on the client side, `Criterion`s and `Predicate`s are in the main source set (both sides), since the server needs to trigger and evaluate them.
:::


### Definitions {#definitions}

A **criterion** (plural: criteria) is something a player can do (or that can happen to a player) that may be counted towards an advancement. The game comes with many [criteria](https://minecraft.wiki/w/Advancement_definition#List_of_triggers), which can be found in the `net.minecraft.advancement.criterion` package. Generally, you'll only need a new criterion if you implement a custom mechanic into the game.

**Conditions** are evalued by criteria %%TODO: fact check%%. A criterion is only counted if all the relevant conditions are met. Conditions are usually expressed with a predicate.

A **predicate** is something that takes a value and returns a `boolean`. For example, a `Predicate<Item>` might return `true` if the item is a diamond, while a `Predicate<LivingEntity>` might return `true` if the entity is not hostile to villagers.

### Creating Custom Criteria {#creating-custom-criteria}

First, we'll need a new mechanic to implement. Let's tell the player what tool they used every time they break a block.

@[code lang=java transcludeWith=:::datagen-advancements:5](@/reference/latest/src/main/java/com/example/docs/advancement/FabricDocsReferenceDatagenAdvancement.java)

Note that this code is really bad. The `HashMap` is not stored anywhere persistent, so it will be reset every time the game is restarted. It's just to show off `Criterion`s. Start the game and try it out!

Next, let's create our custom criterion, `UseToolCriterion`. It's going to need its own `Conditions` class to go with it, so we'll make them both at once:

@[code lang=java transcludeWith=:::datagen-advancements:criterion-base](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

Whew, that's a lot! Let's break it down.

- `UseToolCriterion` is an `AbstractCriterion`, which `Conditions` can apply to.
- `Conditions` has a `playerPredicate` field. All `Conditions` should have a player predicate, since all `Criterion`s apply only to players. %%TODO: fact check%%
- `Conditions` also has a `CODEC`. This `Codec` is simply the codec for its one field, `playerPredicate`, with extra instructions to convert between them (`xmap`).

::: info
To learn more about codecs, see the [Codecs](../codecs) page.
:::

We're going to need a way to check if the conditions are met. Let's add a helper method to `Conditions`:

@[code lang=java transcludeWith=:::datagen-advancements:conditions-test](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

Now that we've got a criterion and its conditions, we need a way to trigger it. Add a trigger method to `UseToolCriterion`:

@[code lang=java transcludeWith=:::datagen-advancements:criterion-trigger](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

Almost there! Next, we need an instance of our criterion to work with. Let's put it in a new class, called `ModCriteria`.

@[code lang=java transcludeWith=:::datagen-advancements:mod-criteria](@/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java)

Finally, we need to trigger our criteria. Add this to where we sent a message to the player. %%TODO: find better naming for transclude, maybe better explanation of where it goes.%%

@[code lang=java transcludeWith=:::datagen-advancements:trigger-criterion](@/reference/latest/src/main/java/com/example/docs/advancement/FabricDocsReferenceDatagenAdvancement.java)

Your shiny new criterion is now ready to use!