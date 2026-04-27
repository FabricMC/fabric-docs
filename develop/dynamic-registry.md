---
title: Dynamic Registry
description: A guide to creating your own dynamic registry with the Fabric API.
authors:
  - Jimmy474
---

This page explains what a dynamic registry is, why it is useful, and how to create and use one with the Fabric API.

## General Information

### What Is a Dynamic Registry? {#what-is-a-dynamic-registry}

A registry is a centralized _phonebook_ that maps unique IDs such as `minecraft:items` to specific objects. Static registries such as the block and item registries are frozen during startup, but dynamic registries are populated at runtime from JSON files in data packs.

Dynamic registries act as a bridge between your hardcoded logic and external data supplied by players, modpacks, or other mods.

### Why Are They Useful? {#why-are-they-useful}

1. They separate your logic from your content.
2. Other modders can add new content through data packs instead of patching your code.
3. Players can override default values, such as a mana cost or upgrade price in a skills registry, by replacing data entries in a data pack.
4. Dynamic registry data is world-specific. It loads when a world opens and is cleared when that world closes.

### What Problems Do They Solve? {#what-problems-do-they-solve}

Dynamic registries solve the "hardcoded content" problem. Instead of baking every skill, quest, or upgrade directly into Java code with enums or static lists, you define a blueprint in code and let the actual content come from data.

## Creating a Dynamic Registry {#creating-a-dynamic-registry}

### Class Setup {#class-setup}

First, create the class that represents one registry entry.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MyModRegistryEntry.java#main

### Registering the Registry {#registering-the-registry}

First, Create a key for the registry

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MyRegistries.java#main

Then register it with Fabric API's `DynamicRegistries` class.
There are two ways to register a dynamic registry:

1. `DynamicRegistries.register()`
2. `DynamicRegistries.registerSynced()`

#### Using `register()` {#using-register}

`DynamicRegistries.register()` creates a non-synced registry. It is loaded only on the server and is not available on the client.

Use this when the client never needs to read the registry. For example:

- A custom loot registry only used by server-side gameplay logic.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MyRegistries.java#simple

#### Using `registerSynced()` {#using-register-synced}

`DynamicRegistries.registerSynced()` creates a synced registry. When a client joins a world, the server automatically synchronizes that registry's data to the client.

Use this when the client needs the data for rendering, UI, tooltips, or other client-side logic. For example:

- A skill registry that the client reads to render a skill menu.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MyRegistries.java#synced

`DynamicRegistries.registerSynced()` has an overload that accepts a second codec for client-side decoding. This is useful if the client does not need every field from the full server entry.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MyModRegistryEntry.java#clientCodec
<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MyRegistries.java#doubleCodec

Both overloads of `DynamicRegistries.registerSynced()` also accept `SyncOption...` at the end to configure synchronization behavior.

#### `SyncOption` {#syncoption}

Available `SyncOption` values include:

1. `SKIP_WHEN_EMPTY`: Synchronizes the registry only when it contains entries. This can help with compatibility for clients that may not need the registry.

Example:

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MyRegistries.java#withOption

#### Initialize the Class {#initialize-the-class}

Call `MyRegistries.initialize()` from your [mod's initializer](./getting-started/project-structure#entrypoints).

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModDynamicRegistries.java#main

### Populating the Registry {#populating-the-registry}

To add entries to the registry, create JSON files under:

`src/main/resources/data/<entry namespace>/<registry namespace>/<registry path>/`

In this example, the registry key is:

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MyRegistries.java#key

That means the JSON files belong in:

`src/main/resources/data/example-mod/example-mod/my_registry_key/`

::: info

The repeated `example-mod/example-mod` is not a mistake.

The first `example-mod` is the namespace of the entry being added. The second `example-mod` comes from the registry ID itself. Using your mod ID for both is normal, and it still allows other mods or data packs to add entries to your registry under their own namespace.

:::

The JSON structure must match `MyModRegistryEntry`. The filename becomes the entry ID path, and it should usually be lowercase with underscores.

For example, if you create:

`src/main/resources/data/example-mod/example-mod/my_registry_key/my_registry_entry_1.json`

then the entry ID is:

`Identifier.fromNamespaceAndPath("example-mod", "my_registry_entry_1")`

Example JSON:

<<< @/reference/latest/src/main/resources/data/example-mod/example-mod/my_registry_key/my_registry_entry_1.json

### Accessing the Registry Data {#accessing-the-registry-data}

Dynamic registries are loaded with the world and can be accessed through `MinecraftServer#registryAccess()` by using your registry key.

#### Get the Entire Registry {#get-the-entire-registry}

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/AccessMyModRegistryEntry.java#getMyRegistry

#### Get a Specific Entry {#get-a-specific-entry}

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/AccessMyModRegistryEntry.java#getSpecificEntry

The `entryId` is built from the entry namespace and the JSON filename. For example:

- `data/example-mod/example-mod/my_registry_key/my_registry_entry_1.json` becomes `Identifier.fromNamespaceAndPath("example-mod", "my_registry_entry_1")`.

#### Iterate Over All Entries {#iterate-over-all-entries}

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/AccessMyModRegistryEntry.java#iterateOverMyRegistry

### Tags For Custom Registry Entries {#tags-for-custom-registry-entries}

Tags are a way to group multiple entries together. For example,

* If your custom registry is for skills, then you can create tags like _physical_, _magical_, etc. to group similar skills.

Tags are defined in `data/<entry namespace>/tags/<registry namespace>/<registry path>/`.

In this example, if we want to create a tag for our `MyModRegistryEntry`, we would create a tag file like:
`data/example-mod/tags/example-mod/my_registry_key/mytag.json`

<<< @/reference/latest/src/main/resources/data/example-mod/tags/example-mod/my_registry_key/mytag.json

#### Using Tags In Code {#using-tags-in-code}

Create a tag key for the tag to check if entries are present in the tag or not.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MyRegistryTags.java#tag
