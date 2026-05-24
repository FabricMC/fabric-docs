---
title: Dynamic Registry
description: A guide to creating your own dynamic registry with the Fabric API.
authors:
  - Jimmy474
---

This page explains what a dynamic registry is, why it is useful, and how to create and use one with the Fabric API.

## General Information {#general-information}

### What Is a Dynamic Registry? {#what-is-a-dynamic-registry}

A registry is a centralized _phonebook_ that maps unique IDs such as `minecraft:items` to specific objects. Static registries such as the block and item registries are frozen during startup, but dynamic registries are populated at runtime from JSON files in data packs.

Dynamic registries act as a bridge between your hardcoded logic and external data supplied by players, modpacks, or other mods.

### Why Are They Useful? {#why-are-they-useful}

1. They separate your logic from your content.
2. Other modders can add new content through data packs instead of patching your code.
3. Players can override default values, such as a mana cost or upgrade price in a skills registry, by replacing data entries in a data pack.
4. Dynamic registry data is world-specific. It loads when a world opens and is cleared when that world closes.
5. Dynamic registries solve the "hardcoded content" problem. Instead of baking every skill, quest, or upgrade directly into Java code with enums or static lists, you define a blueprint in code and let the actual content come from data.

## Creating a Dynamic Registry {#creating-a-dynamic-registry}

Let's create a dynamic registry for a magic skill system.

### Class Setup {#class-setup}

First, create the class that represents a registry entry. It is a simple data holder for values tied to each magic skill like name, mana cost, etc. A [`Codec`](./codecs.md) is required to encode and decode the entry.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MagicSkillsRegistryEntry.java#main

- `name` is the name of the skill.
- `manaCost` is the mana cost of the skill.
- `onUseMcFunction` is an mcfunction that the server can execute when the skill is used. having this in the registry will let other datapacks customize the logic of any skill or add new skills with their own mcfunction.

### Registering the Registry {#registering-the-registry}

Each registry requires a key that uniquely identifies that registry and is required for the registration, so create a key for your registry:

::: tip

While technically the keys can be defined anywhere, Using a common class for keys is recommended if you plan to have more registries in the future. That way it is easier to manage them.

:::

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#main

Then register it with Fabric API's `DynamicRegistries` class.
There are two ways to register a dynamic registry:

1. `DynamicRegistries.register()`
2. `DynamicRegistries.registerSynced()`

#### Using `register()` {#using-register}

`DynamicRegistries.register()` creates a non-synced registry. It is loaded only on the server and is not available on the client. Use this when the client never needs to read the registry. In our case we do need the client to have access to the magic registry, but here is how it can be used otherwise.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#simple

#### Using `registerSynced()` {#using-register-synced}

`DynamicRegistries.registerSynced()` creates a synced registry. When a client joins a world, the server automatically synchronizes that registry's data with the client. Use this when the client needs the data for rendering, UI, tooltips, or other client-side logic.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#synced

`DynamicRegistries.registerSynced()` has an overload that accepts a second codec for client-side decoding. This is useful if the client does not need every field from the full server entry. In our case we only need the [`name`](#class-setup) and [`manaCost`](#class-setup) field for client UI, so we create a [`Codec`](./codecs.md) that doesn't include the [`onUseMcFunction`](#class-setup) field.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MagicSkillsRegistryEntry.java#client_codec
<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#double_codec

#### `SyncOption` {#syncoption}

Both overloads of `DynamicRegistries.registerSynced()` accepts `SyncOption...` at the end to configure synchronization behavior.

Available `SyncOption` values include:

1. `SKIP_WHEN_EMPTY`: Synchronizes the registry only when it contains entries. This can help with compatibility for clients that may not need the registry.

Example:

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#with_option

#### Initialize the Class {#initialize-the-class}

Call `ExampleModRegistries.initialize()` from your [mod's initializer](./getting-started/project-structure#entrypoints).

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModDynamicRegistries.java#main

### Populating the Registry {#populating-the-registry}

JSON files are used for creating registry entries. The JSON structure must match [`MagicSkillsRegistryEntry`](#class-setup), In this example our entry class has three fields so the JSON file for _healing_spell_ entry should look like this

<<< @/reference/latest/src/main/resources/data/example-mod/example-mod/magic_skills_registry/healing_spell.json

Entry JSON files are stored at

`src/main/resources/data/<entry namespace>/<registry namespace>/<registry path>/`

For our [`MAGIC_SKILLS_REGISTRY_KEY`](#registering-the-registry) the JSON files belong at

`src/main/resources/data/example-mod/example-mod/magic_skills_registry/`

::: info

The repeated `example-mod/example-mod` is not a mistake.

The first `example-mod` is the namespace of the entry being added. The second `example-mod` comes from the registry ID itself. Using your mod ID for both is normal, and it allows other mods or data packs to add entries to your registry under their own namespace.

:::

#### Entry ID {#entry-id}
The entry ID is a unique key for each entry, and it can be useful for accessing a specific entry from a registry. The filename and the [registry key](#registering-the-registry) are used to make the entry ID. Our entry JSON file is named `healing_spell.json`, therefore, the entry ID is:

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#entry_id

### Accessing the Registry Data {#accessing-the-registry-data}

Dynamic registries are loaded with the world and can be accessed through `RegistryAccess` class by using your registry key.
Instances of `RegistryAccess` can be acquired from many classes but the most common ones are `MinecraftServer`, `ServerLevel`, `ClientLevel`, `Entity`, etc.

::: warning

Only **synced registries** are available if accessing the `RegistryAccess` instance from a Client only class such as `ClientLevel`.

:::

#### Get the Entire Registry {#get-the-entire-registry}

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#get_registry

Usage:

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#get_registry_usage

#### Get a Specific Entry {#get-a-specific-entry}

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#get_specific_registry_entry

Usage:

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#get_specific_registry_entry_usage

Read [Entry ID](#entry-id) to know how to get the `entryId`.

In our case we can use this method to get the entry for magic skill used by user on server, then extract the [`onUseMcFunction`](#class-setup) field to execute the mcfunction.

#### Iterate Over All Entries {#iterate-over-all-entries}

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#iterate_over_registry_entries

Usage:

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#iterate_over_registry_entries_usage

In our case we can use this method to get all the entries in the registry and show them in a client UI.

### Tags For Custom Registry Entries {#tags-for-custom-registry-entries}

Tags are a way to group multiple entries together. For example, we can create tags like _defensive_, _attacking_, etc. to group similar magic skills together.

Tags are defined in

`data/<entry namespace>/tags/<registry namespace>/<registry path>/`.

In this example, if we want to create a tag for our [`MagicSkillsRegistryEntry`](#class-setup), we would create a tag file like:

`data/example-mod/tags/example-mod/magic_skills_registry/attacking_skills.json`

<<< @/reference/latest/src/main/resources/data/example-mod/tags/example-mod/magic_skills_registry/attacking_skills.json

#### Using Tags In Code {#using-tags-in-code}

Create a tag key for the tag to check if entries are present in the tag or not.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModTags.java#tag

We can use this method to check if a spell is an attacking spell or not.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModTags.java#tag_usage
