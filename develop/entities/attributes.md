---
title: Entity Attributes
description: Learn how to add custom attributes to entities.
authors:
  - cassiancc
  - cprodhomme
---

Attributes determine the properties that your modded entity can possess. Using Fabric, you can create your own custom attributes that enhance gameplay mechanics and apply vanilla ones as well.

## Creating a Custom Attribute {#creating-a-custom-attribute}

Let's create a custom attribute named `AGGRO_RANGE`. This attribute will control the distance an entity can detect and react to potential threats from.

### Defining the Attribute Class {#define-the-attribute-class}

Begin by creating a Java class to manage the definition and registration of your attributes under your mod's code structure. This example will create the following functions in a class named `ModAttributes`.

First, start with a basic helper method to register your mod's attributes. This method will accept the following parameters and register an attribute.

- A `String` that will be the name of your attribute
- A `double` that will be the default value of the attribute
- A `double` that will be the lowest value your attribute will reach
- A `double` that will be the highest value your attribute will reach
- A `boolean` that dictates whether the attribute will be synced to clients

@[code lang=java transcludeWith=:::register](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java)

We'll then register an attribute named `AGGRO_RANGE` with the name `aggro_range`, a default value of `8.0`, a minimum value of `0`, and a maximum value set as high as it can be. This attribute will not be synced to players.

@[code lang=java transcludeWith=:::attributes](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java)

### Translating Custom Attributes {#attribute-translation}

To display the attribute name in a human-readable format, you must modify `assets/example-mod/lang/en_us.json` to include:

```json
{
  "attribute.name.example-mod.aggro_range": "Aggro Range"
}
```

### Initialization {#initialization}

To make sure the attribute is registered properly, you'll need to ensure it is initialized during mod startup. This can be done by adding a public static initialize method to your class and call it from your [mod's initializer](../getting-started/project-structure#entrypoints) class. Currently, this method doesn't need anything inside it.

@[code lang=java transcludeWith=:::initialize](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModAttributes.java)

@[code lang=java transcludeWith=:::init](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ExampleModAttributes.java)

Calling a method on a class statically initializes it if it hasn't been previously loaded - this means that all `static` fields are evaluated. This is what this dummy `initialize` method is for.

## Applying Attributes {#apply-the-attribute}

Attributes need to be attached to an entity to take effect. This is typically done in the method where an entity's attributes are built or modified.

Vanilla provides attributes as well, including [max health](https://minecraft.wiki/w/Attribute#Max_health), [movement speed](https://minecraft.wiki/w/Attribute#Movement_speed), and [attack damage](https://minecraft.wiki/w/Attribute#Attack_damage) as seen below. For a full list, see the vanilla `Attributes` class and the [Minecraft Wiki](https://minecraft.wiki/w/Attribute).

As a demo, we'll include maximum health, movement speed, attack damage and the aggro range attribute as created earlier.

<!-- TODO: move to the reference mod -->

```java
public static AttributeSupplier.Builder createEntityAttributes() {
    return Mob.createMobAttributes()
        .add(Attributes.MAX_HEALTH, 25.0)
        .add(Attributes.MOVEMENT_SPEED, 0.22)
        .add(Attributes.ATTACK_DAMAGE, 3.0)
        .add(ModAttributes.AGGRO_RANGE, 8.0);
}
```

## Reading and Modifying Attributes {#reading-modifying-attributes}

An attribute by itself is just data attached to an entity. For it to be useful, we need to be able to read from and write to it. There are two main ways to do this: getting the `AttributeInstance` on the entity, or getting the value directly.

```java
entity.getAttribute(ModAttributes.AGGRO_RANGE) // returns an `AttributeInstance`
entity.getAttributeValue(ModAttributes.AGGRO_RANGE) // returns a double with the current value
entity.getAttributeBaseValue(ModAttributes.AGGRO_RANGE) // returns a double with the base value
```

An `AttributeInstance` allows more flexibility, such as setting an `AttributeModifier` on the attribute, using one of the [three vanilla attribute modifier operations](https://minecraft.wiki/w/Attribute#Operations). Modifiers can be permanent (saved to NBT) or transitive (not saved to NBT) and are added using `addPermanentModifier` or `addTransitiveModifier`, respectively.

```java
attribute.addPermanentModifier(
    new AttributeModifier(
        Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "increased_range"), // the ID of your modifier, should be static so it can be removed
        8, // how much to modify it
        AttributeModifier.Operation.ADD_VALUE // what operator to use, see the wiki page linked above
    ));
```

Once you have access to the attribute value, you can use it in your entity's AI.
