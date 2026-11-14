---
title: Custom Particle Classes
description: Learn how to create a new Particle Class with custom logic.
authors:
  - Superkat32
---
A particle's movement is just as important as its textures. And Particle Classes control how a particle moves throughout the world. Let's make one!

## Starting The Particle Class {#starting-the-particle-class}
For this example, we will be making a simple sparkly sparkle. We'll make it move and spin in a random direction, before slowing down and shrinking to despawn.

## Particle Fields List {#particle-fields-list}
Here is a list of the most commonly used fields the main Particle Classes. Feel free to continuously refer back to this list.

#### Basic `Particle` Fields
| Field                                                                                   | Description                                                               |
|-----------------------------------------------------------------------------------------|---------------------------------------------------------------------------|
| `level` : ClientLevel                                                                   | The current ClientLevel the particle is in.                               |
| `random` : RandomSource                                                                 | A RandomSource available for generating random numbers.                   |
| `x`, `y`, `z` : double                                                                  | The x, y, and z coordinates the particle is located at.                   |
| `xd`, `yd`, `zd` : double<br/>_x/z Defaults: -0.035 - 0.1_<br/>_y Default: 0.065 - 0.2_ | The x, y, and z velocities (or travel distance per tick) of the particle. |

#### Additional `Particle` Fields
| Field                                                        | Description                                                                                                                                  |
|--------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| `lifetime` : int<br/>_Default: 4 - 40_                       | The maximum number of ticks the particle should exist for before despawning.                                                                 |
| `age` : int<br/>_Default: 0_                                 | The number of ticks the particle has existed for, usually starting at 0 and counting up until greater than or equal to the `lifetime` value. |
| `friction` : float<br/>_Default: 0.98f_                      | Amount to reduce the particle's velocities by every tick.                                                                                    |
| `gravity` : float<br/>_Default: 0.0f_                        | Used to reduce the particle's y velocity by subtracting the product of `0.04 * gravity`.                                                     |
| `hasPhysics` : boolean<br/>_Default: true_                   | Determines whether the particle can collide with blocks or not.                                                                              |
| `onGround` : boolean                                         | Determines whether the particle has collided with the ground or not.                                                                         |
| `sppedUpWhenYMotionIsBlocked` : boolean<br/>_Default: false_ | Determines whether the particle's x and z velocities increase if the y velocity is the same as the previous tick.                            |

#### `SingleQuadParticle` Fields
| Field                                              | Description                                                                                                                             |
|----------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|
| `quadSize` : float<br/>_Default: 0.1f - 0.2f_      | The quad size of the particle. `1f` is roughly equivalent to 2 blocks big.                                                              |
| `roll` : float<br/>_Default: 0.0f_                 | The roll rotation of the particle **in radians**.                                                                                       |
| `oRoll` : float<br/>_Default: 0.0f_                | The previous roll rotation of the particle in radians. This variable is _not_ updated by default, you'll need to do it yourself!        |
| `rCol`, `gCol`, `bCol` : float<br/>_Default: 1.0f_ | The red, green, and blue tints of the particle. Helpful if your particle's texture is fully white and you want to dynamically color it. |
| `alpha` : float<br/>_Default: 1.0f_                | The alpha (transparency) of the particle.                                                                                               |


## Adding Custom Logic {#adding-custom-logic}
TODO

## Creating The Particle Provider {#creating-the-particle-provider}
TODO

## Final Result
Our particle's final result, with all the code and files.

::: tabs

== Particle Class

== Mod Initializer

<<< @/reference/latest/src/main/java/com/example/docs/particle/ExampleModParticles.java#particle_classes

== Client Initializer

<<< @/reference/latest/src/client/java/com/example/docs/particle/ExampleModParticlesClient.java#particle_classes

== Sprite Set JSON

<<< @/reference/latest/src/main/resources/assets/example-mod/particles/sparkly_sparkle_particle.json

== Textures

<DownloadEntry visualURL="/assets/develop/rendering/particles/sparkle_textures_big.png" downloadURL="/assets/develop/rendering/particles/sparkle_particle_textures.zip">Particle Textures</DownloadEntry>

:::
