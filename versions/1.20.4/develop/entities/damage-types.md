---
title: Damage Types
description: Learn how to add custom damage types.
authors:
- dicedpixels
- hiisuuii
- mattidragon

search: false
---

# Damage Types {#damage-types}

Damage types define types of damage that entities can take. Since Minecraft 1.19.4, the creation of new damage types has
become data-driven, meaning they are created using JSON files.

## Creating a Damage Type {#creating-a-damage-type}

Let's create a custom damage type called _Tater_. We start by creating a JSON file for your custom damage. This file
will
be placed in your mod's `data` directory, in a subdirectory named `damage_type`.

```:no-line-numbers
resources/data/fabric-docs-reference/damage_type/tater.json
```

It has the following structure:

@[code lang=json](@/reference/1.20.4/src/main/generated/data/fabric-docs-reference/damage_type/tater.json)

This custom damage type causes 0.1 increase
in [hunger exhaustion](https://minecraft.wiki/w/Hunger#Exhaustion_level_increase) each time a player takes damage, when
the damage is caused by a living, non-player source (e.g., a block). Additionally, the amount of damage dealt will scale
with the world's difficulty

::: info

Refer to the [Minecraft Wiki](https://minecraft.wiki/w/Damage_type#JSON_format) for all the possible keys and values.

:::

### Accessing Damage Types Through Code {#accessing-damage-types-through-code}

When we need to access our custom damage type through code, we will use it's `RegistryKey` to build an instance
of `DamageSource`.

The `RegistryKey` can be obtained as follows:

@[code lang=java transcludeWith=:::1](@/reference/1.20.4/src/main/java/com/example/docs/damage/FabricDocsReferenceDamageTypes.java)

### Using Damage Types {#using-damage-types}

To demonstrate the use of custom damage types, we will use a custom block called _Tater Block_. Let's make is so that
when a living entity steps on a _Tater Block_, it deals _Tater_ damage.

You can override `onSteppedOn` to inflict this damage.

We start by creating a `DamageSource` of our custom damage type.

@[code lang=java transclude={21-24}](@/reference/1.20.4/src/main/java/com/example/docs/damage/TaterBlock.java)

Then, we call `entity.damage()` with our `DamageSource` and an amount.

@[code lang=java transclude={25-25}](@/reference/1.20.4/src/main/java/com/example/docs/damage/TaterBlock.java)

The complete block implementation:

@[code lang=java transcludeWith=:::1](@/reference/1.20.4/src/main/java/com/example/docs/damage/TaterBlock.java)

Now whenever a living entity steps on our custom block, it'll take 5 damage (2.5 hearts) using our custom damage type.

### Custom Death Message {#custom-death-message}

You can define a death message for the damage type in the format of `death.attack.<message_id>` in our
mod's `en_us.json` file.

@[code lang=json transclude={4-4}](@/reference/1.20.4/src/main/resources/assets/fabric-docs-reference/lang/en_us.json)

Upon death from our damage type, you'll see the following death message:

![Effect in player inventory](/assets/develop/tater-damage-death.png)

### Damage Type Tags {#damage-type-tags}

Some damage types can bypass armor, bypass status effects, and such. Tags are used to control these kinds of properties
of damage types.

You can find existing damage type tags in `data/minecraft/tags/damage_type`.

::: info

Refer to the [Minecraft Wiki](https://minecraft.wiki/w/Tag#Damage_types) for a comprehensive list of damage type
tags.

:::

Let's add our Tater damage type to the `bypasses_armor` damage type tag.

To add our damage type to one of these tags, we create a JSON file under the `minecraft` namespace.

```:no-line-numbers
data/minecraft/tags/damage_type/bypasses_armor.json
```

With the following content:

@[code lang=json](@/reference/1.20.4/src/main/generated/data/minecraft/tags/damage_type/bypasses_armor.json)

Ensure your tag does not replace the existing tag by setting the `replace` key to `false`.
