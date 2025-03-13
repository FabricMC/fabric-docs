---
title: Custom Entities
description: Learn how to do custom entities.
authors:
  - JaaiDead
  - Earthcomputer
---

<!-- TODO: Add the referances , flow should be (Create Entity,Add Animations and goals and nbt tracker ,Explain arrtibiute and goals , Explain EntityTypesClass , Explain Entity Rendering(new stff ) , Explain how to add models in rendering and size also comes under it ,Explain assing the spawn egg and stuff)   -->

Entities are dynamic, interactive objects in the game that are not part of the terrain (like blocks). Entities can move around and interact with the world in various ways.

## Your First Entity {#your-first-entity}

This tutorial will walk you through the process of creating a custom _Mini Iron Golem_. This entity will have fun animations.

Entities are a movable object in a world with logic attached to them. A few examples include:
 `NPCs`,`Minecarts`,`Arrows`,`Boats`.

## Peparing Your First Entity {#preparing-your-first-entity}

The first step in creating a custom entity is defining its class and registering it with the game. For now ignore the animations and tracked data we will use it later on.

Now , add attributes to the entity in the class `MiniGolemEntity`

Attributes decide the Health , Speed & Tempt Range.

@[code transcludeWith=:::registerclass](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

To register your entity, you create a separate class `ModEntityTypes` where you register the entity type, set its dimensions, and register its attributes.

## Adding Goals {#adding-goals}

Goals are objective/aim of a entity which provides an defined set of behaviour to an entity. Goals have a certain priority it ranges from `0` being the highest and subsequently reducing in priority as the number increases. vz

To add goals to the entity , you need to create `initGoals` class which will contain the goals for the entity.

@[code transcludeWith=:::goals](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

::: info

1. `TemptGoal` - The entity is attacted towards a player holding an item.
2. `WanderAroundGoal` - Walks/Wanders around the world.
3. `LookAtEntityGoal` - To look at the cow entity.
4. `LookAroundGoal` - To look in random directions.

:::
<!-- TODO: Add stuff about the entity types asked for helpp**-->

## Creating Rendering {#creating-rendering}

Rendering refers to the process of converting game data—such as blocks, `entities`, and environments—into visual representations displayed on the player's screen. This involves determining how objects are illuminated, shaded, and textured.

::: tip

Thr entity rendering is always handled on the client side. The server manages the entity's logic and behavior, while the client is responsible for displaying the entity's model, texture, and animations.

:::

### Setting up Model {#setting-up-model}

The `MiniGolemEntityModel` defines how your entity looks by describing its shape and parts.

@[code transcludeWith=:::model](@/reference/latest/src/client/java/com/example/docs/entity/model/MiniGolemEntityModel.java)
