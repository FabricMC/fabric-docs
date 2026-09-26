---
title: Populate Dynamic Registry
description: A guide to populate a dynamic registry with datagen.
authors:
  - Jimmy474
---

::: info PREREQUISITES

Make sure you've completed the [datagen setup](./setup) process first, and you know what a [dynamic registry](../registries/dynamic-registry.md) is.

:::

## Dynamic Registry Provider {#dynamic-registry-provider}

Create a class that extends the `FabricDynamicRegistryProvider`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDynamicRegistriesProvider.java#main

### Name {#name}

Override the `getName()` method and provide a unique name for your dynamic registry provider.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDynamicRegistriesProvider.java#name

### Configure {#configure}

Override the `configure()` method and add your registry key to the entries using the `entries.addAll()` method.
The `entries.addAll()` method tells the datagen provider to take all entries from this particular registry and include them in the generated datapack.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDynamicRegistriesProvider.java#configure

You can add more registry keys to the entries if you have more than one dynamic registry using the `entries.addAll()` method multiple times.

### Registering The Provider

Add the provider to the `onInitializeDataGenerator()` method which is in the class that implements `DataGeneratorEntryPoint`.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_dynamic_registries

### Bootstraps {#bootstraps}

Create a bootstrap method for each dynamic registry you have, and for each method parameter's generic type use that [registry's entry](../registries/dynamic-registry.md#class-setup) class.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDynamicRegistriesProvider.java#bootstrap

Use the `context` to add each entry to the registry. The first parameter of the `context.register()` method is the [registry entry's id](../registries/dynamic-registry.md#entry-id),
and the second parameter is an instance of [registry's entry](../registries/dynamic-registry.md#class-setup) class.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDynamicRegistriesProvider.java#entry

If you have more entries, add them in the same way.

### Registry Builder

In your mod's data generator entry point class override the `buildRegistry` method and use the `RegistrySetBuilder` parameter to link all your dynamic registries with the registry's bootstrap method using the `add()` method.
The first parameter in the `add()` method takes the [registry key](../registries/dynamic-registry.md#registering-the-registry) and the second parameter takes the bootstrap method reference.

This is how the generator knows which method to use to actually populate that registry.

<<< @/reference/latest/src/client/java/com/example/docs/datagen/ExampleModDataGenerator.java#datagen_magic_skills_dynamic_registries_bootstrap
