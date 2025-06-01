---
title: Custom Entities
description: Learn how to do custom entities.
authors:
  - JaaiDead
  - Earthcomputer
---
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

@[code transcludeWith=:::types](@/reference/latest/src/main/java/com/example/docs/entity/ModEntityTypes.java)

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
<!-- TODO: Add stuff about the entity types ask for helpp**-->

## Creating Rendering {#creating-rendering}

Rendering refers to the process of converting game data—such as blocks, `entities``, and environments—into visual representations displayed on the player's screen. This involves determining how objects are illuminated, shaded, and textured.

::: tip

The entity rendering is always handled on the client side. The server manages the entity's logic and behavior, while the client is responsible for displaying the entity's model, texture, and animations.

:::

Rendering has multiple steps lets now setup a few classes which will of help later on , this is `EntityRenderState` class.

@[code transcludeWith=:::entitystate](@/reference/latest/src/client/java/com/example/docs/entity/state/MiniGolemEntityRenderState.java)

The render state determines how the entity is visually represented, including animation states such as movement and idle behaviors.

### Setting up Model {#setting-up-model}

The `MiniGolemEntityModel` defines how your entity looks by describing its shape and parts.

::: warning
  Blockbench supports multiple mappings (such as Mojmap, Yarn, and others). Ensure you select the correct mapping that matches your development environment.

  Mismatched mappings can cause errors when integrating Blockbench generated code.
:::

@[code transcludeWith=:::model1](@/reference/latest/src/client/java/com/example/docs/entity/model/MiniGolemEntityModel.java)

The `MiniGolemEntityModel` class defines the visual model for a Mini Golem entity. It extends EntityModel, specifying how the entity's body parts (head, left leg, and right leg) are named.

@[code transcludeWith=:::model_texture_data](@/reference/latest/src/client/java/com/example/docs/entity/model/MiniGolemEntityModel.java)

This method defines the Mini Golem's 3D model by creating its body, head, and legs as cuboids, setting their positions and texture mappings, and returning a `TexturedModelData` for rendering.

Each part is added with a pivot point for proper animation and alignment, ensuring the model appears correctly in-game.
