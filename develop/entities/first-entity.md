---
title: Creating Your First Entity
description: Learn how to register a simple entity and how to give it goals, render, model, and animate it.
authors:
  - cassiancc
  - Earthcomputer
  - JaaiDead
---

Entities are dynamic, interactive objects in the game that are not part of the terrain (like blocks). Entities can move around and interact with the world in various ways.

## Your First Entity {#your-first-entity}

This tutorial will walk you through the process of creating a custom _Mini Golem_. This entity will have fun animations.

An entity is a movable object in a world with logic attached to them. A few examples include:

- `Villager`, `Pig`, and `Goat` are all examples of a `Mob`, the most common type of entity - something alive.
- `Zombie` and `Skeleton` are all examples of a `Monster`, a variant of an `Entity` that is hostile to the `Player`.
- `Minecart` and `Boat` are examples of a `VehicleEntity`, which has special logic for accepting player input.

This tutorial will walk you through the creation of a `PathfinderEntity`, used by most mobs with pathfinding like `Zombie` and `Villager` alike.

## Preparing Your First Entity {#preparing-your-first-entity}

The first step in creating a custom entity is defining its class and registering it with the game.

We'll create the class `MiniGolemEntity` for our entity, and start by giving it attributes. [Attributes](attributes) decide the Maximum Health, Movement Speed & Tempt Range of the entity.

