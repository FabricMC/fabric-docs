---
title: Creating Custom Particles
description: Learn how to create a custom particle using Fabric API.
authors:
  - Superkat32
---

Particles are a powerful tool. They can add ambience to a beautiful scene, or add tension to an edge of your seat boss battle. Let's add one!

## Quick Overview {#quick-overview}

Particles are made up of 5 main components, as listed below. We'll look at each one in a moment, but here's a quick overview.

- **ParticleType** - The main Registry entry for your particle, mostly used when adding your particle to the world.
- **Particle Class** - Handles the logic behind a particle, including its movement, lifetime (time before despawning), scale, and more.
- **ParticleProvider** - Tells your ParticleType which Particle Class to use.
- **Sprite Set JSON** - The JSON file that points to all the textures a particle should use.
- **Textures** - The textures that the Sprite Set JSON points towards.

## Particle Type Registration {#particle-type-registration}

For this example, we'll be adding a new sparkle particle that mimics the logic of an end rod particle.

To begin, register a `ParticleType` in your [mod's initializer](../../getting-started/project-structure#entrypoints). This object will be used every time you want to spawn a particle via code, and once more in the ParticleProvider registration.

<<< @/reference/latest/src/main/java/com/example/docs/particle/ExampleModParticles.java#entrypoint

The "sparkle_particle" path of the Identifier is for the Sprite Set JSON file that stores the particle's textures and when spawning the particle with commands. You will be creating a new JSON file with that exact name soon.

## Particle Provider Registration {#particle-provider-registration}

Next, we need to register a ParticleProvider in your [mod's client initializer](../../getting-started/project-structure#entrypoints).

The ParticleProvider tells your earlier ParticleType which Particle Class to use. The Particle Class handles everything about a particle's logic, including its movement, lifetime (time before despawning), scale, and more.

For this example, we want to mimic the end rod particle's logic, so we'll use its ParticleProvider via the `EndRodParticle.Provider::new` lambda statement. Most commonly, the ParticleProvider is a static class within the associated Particle Class file.

<<< @/reference/latest/src/client/java/com/example/docs/particle/ExampleModParticlesClient.java#entrypoint

::: tip

You can see all the Particle Providers by looking at all the implementations of the `ParticleProvider` interface. This is helpful if you want to use another particle's logic for your own particle.

- IntelliJ's hotkey: <kbd>Ctrl</kbd>+<kbd>Alt</kbd>+<kbd>B</kbd>
- Visual Studio Code's hotkey: <kbd>Ctrl</kbd>+<kbd>F12</kbd>

:::

## Sprite Set JSON & Textures {#sprite-set-json-and-textures}

After the registrations, you will need to create 2 folders in your `resources/assets/<mod_id>/` folder.

| Folder Path          | Explanation                                                                                  |
| -------------------- | -------------------------------------------------------------------------------------------- |
| `/textures/particle` | The `particle` folder will contain all the textures for all of your particles.               |
| `/particles`         | The `particles` folder will contain all the Sprite Set JSON files for all of your particles. |

Add any textures you want for your particle to your `/textures/particle` folder. Textures are normally 16x16 pixels, but Vanilla sometimes uses 8x8 pixels (e.g. water splash particles) and 32x32 pixels (e.g. sonic boom particle).

For this example, we have 6 sparkle textures named `sparkle_1` through `sparkle_6`. The `EndRodParticle` class will animate our particle for us based on these textures.
<DownloadEntry visualURL="/assets/develop/rendering/particles/sparkle_textures_big.png" downloadURL="/assets/develop/rendering/particles/sparkle_particle_textures.zip">Particle Textures</DownloadEntry>

Next, create a new JSON file in the `/particles` folder with the same name as the Identifier path from your ParticleType registration (in this example, "sparkle_particle"). This is your Sprite Set JSON, add the paths to the textures you want your particle to use.

::: tabs

== Sparkle Example

<<< @/reference/latest/src/main/resources/assets/example-mod/particles/sparkle_particle.json

You can use Vanilla textures too, just add `minecraft:<vanilla_texture_file_name>` as a texture path to the `textures` array.

== Template

<!-- prettier-ignore-start -->
```json
{
  "textures": [

  ]
}
```
<!-- prettier-ignore-end -->

A blank template for you to copy-paste into your Sprite Set JSON file.

:::

For this example, our chosen `EndRodParticle` Particle Class will animate our particle based on that `textures` array. Each texture will be evenly spaced out throughout our particle's lifetime in the order we list them. You can repeat path entries to give it extra time if desired.

::: details

Unlike normal texture animations, which use a `.mcmeta` file, most Particle Classes will animate particles based on their `textures` array. Each texture is evenly spaced throughout the particle's lifetime, so if a particle has 10 textures and exists for 20 ticks, then each texture will be shown for 2 ticks.

However, some Particle Classes don't animate particles, instead they choose a single random texture from their `textures` array, which lasts the particle's entire lifetime. Notable examples include the `CritParticle` and `FlameParticle` classes (technically, the textures are randomly chosen from their ParticleProviders in these cases).

If you're unsure how a Particle Class handles texture animations, check if the `setSpriteFromAge()` method is called in the `tick()` method. If it is, then it'll animate the particle throughout its lifetime. If it isn't, then it's likely that a random texture or the first texture is the only texture used.

Note: You can technically still use a `.mcmeta` file for animations, but it'll act different than you expect. Instead of each particle getting its own animation based on its lifetime, each particle will use the same frame at the same time (just like block and item texture animations). The only Vanilla particle that does this is the sculk vibration particle.

:::

## Testing the New Particle {#testing-the-new-particle}

Once you've completed your Sprite Set JSON and added the textures you want, it's time to load up Minecraft and test out the particle!

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

There are two ways to spawn a particle depending on your [networking context](../../networking). Most commonly, though, you'll be adding particles from the client side.

You'll use your ParticleType object for both sides, as it's the only component of particles that are available on both the server and client. Particle Classes (and any spawned particles) are client side only.

::: tabs

== Client Side

`ClientLevel#addParticle()` will add a particle on that client's world.

<<< @/reference/latest/src/main/java/com/example/docs/particle/ExampleModParticles.java#client_send_particles

== Server Side

`ServerLevel#sendParticles()` will send a packet telling clients to add particles to their worlds.

<<< @/reference/latest/src/main/java/com/example/docs/particle/ExampleModParticles.java#server_send_particles

This method's parameters are different from its client sided counterpart, being more tuned towards spawning multiple particles with position & velocity variations (like the fishing rod's water particles).

Note that calling `addParticle()` on the `ServerLevel` will not do anything.

:::

::: tip

You can also spawn Vanilla particles by using a ParticleType from the `ParticleTypes` class!

:::

<!---->
