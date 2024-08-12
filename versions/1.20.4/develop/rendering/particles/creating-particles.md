---
title: Creating Custom Particles
description: Learn how to create a custom particle using Fabric API.
authors:
  - Superkat32

search: false
---

# Creating Custom Particles {#creating-custom-particles}

Particles are a powerful tool. They can add ambience to a beautiful scene, or add tension to an edge of your seat boss battle. Let's add one!

## Register a Custom Particle {#register-a-custom-particle}

We'll be adding a new sparkle particle which will mimic the movement of an end rod particle.

We first need to register a `ParticleType` in your mod initializer class using your mod id.

@[code lang=java transcludeWith=#particle_register_main](@/reference/1.20.4/src/main/java/com/example/docs/FabricDocsReference.java)

The "sparkle_particle" in lowercase letters is the JSON path for the particle's texture. You will be creating a new JSON file with that exact name later.

## Client-Side Registration {#client-side-registration}

After you have registered the particle in the `ModInitializer` entrypoint, you will also need to register the particle in the `ClientModInitializer` entrypoint.

@[code lang=java transcludeWith=#particle_register_client](@/reference/1.20.4/src/client/java/com/example/docs/FabricDocsReferenceClient.java)

In this example, we are registering our particle on the client-side. We are then giving the particle some movement using the end rod particle's factory. This means our particle will move exactly like an end rod particle.

::: tip
You can see all the particle factories by looking at all the implementations of the `ParticleFactory` interface. This is helpful if you want to use another particle's behaviour for your own particle.

- IntelliJ's hotkey: Ctrl+Alt+B
- Visual Studio Code's hotkey: Ctrl+F12
:::

## Creating a JSON File and Adding Textures {#creating-a-json-file-and-adding-textures}

You will need to create 2 folders in your `resources/assets/<mod id here>/` folder.

| Folder Path          | Explanation                                                                          |
|----------------------|--------------------------------------------------------------------------------------|
| `/textures/particle` | The `particle` folder will contain all the textures for all of your particles.       |
| `/particles`         | The `particles` folder will contain all of the json files for all of your particles. |

For this example, we will have only one texture in `textures/particle` called "sparkle_particle_texture.png".

Next, create a new JSON file in `particles` with the same name as the JSON path that you used when registering your ParticleType. For this example, we will need to create `sparkle_particle.json`. This file is important because it lets Minecraft know which textures our particle should use.

@[code lang=json](@/reference/1.20.4/src/main/resources/assets/fabric-docs-reference/particles/sparkle_particle.json)

::: tip
You can add more textures to the `textures` array to create a particle animation. The particle will cycle through the textures in the array, starting with the first texture.
:::

## Testing the New Particle {#testing-the-new-particle}

Once you have completed the JSON file and saved your work, you are good to load up Minecraft and test everything out!

You can see if everything has worked by typing the following command:

```mcfunction
/particle <mod id here>:sparkle_particle ~ ~1 ~
```

![Showcase of the particle](/assets/develop/rendering/particles/sparkle-particle-showcase.png)

::: info
The particle will spawn inside the player with this command. You will likely need to walk backwards to actually see it.
:::

Alternatively, you can also use a command block to summon the particle with the exact same command.
