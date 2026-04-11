---
title: Creating Your First Fluid
description: Learn how to create your first custom fluid in Minecraft.
authors:
  - AlbanischeWurst
  - AlexiyOrlov
  - cassiancc
  - CelDaemon
  - Clomclem
  - comp500
  - Daomephsta
  - Earthcomputer
  - florensie
  - Fusion-Flux
  - InfinityChances
  - Kilip1000
  - MaxURhino
  - SolidBlock-cn
  - SuperSoupr
  - Virtuoel
  - UpcraftLP
authors-nogithub:
  - alfiejfs
  - salvopelux
---

<!---->

::: info PREREQUISITES

You must first understand how to [create a block](../blocks/first-block) and how to [create an item](../items/first-item).

:::

This example will cover the creation of an acid fluid that hurts, weakens, and blinds entities that stand inside of it. To do this, we'll need two fluid instances for the source and fluid states, a liquid block, a bucket item, and a fluid tag.

## Creating the Fluid Class {#creating-the-fluid-class}

We'll start by creating an abstract class, in this case called `AcidFluid`, that extends the baseline `FlowingFluid` class. Then, we'll override any the methods that should be the same for both the source and the flowing fluid.

Pay special attention to the following methods:

- `animateTick` is used for displaying particles and sound. The behaviour shown below is based on water, which plays sound when it flows and has underwater bubbling particles.
- `entityInside` is used to handle what should happen when an entity touches the fluid. We'll base it off water and extinguish any fire on entities, but also make it hurt, weaken, and blind entities inside - it is acid after all.
- `canBeReplacedWith` handles some flowing logic - note that `ModFluidTags.ACID` is not yet defined, we'll handle that at the end.

Putting this all together, we end up with the following class:

<<< @/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java#abstract-fluid

Inside `AcidFluid`, we'll create two subclasses for the `Source` and `Flowing` fluids.

<<< @/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java#fluid-subclasses

### Registering Fluids {#registering-fluids}

Next, we'll create a class to register all the fluid instances. We'll call it `ModFluids`.

<<< @/reference/latest/src/main/java/com/example/docs/fluid/ModFluids.java#register

Just like with blocks, you need to ensure that the class is loaded so that all static fields containing your fluid instances are initialized. You can do this by creating a dummy `initialize` method, which can be called in your [mod's initializer](../getting-started/project-structure#entrypoints) to trigger the static initialization.

Now, go back to the `AcidFluid` class, and add these methods to associate the registered fluid instances with this fluid:

<<< @/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java#sources

So far, we've registered the fluid's source state and its flowing state. Next, we'll need to register a bucket and a `LiquidBlock` for it.

### Registering Fluid Blocks {#fluid-blocks}

Let's now add a liquid block for our fluid. This is needed by some commands like `setblock`, so your fluid can exist in the world. If you haven't done so yet, you should take a look at [how to create your first block](../blocks/first-block).

Open your `ModBlocks` class and register this following `LiquidBlock`:

<<< @/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java#acid

Then, override this method in `AcidFluid` to associate your block with the fluid:

<<< @/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java#legacy-block

### Registering Buckets {#buckets}

Fluids in Minecraft usually go in buckets, so let's see how we can add an item for a Bucket of Acid. If you haven't done so yet, you should take a look at [how to create your first item](../items/first-item).

Open your `ModItems` class and register this following `BucketItem`:

<<< @/reference/latest/src/main/java/com/example/docs/item/ModItems.java#acid-bucket

Then, override this method in `AcidFluid` to associate your bucket with the fluid:

<<< @/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java#bucket

Don't forget that items require a translation, [texture](../items/first-item#adding-a-texture), [model](../items/first-item#adding-a-model), and [client item](../items/first-item#creating-the-client-item) with the name `acid_bucket` in order to render correctly. An example texture is provided below.

<DownloadEntry visualURL="/assets/develop/fluids/acid_bucket.png" downloadURL="/assets/develop/fluids/acid_bucket_small.png">Texture</DownloadEntry>

It's also recommended to add your mod's bucket to the `ConventionalItemTags.BUCKET` item tag so that other mods can handle it appropriately, either [manually](#tagging) or through [data generation](../data-generation/tags).

## Tagging Your Fluids {#tagging}

::: info

Users of [data generation](../data-generation/tags) may wish to register tags via `FabricTagProvider.FluidTagProvider`, rather than writing them by hand.

:::

Because a fluid is considered two separate blocks in its flowing and still states, a tag is often used to check for both states together. We'll create a fluid tag in `data/example-mod/tags/fluid/acid.json`:

<<< @/reference/latest/src/main/generated/data/example-mod/tags/fluid/acid.json

::: tip

Minecraft also provider other tags to control the behavior of fluids:

- If you need your mod's fluid to behave like water (water fog, absorbed by sponges, swimmable, slows entities...), considering adding it to the `minecraft:water` fluid tag.
- If you need it to behave like lava (lava fog, swimmable by Striders/Ghasts, slows entities...), consider adding it to the `minecraft:lava` fluid tag.
- If you only need _some_ of those things, you may wish to use mixins to finely control the behavior.

:::

For this demo, we'll also add the acid fluid tag to the water fluid tag, `data/minecraft/tags/fluid/water.json`.

<<< @/reference/latest/src/main/generated/data/minecraft/tags/fluid/water.json

## Texturing Your Fluids {#textures}

To texture your fluid, you should use Fabric API's `FluidRenderHandlerRegistry`.

::: tip

For simplicity, this demo uses `BlockTintSources.constant` to apply a constant green tint to the vanilla water texture. For more details on `BlockTintSource`, see [Block Tinting](../blocks/block-tinting).

:::

Add the following lines to your `ClientModInitializer` to create a `FluidModel.Unbaked`, that takes in two `Material`s for the textures—one for the still source and one for the flowing fluid—and a block tint source for the color to tint it with.

<<< @/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java#fluid-texture

At this point, we have all we need to see the Acid in-game! You can use `setblock` or the Acid Bucket item to place acid in the world.

![A screenshot of a green acid fluid in the world](/assets/develop/fluids/acid.png)
