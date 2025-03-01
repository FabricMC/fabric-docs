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

### Peparing Your First Entity {#preparing-your-first-entity}

The MiniGolemEntity is a custom mob based on `PathAwareEntity`.

@[code lang=java transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)
