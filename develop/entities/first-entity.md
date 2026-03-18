---
title: Creating Your First Entity
description: Learn how to register a simple entity and how to give it goals, render, model, and animate it.
authors:
  - cassiancc
  - Earthcomputer
  - JaaiDead
resources:
  https://docs.neoforged.net/docs/entities/: Entities - NeoForge Docs
  https://www.desmos.com/calculator/9r6lh5knfu: Entity Walk Animation - Desmos
---

Entities are dynamic, interactive objects in the game that are not part of the terrain (like blocks). Entities can move around and interact with the world in various ways. A few examples include:

- `Villager`, `Pig`, and `Goat` are all examples of a `Mob`, the most common type of entity - something alive.
- `Zombie` and `Skeleton` are examples of a `Monster`, a variant of an `Entity` that is hostile to the `Player`.
- `Minecart` and `Boat` are examples of a `VehicleEntity`, which has special logic for accepting player input.

This tutorial will walk you through the process of creating a custom _Mini Golem_. This entity will have fun animations. It will be a `PathfinderMob`, which is the class used by most mobs with pathfinding, such as `Zombie` and `Villager`.

## Preparing Your First Entity {#preparing-your-first-entity}

The first step in creating a custom entity is defining its class and registering it with the game.

We'll create the class `MiniGolemEntity` for our entity, and start by giving it attributes. [Attributes](attributes) decide various things including the maximum health, movement speed, and tempt range of the entity.

