---
title: Entity Attributes
description: Learn how to add custom attributes to entities.
authors:
- cprodhomme
---

Custom entity attributes allow mod developers to define novel properties that entities can possess. Using Fabric, you can create and register attributes that enhance gameplay mechanics.

## Creating a Custom Attribute {#creating-a-custom-attribute}

Let's create a custom entity attribute named `AGGRO_RANGE`. This attribute will control the distance an entity can detect and react to potential threats from.

### Define the Attribute Class {#define-the-attribute-class}

Begin by creating a Java class to manage the definition and registration of your attributes under your mod's code structure.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/entity/attribute/ModEntityAttributes.java)

### Apply the Attribute to an Entity {#apply-the-attribute}

Attributes need to be attached to an entity to take effect. This is typically done in the method where an entity's attributes are built or modified.

```java
public static DefaultAttributeContainer.Builder createEntityAttributes() {
    return MobEntity.createMobAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.22D)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D)
        .add(ModEntityAttributes.AGGRO_RANGE, 8.0);
}
```

### Translation for Custom Attributes {#attribute-translation}

To display the attribute name in a human-readable format, you must modify your resource pack's `en_us.json`:

```json
{
    "attribute.name.fabricdocsreference.aggro_range": "Aggro Range"
}
```

### Initialization {#initialization}

Ensure that your custom attributes are initialized during mod startup:

```java
public class FabricDocsReference implements ModInitializer {
    @Override
    public void onInitialize() {
        ModEntityAttributes.initialize();
        // Other initializations
    }
}
```
