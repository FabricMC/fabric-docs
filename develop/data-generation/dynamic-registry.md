---
title: Dynamic Registry Population
description: A guide to populate a dynamic registry with datagen.
authors:
  - Jimmy474
---

<!---->

::: info PREREQUISITES

Make sure you've completed the [datagen setup](./setup) process and have followed the [dynamic registry](../registries/dynamic-registry) docs first.

:::

## Dynamic Registry Provider {#dynamic-registry-provider}

Create a class that extends the `FabricDynamicRegistryProvider`, and override the `getName()` method to provide a unique name for your dynamic registry provider.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDynamicRegistriesProvider.java#main

Add the provider to the `onInitializeDataGenerator()` method in the class that implements `DataGeneratorEntryPoint`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_dynamic_registries

### Configure {#configure}

Override the `configure()` method of the provider class and add your registry key to the entries using the `entries.addAll()` method.
This tells the datagen provider to take all entries from this particular registry and include them in the generated datapack.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDynamicRegistriesProvider.java#configure

If you have more than one dynamic registry, you can add more registry keys using the `entries.addAll()` method multiple times.

## Bootstraps {#bootstraps}

For each dynamic registry you have, create a bootstrap method with a parameter of type `BootstrapContext<T>`, where `T` is the [registry entry class](../registries/dynamic-registry#class-setup). For our example, we'll use `MagicSkillsRegistryEntry`:

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDynamicRegistriesProvider.java#bootstrap

Add each entry to the registry by calling `context.register()`. It takes in two parameters: the [registry entry's ID](../registries/dynamic-registry#entry-id),
and an instance of the [registry entry's class](../registries/dynamic-registry#class-setup):

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDynamicRegistriesProvider.java#entry

If you have more entries, add them in the same way.

## Registry Builder {#registry-builder}

Now, we need to link each of our dynamic registries with the bootstrap method that will populate them.

In your data generation entrypoint, override the `buildRegistry` method. Use the `RegistrySetBuilder` to link each dynamic registry with its bootstrap method, by calling `registryBuilder.add()`, and passing the [registry key](../registries/dynamic-registry#registering-the-registry) and a reference to the [bootstrap method](#bootstraps).

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_magic_skills_dynamic_registries_bootstrap
