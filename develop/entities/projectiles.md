---
title: Custom Projectiles
description: Learn how to add custom projectiles.
authors:
  - ayutac
  - cassiancc
  - CelDaemon
  - ChampionAsh5357
  - dicedpixels
  - Earthcomputer
  - ekulxam
  - haykam
  - kanpov
  - NetUserGet
  - onlyspxctre
  - patrickmsm
  - tianjun
  - upcraftlp
resources:
  https://minecraft.wiki/w/Projectile: Projectiles - Minecraft Wiki
  https://docs.neoforged.net/docs/entities/#projectiles: Projectiles - NeoForge Docs
---

Projectiles are entities that can be thrown or fired by players or other entities. In this guide, we'll look into implementing a simple projectile like a snowball.

We'll call our projectile a Hot Tater. It will be a potato that sets the block or entity it hits on fire.

::: info PREREQUISITES

Creating a projectile requires you to register an item as well as an entity, therefore we suggest going through the [Creating Your First Item](../items/first-item) and [Creating Your First Entity](./first-entity) guides.

:::

## Creating the Projectile Entity {#creating-the-projectile-entity}

Let's create a `HotTaterEntity` by extending `ThrowableItemProjectile`. This class should be in your `main` source set.

The `ThrowableItemProjectile` class handles the physics and the item form of the projectile.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#entity

There's quite a lot happening here. Let's look at the important code sections.

### Constructors {#constructors}

We define 3 constructors. They're used by entity registration, projectile spawning and projectile conversion respectively.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#constructors

### Overriding `getDefaultItem()` {#override-get-default-item}

Defines the item form of this projectile.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#default_item

::: warning IMPORTANT

Your IDE might tell you that it cannot resolve the item: we will create it soon, in the [Registration](#registration) section.

:::

### Overriding `onHitBlock()` {#override-on-hit-block}

Defines the behavior when this projectile hits a block. We check where the projectile has hit and then set the hit face of that block on fire. This logic is handled on the server side.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#on_hit_block

### Overriding `onHitEntity()` {#override-on-hit-entity}

Defines the behavior when this projectile hits an entity. We set the entity that was hit on fire for 5 seconds.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#on_hit_entity

### Overriding `onHit()` {#override-on-hit}

Defines the behavior when this projectile hits anything, whether a block or an entity. We will use this to discard the projectile, so that it is removed on hit; without this, the projectile would just keep going.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterEntity.java#on_hit

## Creating the Item {#creating-the-item}

We register a simple item. Since we need to implement the throwing logic, our class `HotTaterItem` will extend `Item` and implement `ProjectileItem`. This class should be in your `main` source set.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterItem.java#item

It's a standard item implementation, with some special methods from `ProjectileItem`. Let's look at them:

### Overriding `asProjectile()` {#override-as-projectile}

This method converts the item into its entity form.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterItem.java#as_projectile

### Overriding `use()` {#override-use}

Defines the action that happens when the item is used. In our case, we call the `Projectile.spawnProjectileFromRotation()` utility method to spawn the projectile.

In addition to the standard parameters (level, item stack, and player), this utility method takes three additional floats:

- `yOffset`: Offset for the pitch (rotation around the X axis, upward or downward), in degrees. Negative values angle the initial velocity upward.
- `pow`: Multiplier for the speed of the projectile movement.
- `uncertainty`: Imprecision of the projectile. 0 means no random spread. As the value increases, projectiles will be more dispersed, even when thrown with the same initial position and rotation.

For more information, see the [Minecraft Wiki's article on projectiles](https://minecraft.wiki/w/Projectile#Initial_conditions).

::: details Why is the parameter called `yOffset`?

We don't know either, dear reader. Despite the name, [the offset is applied to the pitch](https://mcsrc.dev/2/26.2/net/minecraft/world/entity/projectile/Projectile#L156), which is the rotation of the _velocity_ around the X axis:

```java
float yd = -Mth.sin((xRot + yOffset) * (float) (Math.PI / 180.0));
```

For example, when vanilla uses a [`yOffset` of `-20.0F` for splash potions](https://mcsrc.dev/2/26.2/net/minecraft/world/item/ThrowablePotionItem#L28), it changes the initial velocity pitch from `source.getXRot()` to `source.getXRot() - 20.0F` (20 degrees upward, toward the sky).

Perhaps a more apt name would have been `xRotOffset`.

:::

Finally, we award the `ITEM_USED` stat, consume one item from the stack, and mark the interaction as successful.

<<< @/reference/latest/src/main/java/com/example/docs/projectile/HotTaterItem.java#use

## Registration {#registration}

Register the item, like we did in the [Creating Your First Item](../items/first-item#registering-an-item) guide. First, define the item's key in `ModItemIds`:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItemIds.java#hot_tater

Then register the item in `ModItems`, alongside the others:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#hot_tater

Don't forget to add a [model](../items/first-item#adding-a-model), [texture](../items/first-item#adding-a-texture), [client item](../items/first-item#creating-the-client-item), and [name](../items/first-item#naming-the-item), using the identifier `hot_tater`. You should also [add the item to a creative tab](../items/first-item#adding-the-item-to-a-creative-tab). Here's an example texture:

<DownloadEntry visualURL="/assets/develop/projectiles/hot_tater_preview.png" downloadURL="/assets/develop/projectiles/hot_tater.png">Texture</DownloadEntry>

Make sure to add the ID of the new entity to `ModEntityTypeIds`:

<<< @/reference/latest/src/main/java/com/example/docs/entity/ModEntityTypeIds.java#hot_tater

Now register the entity, like we did in the [Creating Your First Entity](./first-entity#preparing-your-first-entity) guide, by adding it as a static field in `ModEntityTypes`. Since entities and items live in separate registries, the entity type simply uses the same path as the item, like vanilla's `snowball`:

<<< @/reference/latest/src/main/java/com/example/docs/entity/ModEntityTypes.java#hot_tater

Finally, let's use the vanilla `ThrownItemRenderer` in the client initializer:

<<< @/reference/latest/src/client/java/com/example/docs/projectile/ExampleModProjectileClient.java#renderer

And you're done!

<VideoPlayer src="/assets/develop/projectiles/hot-tater.mp4">A Hot Tater setting a villager on fire</VideoPlayer>
