---
title: Project Structure
description: An overview of the structure of a Fabric mod project.
authors:
  - IMB11
---

This page will go over the structure of a Fabric mod project, and the purpose of each file and folder in the project.

## `fabric.mod.json` {#fabric-mod-json}

The `fabric.mod.json` file is the main file that describes your mod to Fabric Loader. It contains information such as the mod's ID, version, and dependencies.

The most important fields in the `fabric.mod.json` file are:

- `id`: The mod's ID, which should be unique.
- `name`: The mod's name.
- `environment`: The environment that your mod runs in, such as `client`, `server`, or `*` for both.
- `entrypoints`: The entrypoints that your mod provides, such as `main` or `client`.
- `depends`: The mods that your mod depends on.
- `mixins`: The mixins that your mod provides.

You can see an example `fabric.mod.json` file below - this is the `fabric.mod.json` file for the mod that powers this documentation site.

::: details `fabric.mod.json` of the Example Mod
@[code lang=json](@/reference/latest/src/main/resources/fabric.mod.json)
:::

## Entrypoints {#entrypoints}

As mentioned before, the `fabric.mod.json` file contains a field called `entrypoints` - this field is used to specify the entrypoints that your mod provides.

The template mod generator creates both a `main` and `client` entrypoint by default:

- The `main` entrypoint is used for common code, and it's contained in a class which implements `ModInitializer`
- The `client` entrypoint is used for client-specific code, and its class implements `ClientModInitializer`

These entrypoints are called respectively when the game starts.

Here's an example of a simple `main` entrypoint that logs a message to the console when the game starts:

@[code lang=java transcludeWith=#entrypoint](@/reference/latest/src/main/java/com/example/docs/ExampleMod.java)

## `src/main/resources` {#src-main-resources}

The `src/main/resources` folder is used to store the resources that your mod uses, such as textures, models, and sounds.

It's also the location of `fabric.mod.json` and any mixin configuration files that your mod uses.

Assets are stored in a structure that mirrors the structure of resource packs - for example, a texture for a block would be stored in `assets/mod-id/textures/block/block.png`.

## `src/client/resources` {#src-client-resources}

The `src/client/resources` folder is used to store client-specific resources, such as textures, models, and sounds that are only used on the client side.

## `src/main/java` {#src-main-java}

The `src/main/java` folder is used to store the Java source code for your mod - it exists on both the client and server environments.

## `src/client/java` {#src-client-java}

The `src/client/java` folder is used to store client-specific Java source code, such as rendering code or client-side logic - such as block color providers.
