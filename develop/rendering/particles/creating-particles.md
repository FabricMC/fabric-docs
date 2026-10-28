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

In the file, add the paths to the textures you want to use:

::: tabs

== Sparkle Sprite Set Example

<<< @/reference/latest/src/main/resources/assets/example-mod/particles/sparkle_particle.json

Entries will be matched to texture files in the `assets/example-mod/textures/particle` folder.

You can also use vanilla particle textures by adding `minecraft:texture_path`.

== Template Sprite Set

<!-- prettier-ignore-start -->
```json
{
  "textures": [

  ]
}
```
<!-- prettier-ignore-end -->

A blank template that you can copy-paste into your Sprite Set JSON file.

:::

Because we chose to use `EndRodParticle`'s logic, it will animate our particle by cycling through the textures in the sprite set throughout its lifetime.

Not all particle classes do this: a few classes, including `CritParticle` and `FlameParticle`, will instead choose a random texture and use it for the entire lifetime.

::: tip

You can "extend" the duration that one specific texture is shown by repeating it multiple times in the sprite set!

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

We have successfully added the particle. Now, let's see how we can spawn it with code.

There are two methods to spawn a particle, depending on your [networking side](../../networking): via `ClientLevel#addParticle()` and `ServerLevel#sendParticles()`. Most commonly, you'll be spawning particles with the client method.

For both methods, you'll pass the `ParticleType` you want to spawn, along with some other parameters.

You can use a custom particle type, or you can also spawn vanilla particles by referencing the `ParticleTypes` class!

::: tabs

== Client Side

`ClientLevel#addParticle()` will add a particle on the client side. It takes in a `ParticleType`, three parameters for the position, and three more for the velocities:

<<< @/reference/latest/src/main/java/com/example/docs/particle/ExampleModParticles.java#client_send_particles

== Server Side

`ServerLevel#sendParticles()` will [send a packet](../../networking#why-is-networking-important) telling clients to add particles to their worlds.
It takes in a few parameters:

- The `ParticleType` of our particle,
- Three parameters for the starting position
- A count of particles to be spawned
- Three parameters for the random distance the particle should travel
- A speed parameter, which I don't understand how it works

<<< @/reference/latest/src/main/java/com/example/docs/particle/ExampleModParticles.java#server_send_particles

This method's parameters are more tuned towards spawning multiple particles with variation in their positions and velocities (for example, the fishing rod's water particles).

If you're sending multiple particles at once, consider [creating your own packet](../../networking#an-introduction-to-networking), which would spawn all particles at once via `ClientLevel#addParticle()`. This can help reduce lag, by sending a single packet with less data while achieving the same effect.

:::

<!---->
