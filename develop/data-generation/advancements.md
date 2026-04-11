---
title: Advancement Generation
description: A guide to setting up advancement generation with datagen.
authors:
  - CelDaemon
  - MattiDragon
  - skycatminepokie
  - Spinoscythe
authors-nogithub:
  - jmanc3
  - mcrafterzz
---

<!---->

::: info PREREQUISITES

Make sure you've completed the [datagen setup](./setup) process first.

:::

## Setup {#setup}

First, we need to make our provider. Create a class that extends `FabricAdvancementProvider` and fill out the base methods:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen-advancements--provider-start

To finish setup, add this provider to your `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen-advancements--register

## Advancement Structure {#advancement-structure}

An advancement is made up a few different components. Along with the requirements, called "criterion," it may have:

- Some `DisplayInfo` that tell the game how to show the advancement to players,
- `AdvancementRequirements`, which are lists of lists of criteria, requiring at least one criterion from each sub-list to be completed,
- `AdvancementRewards`, which the player receives for completing the advancement.
- A `Strategy`, which tells the advancement how to handle multiple criterion, and
- A parent `Advancement`, which organizes the hierarchy you see on the "Advancements" screen.

## Simple Advancements {#simple-advancements}

Here's a simple advancement for getting a dirt block:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen-advancements--simple-advancement

::: warning

When building your advancement entries, remember that the function accepts the `Identifier` of the advancement in `String` format!

:::

::: details JSON Output

<<< @/reference/latest/src/main/generated/data/example-mod/advancement/get_dirt.json

:::

## One More Example {#one-more-example}

Just to get the hang of it, let's add one more advancement. We'll practice adding rewards, using multiple criterion, and assigning parents:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen-advancements--second-advancement

## Custom Criteria {#custom-criteria}

::: warning

While datagen can be on the client side, `Criterion`s and `Predicate`s are in the main source set (both sides), since the server needs to trigger and evaluate them.

:::

### Definitions {#definitions}

A **criterion** (plural: criteria) is something a player can do (or that can happen to a player) that may be counted towards an advancement. The game comes with many [criteria](https://minecraft.wiki/w/Advancement_definition#List_of_triggers), which can be found in the `net.minecraft.advancements.criterion` package. Generally, you'll only need a new criterion if you implement a custom mechanic into the game.

**Conditions** are evaluated by criteria. A criterion is only counted if all the relevant conditions are met. Conditions are usually expressed with a predicate.

A **predicate** is something that takes a value and returns a `boolean`. For example, a `Predicate<Item>` might return `true` if the item is a diamond, while a `Predicate<LivingEntity>` might return `true` if the entity is not hostile to villagers.

### Creating Custom Criteria {#creating-custom-criteria}

First, we'll need a new mechanic to implement. Let's tell the player what tool they used every time they break a block.

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen-advancements--entrypoint

Note that this code is really bad. The `HashMap` is not stored anywhere persistent, so it will be reset every time the game is restarted. It's just to show off `Criterion`s. Start the game and try it out!

Next, let's create our custom criterion, `UseToolCriterion`. It's going to need its own `Conditions` class to go with it, so we'll make them both at once:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen-advancements--criterion-base

Whew, that's a lot! Let's break it down.

- `UseToolCriterion` is a `SimpleCriterionTrigger`, which `Conditions` can apply to.
- `Conditions` has a `playerPredicate` field. All `Conditions` should have a player predicate (technically a `LootContextPredicate`).
- `Conditions` also has a `CODEC`. This `Codec` is simply the codec for its one field, `playerPredicate`, with extra instructions to convert between them (`xmap`).

::: info

To learn more about codecs, see the [Codecs](../codecs) page.

:::

We're going to need a way to check if the conditions are met. Let's add a helper method to `Conditions`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen-advancements--conditions-test

Now that we've got a criterion and its conditions, we need a way to trigger it. Add a trigger method to `UseToolCriterion`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java#datagen-advancements--criterion-trigger

Almost there! Next, we need an instance of our criterion to work with. Let's put it in a new class, called `ModCriteria`.

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen-advancements--mod-criteria

To make sure that our criteria are initialized at the right time, add a blank `init` method:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen-advancements--mod-criteria-init

And call it in your mod initializer:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen-advancements--call-init

Finally, we need to trigger our criteria. Add this to where we sent a message to the player in the main mod class.

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen-advancements--trigger-criterion

Your shiny new criterion is now ready to use! Let's add it to our provider:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen-advancements--custom-criteria-advancement

Run the datagen task again, and you've got your new advancement to play with!

## Conditions with Parameters {#conditions-with-parameters}

This is all well and good, but what if we want to only grant an advancement once we've done something 5 times? And why not another one at 10 times? For this, we need to give our condition a parameter. You can stay with `UseToolCriterion`, or you can follow along with a new `ParameterizedUseToolCriterion`. In practice, you should only have the parameterized one, but we'll keep both for this tutorial.

Let's work bottom-up. We'll need to check if the requirements are met, so let's edit our `Conditions#requirementsMet` method:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen-advancements--new-requirements-met

`requiredTimes` doesn't exist, so make it a parameter of `Conditions`:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen-advancements--new-parameter

Now our codec is erroring. Let's write a new codec for the new changes:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen-advancements--new-codec

Moving on, we now need to fix our `trigger` method:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java#datagen-advancements--new-trigger

If you've made a new criterion, we need to add it to `ModCriteria`

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java#datagen-advancements--new-mod-criteria

And call it in our main class, right where the old one is:

<<< @/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java#datagen-advancements--trigger-new-criterion

Add the advancement to your provider:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen-advancements--new-custom-criteria-advancement

Run datagen again, and you're finally done!
