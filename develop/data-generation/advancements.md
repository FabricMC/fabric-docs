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

@[code lang=java transcludeWith=:::datagen-advancements:provider-start](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java)

To finish setup, add this provider to your `DataGeneratorEntrypoint` within the `onInitializeDataGenerator` method.

@[code lang=java transcludeWith=:::datagen-advancements:register](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java)

## Advancement Structure {#advancement-structure}

An advancement is made up a few different components. Along with the requirements, called "criterion", it may have:

- Some `DisplayInfo` that tell the game how to show the advancement to players,
- `AdvancementRequirements`, which are lists of lists of criteria, requiring at least one criterion from each sub-list to be completed,
- `AdvancementRewards`, which the player receives for completing the advancement.
- A `Strategy`, which tells the advancement how to handle multiple criterion, and
- A parent `Advancement`, which organizes the hierarchy you see on the "Advancements" screen.

## Simple Advancements {#simple-advancements}

Here's a simple advancement for getting a dirt block:

@[code lang=java transcludeWith=:::datagen-advancements:simple-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java)

::: details JSON Output

<<< @/reference/latest/src/main/generated/data/example-mod/advancement/get_dirt.json

:::

## Parents {#parents}

In order to create or extend a tree of advancements, we can set a parent for our advancement. To do this, call `Advancement.Builder#parent(...)` and pass in a reference to the parent advancement.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#reference-parent

If no direct reference to the parent advancement is available (e.g. using a vanilla advancement as a parent), a placeholder can be created using an identifier.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#placeholder-parent

Your advancements should now be shown as a tree in the advancement menu.

![Advancement Tree](/assets/develop/data-generation/advancement_tree.png)

## Multiple Criteria {#multiple-criteria}

To have more advanced conditions in our advancements, we can call `Advancement.Builder#addCriteria(...)` more than once with additional criteria.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#multiple-criteria

By default, all criteria must be met for the advancement to be completed. We can change this behavior by supplying a different strategy.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#requirements-strategy

## Rewards {#rewards}

We can attach rewards to our advancements, which will be granted when a player completes the advancement. We can do this by calling `Advancement.Builder#rewards(...)` with the rewards we want to add.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#experience-reward

There are multiple other reward types available:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#reward-types

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

@[code lang=java transcludeWith=:::datagen-advancements:entrypoint](@/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java)

Note that this code is really bad. The `HashMap` is not stored anywhere persistent, so it will be reset every time the game is restarted. It's just to show off `Criterion`s. Start the game and try it out!

Next, let's create our custom criterion, `UseToolCriterion`. It's going to need its own `Conditions` class to go with it, so we'll make them both at once:

@[code lang=java transcludeWith=:::datagen-advancements:criterion-base](@/reference/latest/src/main/java/com/example/docs/advancement/UseToolCriterion.java)

Whew, that's a lot! Let's break it down.

- `UseToolCriterion` is a `SimpleCriterionTrigger`, which `Conditions` can apply to.
- `Conditions` has a `playerPredicate` field. All `Conditions` should have a player predicate (technically a `LootContextPredicate`).
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

To make sure that our criteria are initialized at the right time, add a blank `init` method:

@[code lang=java transcludeWith=:::datagen-advancements:mod-criteria-init](@/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java)

And call it in your mod initializer:

@[code lang=java transcludeWith=:::datagen-advancements:call-init](@/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java)

Finally, we need to trigger our criterion. Add this to where we sent a message to the player in the main mod class.

@[code lang=java transcludeWith=:::datagen-advancements:trigger-criterion](@/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java)

Your shiny new criterion is now ready to use! Let's add it to our provider:

@[code lang=java transcludeWith=:::datagen-advancements:custom-criteria-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java)

Run the datagen task again, and you've got your new advancement to play with!

## Conditions with Parameters {#conditions-with-parameters}

This is all well and good, but what if we want to only grant an advancement once we've done something 5 times? And why not another one at 10 times? For this, we need to give our condition a parameter. You can stay with `UseToolCriterion`, or you can follow along with a new `ParameterizedUseToolCriterion`. In practice, you should only have the parameterized one, but we'll keep both for this tutorial.

Let's work bottom-up. We'll need to check if the requirements are met, so let's edit our `Conditions#requirementsMet` method:

@[code lang=java transcludeWith=:::datagen-advancements:new-requirements-met](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

`requiredTimes` doesn't exist, so make it a parameter of `Conditions`:

@[code lang=java transcludeWith=:::datagen-advancements:new-parameter](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

Now our codec is erroring. Let's write a new codec for the new changes:

@[code lang=java transcludeWith=:::datagen-advancements:new-codec](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

Moving on, we now need to fix our `trigger` method:

@[code lang=java transcludeWith=:::datagen-advancements:new-trigger](@/reference/latest/src/main/java/com/example/docs/advancement/ParameterizedUseToolCriterion.java)

If you've made a new criterion, we need to add it to `ModCriteria`

@[code lang=java transcludeWith=:::datagen-advancements:new-mod-criteria](@/reference/latest/src/main/java/com/example/docs/advancement/ModCriteria.java)

And call it in our main class, right where the old one is:

@[code lang=java transcludeWith=:::datagen-advancements:trigger-new-criterion](@/reference/latest/src/main/java/com/example/docs/advancement/ExampleModDatagenAdvancement.java)

Add the advancement to your provider:

@[code lang=java transcludeWith=:::datagen-advancements:new-custom-criteria-advancement](@/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java)

Run datagen again, and you're finally done!

## Resource Conditions {#resource-conditions}

To apply a [resource condition](../resource-conditions) to a data-generated advancement, wrap the consumer with `withConditions` and provide any resource conditions you want to apply. This will then generate an advancement that has resource conditions applied:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModAdvancementProvider.java#datagen-advancements--conditions