@[code transcludeWith=:::registerclass](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

To register your entity, it's recommended to create a separate class, `ModEntityTypes`, where you register any and all entity types, set their dimensions, and register their attributes.

@[code transcludeWith=:::types](@/reference/latest/src/main/java/com/example/docs/entity/ModEntityTypes.java)

## Adding Goals {#adding-goals}

Goals are the system that handle an entity's objective/aim, providing them with a defined set of behaviour. Goals have a certain priority: goals with a lower value for the priority are prioritized over goals with a higher value for the priority.

To add goals to the entity, you need to create a `registerGoals` method in your entity's class that defines the goals for the entity.

@[code transcludeWith=:::goals](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

::: info

1. `TemptGoal` - The entity is attracted towards a player holding an item.
2. `RandomStrollGoal` - Walks/Wanders around the world.
3. `LookAtPlayerGoal` - To look at the `Cow` entity.
4. `RandomLookAroundGoal` - To look in random directions.

:::

## Creating Rendering {#creating-rendering}

Rendering refers to the process of converting game data such as blocks, entities, and environments into visual representations displayed on the player's screen. This involves determining how objects are illuminated, shaded, and textured.

::: tip
Entity rendering is always handled on the client side. The server manages the entity's logic and behavior, while the client is responsible for displaying the entity's model, texture, and animations.
:::

Rendering has multiple steps involving their own classes, but we'll start with the `EntityRenderState` class.

@[code transcludeWith=:::entitystate](@/reference/latest/src/client/java/com/example/docs/entity/state/MiniGolemEntityRenderState.java)

The render state determines how the entity is visually represented, including animation states such as movement and idle behaviors.

### Setting up the Model {#setting-up-model}

The `MiniGolemEntityModel` class defines how your entity looks by describing its shape and parts. Models are generally created in third-party tools like [Blockbench](https://web.blockbench.net), rather than being written by hand. Nonetheless, this tutorial will go through a manual example to show you how it works.

::: warning
Blockbench supports multiple [mappings](../migrating-mappings/#mappings) (such as Mojang Mappings, Yarn, and others). Ensure you select the correct mapping that matches your development environment - this tutorial uses Mojang Mappings.

Mismatched mappings can cause errors when integrating Blockbench generated code.
:::

@[code transcludeWith=:::model1](@/reference/latest/src/client/java/com/example/docs/entity/model/MiniGolemEntityModel.java)

The `MiniGolemEntityModel` class defines the visual model for a Mini Golem entity. It extends EntityModel, specifying how the entity's body parts (body, head, left leg, and right leg) are named.

@[code transcludeWith=:::model_texture_data](@/reference/latest/src/client/java/com/example/docs/entity/model/MiniGolemEntityModel.java)

This method defines the Mini Golem's 3D model by creating its body, head, and legs as cuboids, setting their positions and texture mappings, and returning a `LayerDefinition` for rendering.

Each part is added with a offset point for proper animation and alignment, ensuring the model appears correctly in-game.

::: info
The higher Y values in the model, the lower you are in the entity. This is the reverse compared to in-game coordinates.
:::

We'll now need to create a `ModEntityModelLayers` class in the client package. This entity only has a single texture layer, but other entities may use multiple - think of the secondary skin layer on mobs like the `Player` or a `Spider`'s eyes.

@[code transcludeWith=:::model_layer](@/reference/latest/src/client/java/com/example/docs/entity/model/ModEntityModelLayers.java)

This class must then be initialized in the mod's client initializer.

@[code transcludeWith=::register_client](@/reference/latest/src/client/java/com/example/docs/entity/ExampleModCustomEntityClient.java)

### Setting up the Texture {#setting-up-texture}

::: warning
The size of the texture should match the values in the `LayerDefinition.create(modelData, 64, 32);`, 64 pixels wide and 32 pixels tall. If you need a bigger texture, then don't forget to change the size in `LayerDefinition.create` to match.
:::

Each model part / box is expecting a net on the texture in a particular location. By default, it's expecting it at `0, 0` (the top left), but this can be changed by calling the texOffs function in CubeListBuilder.

For example purposes, you can use this example texture for `assets/example-mod/textures/entity/mini_golem.png`

<DownloadEntry visualURL="/assets/develop/entity/mini_golem.png" downloadURL="/assets/develop/entity/mini_golem_small.png">Texture</DownloadEntry>

### Creating the Renderer {#creating-the-renderer}

A mob's entity renderer enables you to view your entity in-game. We'll create a new class, `MiniGolemEntityRenderer`, which will tell Minecraft what texture, model and entity render state to use for this entity.

@[code transcludeWith=:::renderer](@/reference/latest/src/client/java/com/example/docs/entity/renderer/MiniGolemEntityRenderer.java)

This is also where the shadow radius is set, for this entity that will be `0.375f`.

### Adding Walking Animations {#walking-animations}

The following code can be added to the `MiniGolemEntityModel` class to give the entity a walking animation.

@[code transcludeWith=:::model_animation](@/reference/latest/src/client/java/com/example/docs/entity/model/MiniGolemEntityModel.java)

Most of the math is provided by Blockbench.

Looking into the game, you now have all you need to spawn the entity with `/summon example-mod:mini_golem`!

![Spawn Egg showcase](/assets/develop/entity/mini_golem_summoned.png)

## Adding a Dancing Animation {#adding-a-dancing-animation}

Let's make this entity a bit more lively by giving it a dancing animation. We'll create a `MiniGolemAnimations` class with a simple animation that allows the Mini Golem to dance.

@[code transcludeWith=:::dancing_animation](@/reference/latest/src/client/java/com/example/docs/entity/animation/MiniGolemAnimations.java)

### Using Synched Entity Data {#using-synched-entity-data}

We can store data on the entity itself by defining an `EntityDataAccessor` for it. This data will be synchronized with the client, making it a good place to define our dancing state.

@[code transcludeWith=:::datatracker](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

### Storing Data to NBT {#storing-data}

For persistent data that can be saved after the game is closed, we'll start by overriding the `addAdditionalSaveData` and `readAdditionalSaveData` methods in `MiniGolemEntity`. We can use this to store the amount of time remaining in the dancing animation.

@[code transcludeWith=:::savedata](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

Now, whenever the entity is loaded, it will restore the state that it was left in.

## Adding the Spawn Egg {#adding-spawn-egg}

All that's left to do now is add a Spawn Egg for our entity. We'll register it in our `ModItems` class, using the helper method discussed in [Creating Your First Item](../items/first-item).

@[code transcludeWith=:::spawn_egg](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

From there, it just needs a [client item, item model, and texture](../items/first-item#adding-a-client-item-texture-and-model). A basic client item and item model as discussed in the linked article will work fine, and you can use this example texture for `assets/example-mod/textures/item/mini_golem_spawn_egg.png`

<DownloadEntry visualURL="/assets/develop/entity/mini_golem_spawn_egg.png" downloadURL="/assets/develop/entity/mini_golem_spawn_egg_small.png">Texture</DownloadEntry>

Looking into the game, you now have a spawn egg that can spawn the entity!

![Spawn Egg showcase](/assets/develop/entity/mini_golem_spawned.png)
