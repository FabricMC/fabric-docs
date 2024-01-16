---
title: Creating Custom Particles
description: Learn how to create a custom particle using Fabric API.
---

# Creating Custom Particles

Particles are a powerful tool. They can add ambience to a beautiful scene, or add tension to an edge of your seat boss battle. Let's add one!

## Register a custom particle

To add a custom particle, we'll first need to register a `ParticleType` in your mod initializer class using your mod id.

```java{4-5,9} [MyMod.java]
public class MyMod implements ModInitializer {

  public static final String MOD_ID = "<mod id here>";
  //This DefaultParticleType gets called when you want to use your particle in code
  public static final DefaultParticleType MY_PARTICLE = FabricParticleTypes.simple();

  @Override
  public void onInitialize() {
    Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "my_particle"), MY_PARTICLE);
  }
}
```

The "my_particle" in lowercase letters is the json path for the particle's texture. You will be creating a new json file with that exact name later.

## Client-side registration

After you have registered the particle in the `ModInitializer` entrypoint, you will also need to register the particle in the `ClientModInitializer` entrypoint.

```java{5} [MyModClient.java]
public class MyModClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(MyMod.MY_PARTICLE, EndRodParticle.Factory::new);
    }
}
```
In this example, we are registering our particle on the client-side. We are then giving the particle some movement using the end rod particle's factory. That means our particle will now move exactly like an end rod particle. You can replace the factory with another particle's factory, or even your own.

## Creating a json file and adding textures

You will need to create 3 folders in your resources folder.

Let's begin with creating the folders necessary for the particle's texture(s). Add the new `resources/assets/<mod id here>/textures/particle` folders to your directory. Place the particle's textures that you want to use in the `particle` folder.

For this example, we will only be adding one texture, named "myparticletexture.png".


Then create the `particles` folder in  `resources/assets/<mod id here>/particles` folder, and create a new json file named `my_particle.json` - this file will let Minecraft know which textures your particle should use.

```json [my_particle.json]
{
  "textures": [
    "<mod id here>:myparticletexture"
  ]
}
```

You can add more textures to animate the particle as well.

```json [my_particle.json]
{
  "textures": [
    "<mod id here>:myparticletexture1",
    "<mod id here>:myparticletexture2",
    "<mod id here>:myparticletexture3"
  ]
}
```

## Testing the new particle

Once you have completed the json file and saved your work, you are good to load up Minecraft and test everything out.

You can see if everything has worked by typing the command `/particle <mod id here>:my_particle ~ ~1 ~`. The particle will spawn inside the player with this command, so you may need to walk backwards to actually see it. You can also use a command block to summon the particle with the exact same command.