@[code transcludeWith=:::registerclass](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

To register your entity, it's recommended to create a separate class, `ModEntityTypes`, where you register any and all entity types, set their sizes, and register their attributes.

@[code transcludeWith=:::types](@/reference/latest/src/main/java/com/example/docs/entity/ModEntityTypes.java)

## Adding Goals {#adding-goals}

Goals are the system that handle an entity's objective/aim, providing them with a defined set of behavior. Goals have a certain priority: goals with a lower value for the priority are prioritized over goals with a higher value for the priority.

To add goals to the entity, you need to create a `registerGoals` method in your entity's class that defines the goals for the entity.

@[code transcludeWith=:::goals](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

::: info

1. `TemptGoal` - The entity is attracted towards a player holding an item.
2. `RandomStrollGoal` - Walks/wanders around the world.
3. `LookAtPlayerGoal` - Despite the name, this accepts any entity. Used here to look at the `Cow` entity.
4. `RandomLookAroundGoal` - To look in random directions.

:::

## Creating Rendering {#creating-rendering}

Rendering refers to the process of converting game data such as blocks, entities, and environments into visual representations displayed on the player's screen. This involves determining how objects are illuminated, shaded, and textured.

::: info

Entity rendering is always handled on the client side. The server manages the entity's logic and behavior, while the client is responsible for displaying the entity's model, texture, and animations.

:::

Rendering has multiple steps involving their own classes, but we'll start with the `EntityRenderState` class.

@[code transcludeWith=:::entitystate](@/reference/latest/src/client/java/com/example/docs/entity/state/MiniGolemEntityRenderState.java)

Data stored on the render state is used to determine how the entity is visually represented, including animation states such as movement and idle behaviors.

### Setting up the Model {#setting-up-model}

The `MiniGolemEntityModel` class defines how your entity looks by describing its shape and parts. Models are generally created in third-party tools like [Blockbench](https://www.blockbench.net/), rather than being written by hand. Nonetheless, this tutorial will go through a manual example to show you how it works.

::: warning

Blockbench supports multiple [mappings](../migrating-mappings/#mappings) (such as Mojang Mappings, Yarn, and others). Ensure you select the correct mapping that matches your development environment - this tutorial uses Mojang Mappings.

Mismatched mappings can cause errors when integrating Blockbench generated code.

:::

@[code transcludeWith=:::model1](@/reference/latest/src/client/java/com/example/docs/entity/model/MiniGolemEntityModel.java)

The `MiniGolemEntityModel` class defines the visual model for a Mini Golem entity. It extends `EntityModel`, specifying how the entity's body parts (body, head, left leg, and right leg) are named.

@[code transcludeWith=:::model_texture_data](@/reference/latest/src/client/java/com/example/docs/entity/model/MiniGolemEntityModel.java)

This method defines the Mini Golem's 3D model by creating its body, head, and legs as cuboids, setting their positions and texture mappings, and returning a `LayerDefinition` for rendering.

Each part is added with an offset point which is the origin for all of the transformations applied to that part. All other coordinates in the model part are measured relative to this offset point.

::: warning

Higher Y values in the model correspond to the **bottom** of the entity. This is the reverse compared to in-game coordinates.

:::

We'll now need to create a `ModEntityModelLayers` class in the client package. This entity only has a single texture layer, but other entities may use multiple - think of the secondary skin layer on entities like the `Player` or a `Spider`'s eyes.

@[code transcludeWith=:::model_layer](@/reference/latest/src/client/java/com/example/docs/entity/model/ModEntityModelLayers.java)

This class must then be initialized in the mod's client initializer.

@[code transcludeWith=::register_client](@/reference/latest/src/client/java/com/example/docs/entity/ExampleModCustomEntityClient.java)

### Setting up the Texture {#setting-up-texture}

::: tip

The size of the texture should match the values in the `LayerDefinition.create(modelData, 64, 32)`: 64 pixels wide and 32 pixels tall. If you need a differently-sized texture, then don't forget to change the size in `LayerDefinition.create` to match.

:::

Each model part / box is expecting a net on the texture in a particular location. By default, it's expecting it at `0, 0` (the top left), but this can be changed by calling the `texOffs` function in `CubeListBuilder`.

For example purposes, you can use this texture for `assets/example-mod/textures/entity/mini_golem.png`

<DownloadEntry visualURL="/assets/develop/entity/mini_golem.png" downloadURL="/assets/develop/entity/mini_golem_small.png">Texture</DownloadEntry>

### Creating the Renderer {#creating-the-renderer}

A entity's renderer enables you to view your entity in-game. We'll create a new class, `MiniGolemEntityRenderer`, which will tell Minecraft what texture, model, and entity render state to use for this entity.

@[code transcludeWith=:::renderer](@/reference/latest/src/client/java/com/example/docs/entity/renderer/MiniGolemEntityRenderer.java)

This is also where the shadow radius is set, for this entity that will be `0.375f`.

This renderer must then be registered in the mod's client initializer.

@[code transcludeWith=::register_renderer](@/reference/latest/src/client/java/com/example/docs/entity/ExampleModCustomEntityClient.java)

### Adding Walking Animations {#walking-animations}

The following code can be added to the `MiniGolemEntityModel` class to give the entity a walking animation.

@[code transcludeWith=:::model_animation](@/reference/latest/src/client/java/com/example/docs/entity/model/MiniGolemEntityModel.java)

To start, apply the yaw and pitch to the head model part.

Then, we apply the walking animation to the leg model parts. We use the `cos` function to create the general leg swing effect and then transform the cosine wave to get the correct swing speed and amplitude.

- The `0.2` constant in the formula controls the frequency of the cosine wave (how fast the legs are swinging). Higher values result in a higher frequency.
- The `1.4` constant in the formula controls the amplitude of the cosine wave (how far the legs swing). Higher values result in a higher amplitude.
- The `limbSwingAmplitude` variable also affects the amplitude in the same way as the `1.4` constant. This variable changes based on the velocity of the entity, so that the legs swing more when the entity is moving faster and swing less or not at all when the entity is moving slower or not moving.
- The `Mth.PI` constant for the left leg translates the cosine wave a half phase so that the left leg is swinging the opposite direction to the right leg.

You can plot those on a graph to see what they look like:

![Graph](/assets/develop/entity/graphs/dark_graph.png){.dark-only}
![Graph](/assets/develop/entity/graphs/light_graph.png){.light-only}

The blue curve is for the left leg, and the brown one is for the right. The horizontal x-axis represents time, and the y-axis indicates the angle of the leg limbs.

Feel free to [play around with the constants on Desmos](https://www.desmos.com/calculator/9r6lh5knfu) to see how they affect the curve.

Looking into the game, you now have all you need to spawn the entity with `/summon example-mod:mini_golem`!

![Summoned Golem](/assets/develop/entity/mini_golem_summoned.png)

## Adding Data To An Entity {#adding-data-to-an-entity}

To store data on an entity, the normal way is to simply add a field in entity class.

Sometimes you need data from the server-side entity to be synced with the client-side entity. See the [Networking Page](../networking) for more info on the client-server architecture. To do this we can use _synched data_ \[sic] by defining an `EntityDataAccessor` for it.

In our case we want our entity to dance every so often, so we need to create a dancing state that is synchronized between the clients so that it can be animated later. However, the dancing cooldown need not be synced with the client because the animation is triggered by the server.

@[code transcludeWith=:::datatracker](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

As you can see we added a tick method to control the dancing state.

## Storing Data to NBT {#storing-data-to-nbt}

For persistent data that can be saved after the game is closed, we will override the `addAdditionalSaveData` and `readAdditionalSaveData` methods in `MiniGolemEntity`. We can use this to store the amount of time remaining in the dancing animation.

@[code transcludeWith=:::savedata](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

Now, whenever the entity is loaded, it will restore the state that it was left in.

## Adding an Animation {#adding-an-animation}

The first step to adding an animation to the entity is adding the animation state in the entity class. We'll create an animation state that will be used to make the entity dance.

@[code transcludeWith=:::dancing_animation](@/reference/latest/src/main/java/com/example/docs/entity/MiniGolemEntity.java)

We have overridden the `onSyncedDataUpdated` method. This gets called whenever synched data is updated both the server and the client. The if-statement checks whether the synched data that was updated is the dancing synched data.

Now, we'll move on to animation itself. We will create the `MiniGolemAnimations` class, and add an `AnimationDefinition` to define how the animation will be applied to the entity.

@[code transcludeWith=:::dancing_animation](@/reference/latest/src/client/java/com/example/docs/entity/animation/MiniGolemAnimations.java)

There's a lot going on here, notice the following key points:

- `withLength(1)` makes the animation last 1 second.
- `looping()` makes the animation loop repeatedly.
- Then follows a series of `addAnimation` calls which adds individual animations targeting individual model parts. Here, we have different animations targeting the head, left leg, and right leg.
  - Each animation targets a specific property of that model part, in our case we are changing the rotation of the model part in each case.
  - An animation is made up of a list of keyframes. When the time (number of seconds elapsed) of the animation is equal to one of these keyframes, then the value of the property we targeted will be equal to the value we specified for that keyframe (in our case the rotation).
  - When the time is between our keyframes, then the value will be interpolated (blended) between the two neighboring keyframes.
  - We have used linear interpolation, which is the simplest and changes the value (in our case rotation of the model part) at a constant rate from one keyframe to the next. Vanilla also provides Catmull-Rom spline interpolation, which produces a smoother transition between keyframes.
  - Modders can also create custom interpolation types.

Finally, let's hook up the animation to the model:

@[code transcludeWith=:::dancing_animation](@/reference/latest/src/client/java/com/example/docs/entity/model/MiniGolemEntityModel.java)

When the animation is playing we apply the animation, otherwise we use the old leg animation code.

## Adding the Spawn Egg {#adding-spawn-egg}

To add a spawn egg for the Mini Golem entity, refer to the full article on [Creating a Spawn Egg](../items/spawn-egg).
