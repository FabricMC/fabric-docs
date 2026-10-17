---
title: Creating Custom Particles
description: Learn how to create a custom particle using Fabric API.
authors:
  - Superkat32
---

Particles are a powerful tool. They can add ambience to a beautiful scene, or add tension to an edge of your seat boss battle. Let's add one!

## Particle Type Registration {#particle-type-registration}

For this example, we'll be adding a new sparkle particle that mimics the logic of an end rod particle.

To begin, create and register a `ParticleType` in your [mod's initializer](../../getting-started/project-structure#entrypoints). This object is used every time you want to spawn a particle via code.

<<< @/reference/latest/src/main/java/com/example/docs/particle/ExampleModParticles.java#entrypoint

The "sparkle_particle" path is for the Sprite Set JSON file that stores the particle's textures. You will be creating a new JSON file with that exact name soon.

## Particle Provider Registration {#particle-provider-registration}

Next, we need to register a ParticleProvider in your [mod's client initializer](../../getting-started/project-structure#entrypoints).

The ParticleProvider determines which Particle Class to use. The Particle Class handles everything about a particle's logic, including its movement, lifetime (time before despawning), scale, and more.

For this example, we want to mimic the end rod particle's logic, so we'll use its ParticleProvider via `EndRodParticle.Provider::new`.

<<< @/reference/latest/src/client/java/com/example/docs/particle/ExampleModParticlesClient.java#entrypoint

::: tip

You can see all available Particle Providers by viewing all the implementations of the `ParticleProvider` interface. This is helpful if you want to use another particle's logic for your own particle.

- IntelliJ's hotkey: <kbd>Ctrl</kbd>+<kbd>Alt</kbd>+<kbd>B</kbd>
- Visual Studio Code's hotkey: <kbd>Ctrl</kbd>+<kbd>F12</kbd>

:::

## Sprite Set JSON & Textures {#sprite-set-json-and-textures}

After the registrations, we need to give our particle some textures.

All particle textures should be put in the `assets/<mod_id>/textures/particle` folder. Textures are normally 16x16 pixels, but Vanilla sometimes uses 8x8 or 32x32.

For this example, we have 6 sparkle textures named `sparkle_1` through `sparkle_6`.
<DownloadEntry visualURL="/assets/develop/rendering/particles/sparkle_textures_big.png" downloadURL="/assets/develop/rendering/particles/sparkle_particle_textures.zip">Particle Textures</DownloadEntry>

Next, all Sprite Set JSON files should be put in the `assets/<mod_id>/particles` folder _(notice the extra "s" in this folder's name!)_.

In this folder, create a new JSON file with the same name as your particle's Identifier path from your ParticleType registration (e.g., "sparkle_particle.json"). Then, add the paths to the textures you want to use.

::: tabs

== Sparkle Sprite Set Example

<<< @/reference/latest/src/main/resources/assets/example-mod/particles/sparkle_particle.json

You can use Vanilla textures too, just add `minecraft:<vanilla_texture_file_name>` as a texture path to the `textures` array.

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

For this example, our chosen `EndRodParticle` Particle Class will animate our particle based on that `textures` array. Each texture will be evenly spaced out throughout our particle's lifetime in the order we list them. Entries can even be repeated to give them more time, if desired.

Most Particle Classes will animate the particle like this, but some will instead choose a random texture from that `textures` array to use throughout its entire lifetime. Notable instances of this include the `CritParticle` and `FlameParticle` classes.

## Testing the New Particle {#testing-the-new-particle}

Once you've added the textures you want and finished your Sprite Set JSON, it's time to load up Minecraft and test out the particle!

You can test your particle by using the `/particle` command with your mod id and your particle's Identifier path:

```mcfunction
/particle example-mod:sparkle_particle ~ ~1 ~
```

<VideoPlayer src="/assets/develop/rendering/particles/sparkle-particle-video-showcase.mp4">Finished Sparkle Particle Example</VideoPlayer>

::: info

This command works best with a command block.

If you type it in chat, the particle will spawn inside the player, and you'll likely need to walk backwards to see it well.

:::

## Spawning Particles in Code {#spawning-particles-in-code}

What good is a particle if you can't spawn it from code?

There are two methods to spawn a particle depending on your [networking context](../../networking) via `ClientLevel#addParticle()` and `ServerLevel#sendParticles()`. Most commonly, you'll be spawning particles from the client side.

For both methods, you'll pass the ParticleType you want to spawn along with other parameters for positioning and velocities.

::: tabs

== Client Side

`ClientLevel#addParticle()` will add a particle on that client's world, taking in a ParticleType, position, and velocities.

<<< @/reference/latest/src/main/java/com/example/docs/particle/ExampleModParticles.java#client_send_particles

== Server Side

`ServerLevel#sendParticles()` will send a packet telling clients to add particles to their worlds, taking in a ParticleType, position, count, maximum random travel distance, and maximum random speed.

<<< @/reference/latest/src/main/java/com/example/docs/particle/ExampleModParticles.java#server_send_particles

This method's parameters are more tuned towards spawning multiple particles with positional & velocity variations (e.g., the fishing rod's water particles).

Note that calling `Level#addParticle()` on the `ServerLevel` will not do anything.

:::

::: tip

You can also spawn Vanilla particles by using a ParticleType from the `ParticleTypes` class!

:::

<!---->
