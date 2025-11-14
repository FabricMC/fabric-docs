---
title: Entity Attributes
description: Learn how to add custom attributes to entities.
authors:
- cassiancc
- cprodhomme
---

Entity attributes determine the properties that your modded entity can possess. Using Fabric, you can create your own custom attributes that enhance gameplay mechanics and apply vanilla ones as well.

## Creating a Custom Attribute {#creating-a-custom-attribute}

Let's create a custom entity attribute named `AGGRO_RANGE`. This attribute will control the distance an entity can detect and react to potential threats from.

### Defining the Attribute Class {#define-the-attribute-class}

Begin by creating a Java class to manage the definition and registration of your attributes under your mod's code structure.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModEntityAttributes.java)

### Applying Attributes {#apply-the-attribute}

Attributes need to be attached to an entity to take effect. This is typically done in the method where an entity's attributes are built or modified.

Vanilla provides attributes as well, including [max health](https://minecraft.wiki/w/Attribute#Max_health), [movement speed](https://minecraft.wiki/w/Attribute#Movement_speed), and [attack damage](https://minecraft.wiki/w/Attribute#Attack_damage) as seen below. For a full list, see the vanilla `EntityAttributes` class and the [Minecraft Wiki](https://minecraft.wiki/w/Attribute).

```java
public static DefaultAttributeContainer.Builder createEntityAttributes() {
    return MobEntity.createMobAttributes()
        .add(EntityAttributes.MAX_HEALTH, 25.0)
        .add(EntityAttributes.MOVEMENT_SPEED, 0.22D)
        .add(EntityAttributes.ATTACK_DAMAGE, 3.0D)
        .add(ModEntityAttributes.AGGRO_RANGE, 8.0);
}
```

### Translating Custom Attributes {#attribute-translation}

To display the attribute name in a human-readable format, you must modify your mod's `assets/mod-id/lang/en_us.json` to include:

```json
{
    "attribute.name.examplemod.aggro_range": "Aggro Range"
}
```

### Initialization {#initialization}

You'll also need to ensure that your custom attributes are initialized during mod startup. This can be done by adding a public static initialize method to your class and call it from your [mod's initializer](../getting-started/project-structure#entrypoints) class. Currently, this method doesn't need anything inside it.

```java
public static void initialize() {
}
```

```java
public class ExampleMod implements ModInitializer {
    @Override
    public void onInitialize() {
        ModEntityAttributes.initialize();
        // Other initializations
    }
}
```
