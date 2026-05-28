---
title: Resource Conditions
description: A guide to allowing your mod's data to load conditionally.
authors:
  - cassiancc
---

When designing integrations with other mods, a common need is a way to define when your mod's resources should be loaded. For this reason, Fabric API offers Resource Conditions.

By default, this API can be used with recipes, advancements, loot tables, predicates, and item modifiers.

While you could use [data generation](./data-generation/setup) to output resource conditions, this guide will primarily focus on how to write them by hand.

Load conditions can be added to the root of a JSON file.

::: details A recipe with a condition set to always load.

```json
{
  "fabric:load_conditions": [
    {
      "condition": "fabric:true"
    }
  ],
  "type": "minecraft:crafting_shapeless",
  "ingredients": ["minecraft:stick"],
  "result": {
    "id": "minecraft:diamond"
  }
}
```

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

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/true.json

#### Or {#or}

Succeeds if at least one of the conditions in `values` succeeds. For example, the following will succeed:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/or.json

#### And {#and}

Succeeds if every condition in `values` succeeds. For example, the following will fail:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/or.json

### All Mods Loaded {#all-mods-loaded}

Succeeds if all mods in `values` are present and loaded. For example, the following succeeds only if both `example-mod` and `another-mod` are loaded:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/all_mods_loaded.json

### Any Mods Loaded {#any-mods-loaded}

Succeeds if at least one of the mods in `values` is present. For example, the following will succeed if either `example-mod`, or `another-mod`, or both, are loaded.

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

To demonstrate this, we'll create a condition that mirrors [Tags Populated](#tags-populated), but it only succeeds if at least one tag is empty. Obviously, in real code you can just use [`not`](#not).

### Preparing Your Condition {#preparing-your-condition}

For simplicity, we'll be creating a helper method that instantiates your resource condition from a name and a [Codec](./codecs). You should put this method in a class called `ModResourceConditions` (or whatever you want to name it).

::: tip

Fabric does the same with its built-in conditions; you can refer to the `DefaultResourceConditionTypes` class to see this in action.

:::

<<< @/reference/latest/src/main/java/com/example/docs/conditions/ModResourceConditions.java#create

### Creating Your Condition {#creating-your-condition}

A resource condition consists of three parts: a constructor that accepts values, a codec to serialize those values, and a `test` method to handle what to do with them.

We'll create a new class for the resource condition, named `TagsEmptyResourceCondition`. First, create a new `record` that accepts an `Identifier` for the registry and a list of `Identifier`s for the tags:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/TagsEmptyResourceCondition.java#record

Next, add a Codec that reflects what the constructor accepts:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/TagsEmptyResourceCondition.java#codec

Next, we'll add a `test` method. We'll invert the logic from `TagsPopulated`:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/TagsEmptyResourceCondition.java#test

### Registering Your Condition {#registering-your-condition}

Back in `ModResourceConditions`, we can now register our resource condition:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/ModResourceConditions.java#register

This condition type can then be referenced from `TagsEmptyResourceCondition` as well:

<<< @/reference/latest/src/main/java/com/example/docs/conditions/TagsEmptyResourceCondition.java#type

Be sure to call `ModResourceConditions.register` in your [mod's initializer](./getting-started/project-structure#entrypoints):

<<< @/reference/latest/src/main/java/com/example/docs/conditions/ExampleModResourceConditions.java#init

### Using Your Condition {#using-your-condition}

Now, we have a condition that fails if the specified registry contains a valid tag. For example, the following will succeed if the `example-mod:smelly_items` item tag has no contents loaded, either because it's empty or broken:

<<< @/reference/latest/src/main/generated/reports/example-mod/resource_condition_examples/tags_empty.json
