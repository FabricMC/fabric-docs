---
title: Resource Conditions
description: A guide to allowing your mod's data to load conditionally.
authors:
  - cassiancc
---

When designing integrations with other mods, a common need is a way to define your mod's JSON resources should be loaded. For this reason, the Fabric Resource Conditions API was designed.

By default, this API can be used with recipes, advancements, loot tables, predicates, and item modifiers. Fabric's data generation APIs support outputting resource conditions, but this guide will primarily focus on handwriting them. Load conditions can be added to the root of a JSON file.

::: details A recipe with a condition set to always load.

```json
{
  "fabric:load_conditions": [
    {
      "condition": "fabric:true"
    }
  ],
  "type": "minecraft:crafting_shapeless",
  "ingredients": [
    "minecraft:stick"
  ],
  "result": {
    "id": "minecraft:diamond"
  }
}
```

:::

## Built-in Conditions {#built-in}

Fabric API provides nine built-in conditions for your mod to use.

### Operators {#operators}

The first four operators are basic boolean operators. They're best used in tandem with the operators seen below.

#### True {#true}

Always returns true.

```json
"fabric:load_conditions": [
    {
      "condition": "fabric:true"
    }
  ]
```

#### Not {#not}

Always returns the inverse of the load condition specified below. As an example, the condition below will return `false`.

```json
 "fabric:load_conditions": [
    {
      "condition": "fabric:not",
      "value": {
        "condition": "fabric:true"
      }
    }
  ]
```

#### Or {#or}

This condition takes in an array of load conditions, and will return `true` if any conditions match. As an example, the condition below will return `true`.

```json
"fabric:load_conditions": [
  {
    "condition": "fabric:or",
    "values": [
      {
        "condition": "fabric:true"
      },
      {
        "condition": "fabric:not",
        "value": {
          "condition": "fabric:true"
        }
      }
    ]
  }
]
```

#### And {#and}

This condition takes in an array of load conditions, and will only return `true` if all conditions match. As an example, the condition below will return `false`.

```json
"fabric:load_conditions": [
  {
    "condition": "fabric:and",
    "values": [
      {
        "condition": "fabric:true"
      },
      {
        "condition": "fabric:not",
        "value": {
          "condition": "fabric:true"
        }
      }
    ]
  }
]
```

### Mod Loaded Checks {#mods-loaded}

The next two checks focus on conditionally loading resources based on the presence of other mods.

#### All Mods Loaded {#all-mods-loaded}

Returns `true` when all mods in the array are present. As an example, the condition below will return `true` only if both `example-mod` and `another-mod` are loaded.

```json
 "fabric:load_conditions": [
    {
      "condition": "fabric:all_mods_loaded",
      "values": [
        "example-mod",
        "another-mod"
      ]
    }
  ]
```

#### Any Mods Loaded {#any-mods-loaded}

Returns `true` if any of the mods in the array are present. As an example, the condition below will return `true` if either `example-mod` and `another-mod` are loaded.

```json
 "fabric:load_conditions": [
    {
      "condition": "fabric:any_mods_loaded",
      "values": [
        "example-mod",
        "another mod"
      ]
    }
  ]
```

### Tags Populated {#tags-populated}

Returns `true` if the specified registry contains a tag. As an example, the condition below will return `true` only if the `example-mod:smelly_items` item tag has loaded contents. If the tag is empty or broken, it will return `false`.

```json
"fabric:load_conditions": [
    {
      "condition": "fabric:tags_populated",
      "registry": "minecraft:item",
      "values": [
        "example-mod:smelly_items"
      ]
    }
  ]
```

### Features Enabled {#features-enabled}

Returns `true` if the following [feature flags](https://minecraft.wiki/w/Experiments#Java_Edition) are enabled. As an example, the condition below will return `true` only if both `minecraft:vanilla` and `minecraft:redstone_experiments` are enabled.

```json
"fabric:load_conditions": [
    {
      "condition": "fabric:features_enabled",
      "features": [
        "minecraft:vanilla",
        "minecraft:redstone_experiments"
      ]
    }
  ]
```

### Registry Contains {#registry-contains}

Returns `true` if the registry contains the specified ID. As an example, the condition below will return `true` only if `minecraft:cobblestone` exists in the registry.

```json
"fabric:load_conditions": [
    {
      "condition": "fabric:registry_contains",
      "values": [
        "minecraft:cobblestone"
      ]
    }
  ]
```

## Custom Conditions {#custom-conditions}

::: info PREREQUISITES

You must first understand [codecs](./codecs) to create a resource condition.

:::

Fabric also provides the flexibility to create your own resource conditions. To demonstrate this, we'll create a copy of the Tags Populated condition that will only return true if a tag is empty. Note that in reality, you would use the `not` condition for this.

### Preparing Your Condition {#preparing-your-condition}

For simplicity, we'll be creating a helper method that creates your resource condition from a name and a [Codec](./codecs).

You should put this method in a class called `ModResourceConditions` (or whatever you want to name it).

Fabric does the same with its built-in conditions; you can refer to the `DefaultResourceConditionTypes` class to see this in action.

<<< @/reference/latest/src/main/java/com/example/docs/conditions/ModResourceConditions.java#create

### Creating Your Condition {#creating-your-condition}

We'll create a new class for the resource condition, named `TagsEmptyResourceCondition`. A resource condition consists of three parts, a constructor that accepts values, a codec to serialize those values, and a `test` method to handle what to do with the values.

First, create a new `record` that implements accepts a `Identifier` for the registry and a list of `Identifier`s for the tags.

<<< @/reference/latest/src/main/java/com/example/docs/conditions/TagsEmptyResourceCondition.java#record

Next, add a Codec that reflects what the constructor accepts.

<<< @/reference/latest/src/main/java/com/example/docs/conditions/TagsEmptyResourceCondition.java#codec

Next, we'll add a `test` method. We'll reuse the logic from TagsPopulated.

<<< @/reference/latest/src/main/java/com/example/docs/conditions/TagsEmptyResourceCondition.java#test

### Registering Your Condition {#registering-your-condition}

Returning to `ModResourceConditions`, we can now register our resource condition. Fabric does this in a `ResourceConditionsImpl` class, but we'll keep it in `ModResourceConditions`.

<<< @/reference/latest/src/main/java/com/example/docs/conditions/ModResourceConditions.java#register

This condition type can then be added to `TagsEmptyResourceCondition` as well.

<<< @/reference/latest/src/main/java/com/example/docs/conditions/TagsEmptyResourceCondition.java#type

Be sure to call `ModResourceConditions.register` in your mod's initializer, otherwise it won't be registered on time and you'll see an error in the logs when you try to use the condition.

<<< @/reference/latest/src/main/java/com/example/docs/conditions/ExampleModResourceConditions.java#init

### Using Your Condition {#using-your-condition}

Now, we have a condition that returns `false` if the specified registry contains a tag. As an example, the condition below will return `true` only if the `example-mod:smelly_items` item tag has no loaded contents. If the tag is empty or broken, it will return `true`.

```json
"fabric:load_conditions": [
    {
      "condition": "example_mod:tags_empty",
      "registry": "minecraft:item",
      "values": [
        "example-mod:smelly_items"
      ]
    }
  ]
```
