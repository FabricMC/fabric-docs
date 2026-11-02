---
title: Creating Custom Particles
description: Learn how to create a custom particle using Fabric API.
authors:
  - Superkat32
---

Particles are a powerful tool. They can add ambience to a beautiful scene, or add tension to an edge-of-your-seat boss battle. Let's add one!

## Particle Type Registration {#particle-type-registration}

For this example, we'll be adding a new sparkle particle that mimics the logic of an end rod particle.

To begin, create and register a `ParticleType` in your [mod's initializer](../../getting-started/project-structure#entrypoints). This object is used every time you want to spawn a particle via code.

<<< @/reference/latest/src/main/java/com/example/docs/particle/ExampleModParticles.java#entrypoint

The `sparkle_particle` path is for the [Sprite Set JSON file](#sprite-set-json-and-textures) that stores the particle's texture paths. We will be creating that file soon.

## Particle Provider Registration {#particle-provider-registration}

Next, we need to register a `ParticleProvider` in your [mod's client initializer](../../getting-started/project-structure#entrypoints).

The `ParticleProvider` determines which particle class to use. The particle's class handles everything about its logic, including its movement, lifetime (time before despawning), scale, and more.

For this example, we want to mimic the end rod particle's logic, so we'll use its `ParticleProvider` via `EndRodParticle.Provider::new`.

<<< @/reference/latest/src/client/java/com/example/docs/particle/ExampleModParticlesClient.java#entrypoint

::: tip

You can see all available particle providers by viewing all the implementations of the `ParticleProvider` interface. This is helpful if you want to use another particle's logic for your own particle.

Find how to view all implementations in your IDE in the [IDE Tips and Tricks page](../../getting-started/tips-and-tricks).

:::

## Sprite Set JSON & Textures {#sprite-set-json-and-textures}

After the registrations, we need to give our particle some textures.

All particle textures should be put in the `assets/example-mod/textures/particle` folder. Textures are normally 16x16 pixels, but any multiple of 8 will also work.

For this example, we have prepared 6 sparkle textures named `sparkle_1` through `sparkle_6`:

<DownloadEntry visualURL="/assets/develop/rendering/particles/sparkle_textures_big.png" downloadURL="/assets/develop/rendering/particles/sparkle_particle_textures.zip">Particle Textures</DownloadEntry>

Next, we need to create a Sprite Set JSON file and place it under the `assets/example-mod/particles` folder _(notice the extra "s" in the folder name!)_.

The JSON file should be named just like your particle's `Identifier` path from the `ParticleType` registration (in our case `sparkle_particle.json`).

In that file, add the paths to the textures you want to use in a `textures` array:

<<< @/reference/latest/src/main/resources/assets/example-mod/particles/sparkle_particle.json

Entries will be matched to texture files in the `assets/example-mod/textures/particle` folder.

Because we chose to use `EndRodParticle`'s logic, it will animate our particle by cycling through the textures in the sprite set throughout its lifetime.

But not all particle classes do this: a few classes, including `CritParticle` and `FlameParticle`, will instead choose a random texture and use it for the entire lifetime.

::: tip

You can use vanilla particle textures by using `minecraft:texture_path`.

Textures are also allowed to be repeated in the `textures` array. If the particle is animated, that texture's duration will be "extended" in the animation. Otherwise, that texture will have a higher chance of being randomly selected for the entire lifetime.

:::

## Testing the New Particle {#testing-the-new-particle}

Once you've added the textures you want, and created your sprite set, it's time to test out the particle in-game!

You can test it by using a command block with the `/particle` command, and giving it the particle's identifier:

```mcfunction
/particle example-mod:sparkle_particle ~ ~1 ~
```

<VideoPlayer src="/assets/develop/rendering/particles/sparkle-particle-video-showcase.webm">Finished Sparkle Particle Example</VideoPlayer>

::: info

This command works best with a command block.

If you type it in chat, the particle will spawn at the player's location, and you'll likely need to walk backwards to see it.

:::

## Spawning Particles in Code {#spawning-particles-in-code}

Now that we have successfully added a particle, let's spawn it with code!

There are two methods to spawn a particle, depending on your [networking side](../../networking): via `ClientLevel#addParticle()` and `ServerLevel#sendParticles()`. Most commonly, you'll be spawning particles with the client method.

Both methods take in a `ParticleType` along with some other parameters. You can use a custom particle type, or you can spawn vanilla particles by referencing the `ParticleTypes` class!

### Spawning on the Client {#spawning-on-the-client}

`ClientLevel#addParticle()` will add a particle on the client side.
It takes in some simple parameters:

- The `ParticleType` of the particle to spawn.
- Three parameters for the starting x, y, and z positions.
- Three parameters for the x, y, and z velocities. _(Note that some particle classes choose to ignore the passed velocities in favor of generating their own)_

<<< @/reference/latest/src/main/java/com/example/docs/particle/ExampleModParticles.java#client_send_particles

### Spawning From the Server {#spawning-from-the-server}

`ServerLevel#sendParticles()` will [send a packet](../../networking#why-is-networking-important) telling clients to add particles to their worlds.
It takes in a few parameters:

- The `ParticleType` of the particle to spawn.
- Three parameters for the starting x, y, and z positions.
- A count of particles to be spawned.
- Three parameters for the x, y, and z distances:
  - By default, these are the standard deviations for the random x, y, and z position offsets.
  - If the particle count is `0`, these are simply multiplied with the max speed parameter to get the particle's x, y, and z velocities.
- A parameter for the max speed:
  - By default, this is the standard deviation for the random x, y, and z velocities.
  - If the particle count is `0`, this is simply multiplied with the x, y, and z distances to get the particle's x, y, and z velocities.

<<< @/reference/latest/src/main/java/com/example/docs/particle/ExampleModParticles.java#server_send_particles

This method's parameters are more tuned towards spawning multiple particles of the same `ParticleType` with variation in their positions and velocities (for example, the fishing rod's water particles).

If you're sending multiple particles of various `ParticleType`s at once, consider [creating your own packet](../../networking#an-introduction-to-networking), which would [spawn all the particles on the client](#spawning-on-the-client). This can help reduce lag by sending a single packet with less data while achieving the same effect.

<!---->
