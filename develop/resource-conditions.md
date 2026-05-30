---
title: Resource Conditions
description: A guide to allowing your mod's data to load conditionally.
authors:
  - cassiancc
resources:
  https://github.com/FabricMC/fabric-api/blob/26.1.2/fabric-data-generation-api-v1/src/testmod/java/net/fabricmc/fabric/test/datagen/DataGeneratorTestEntrypoint.java: Fabric API's Data Generation Test Mod
---

When designing integrations with other mods, a common need is a way to define when your mod's resources should be loaded. For this reason, Fabric API offers Resource Conditions.

By default, this API can be used with recipes, advancements, loot tables, predicates, and item modifiers.

Resource conditions can either be added via [data generation](./data-generation/setup) or when writing JSON by hand. For more information about how to add resource conditions via data generation, see the Data Generation docs.

Load conditions are added to the root of a JSON file.

::: details A recipe with a condition that makes it only load when a tag is populated.

<<< @/reference/latest/src/main/generated/data/example-mod/recipe/sand.json

:::

## Built-in Conditions {#built-in}

Fabric API provides nine built-in conditions for your mod to use.

### Operators {#operators}

These are the standard boolean operators.

#### True {#true}

Always succeeds:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/true.json

#### Not {#not}

Inverts the load condition specified in `value`. For example, the following will fail:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/not.json

#### Or {#or}

Succeeds if at least one of the conditions in `values` succeeds. For example, the following will succeed:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/or.json

#### And {#and}

Succeeds if every condition in `values` succeeds. For example, the following will fail:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/and.json

### All Mods Loaded {#all-mods-loaded}

Succeeds if all mods in `values` are present and loaded. For example, the following succeeds only if both `example-mod` and `another-mod` are loaded:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/all_mods_loaded.json

### Any Mods Loaded {#any-mods-loaded}

Succeeds if at least one of the mods in `values` is present. For example, the following will succeed if either `example-mod`, or `another-mod`, or both, are loaded:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/any_mods_loaded.json

### Tags Populated {#tags-populated}

Succeeds if the specified `registry` contains all tags in `values`. For example, the following will succeed if the `example-mod:smelly_items` item tag is loaded, and will fail if the tag is empty or broken:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/tags_populated.json

### Features Enabled {#features-enabled}

Succeeds if all [feature flags](https://minecraft.wiki/w/Experiments#Java_Edition) in `features` are enabled. For example, the following will succeed if both `minecraft:vanilla` and `minecraft:redstone_experiments` are enabled:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/features_enabled.json

### Registry Contains {#registry-contains}

Succeeds if the registry contains all of the identifiers in `values`. For example, the following will succeed if `minecraft:cobblestone` exists in the registry:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/registry_contains.json

## Custom Conditions {#custom-conditions}

::: info PREREQUISITES

You must first understand [how to create a codec](./codecs) before setting a custom resource condition.

:::

Fabric API also provides the flexibility of creating your own resource conditions.

To demonstrate this, we'll create a condition that checks the current date. This could be used for special behaviour on holidays like Halloween or April Fools.

### Preparing Your Condition {#preparing-your-condition}

For simplicity, we'll be creating a helper method that instantiates your resource condition from a name and a [Codec](./codecs). You should put this method in a class called `ModResourceConditions` (or whatever you want to name it).

::: tip

Fabric does the same with its built-in conditions; you can refer to the `DefaultResourceConditionTypes` class to see this in action.

:::

<<< @/reference/latest/src/main/java/com/example/docs/conditions/ModResourceConditions.java#create

### Creating Your Condition {#creating-your-condition}

A resource condition consists of three parts: 
- a constructor that accepts values
- a codec to serialize those values
- a `test` method to handle what to do with them

We'll create a new class for the resource condition, named `DateMatchesResourceCondition`. First, create a new `record` that accepts an `int` for the month, and an `int` for the day:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#record

Next, add a Codec that reflects what the constructor accepts:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#codec

Next, we'll add a `test` method that checks the current date. This example is based on the logic the game itself does in `SpecialDates`.

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#test

### Registering Your Condition {#registering-your-condition}

Back in `ModResourceConditions`, we can now register our resource condition:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/ModResourceConditions.java#register

This condition type can then be referenced from `DateMatchesResourceCondition` as well:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/DateMatchesResourceCondition.java#type

Be sure to call `ModResourceConditions.register` in your [mod's initializer](./getting-started/project-structure#entrypoints):

<<< @/reference/latest/src/main/java/com/example/docs/conditions/ExampleModResourceConditions.java#init

### Using Your Condition {#using-your-condition}

Now, we have a condition that succeeds if the system date matches the date provided in the resource condition. For example, this condition will only succeed on April Fools.

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/date_matches.json
