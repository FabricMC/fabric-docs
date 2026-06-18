---
title: Dynamic Registry
description: An introduction to dynamic registries - what are they, what are they useful for, and how to create your own with the Fabric API.
authors:
  - Jimmy474
---

A **registry** is a centralized "phonebook" that maps unique IDs such as `minecraft:items` to specific objects. Static registries such as the block and item registries are frozen during startup, but dynamic registries are populated at runtime from JSON files in data packs.

Dynamic registries act as a bridge between your hardcoded logic and external data supplied by players, modpacks, or other mods.

They are useful for many reasons:

- They separate logic from content.
- Other modders can add new content through data packs instead of patching your code.
- Players can override default values, such as mana cost or upgrade price, by replacing data entries in a data pack.
- Dynamic registry data is world-specific. It loads when a world opens and is cleared when that world closes.
- Dynamic registries solve the "hardcoded content" problem. Instead of baking every skill, quest, or upgrade directly into Java code with enums or static lists, you define a blueprint in code and let the actual content come from data.

## Creating a Dynamic Registry {#creating-a-dynamic-registry}

Let's create a dynamic registry for a magic skill system.

### Class Setup {#class-setup}

First, create the class that represents a registry entry. It is a simple data holder for values tied to each magic skill like name, mana cost, etc. A [`Codec`](./codecs) is required to encode and decode the entry.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MagicSkillsRegistryEntry.java#main

- `name` is the name of the skill.
- `manaCost` is the mana cost of the skill.
- `onUseMcFunction` is a [function](<https://minecraft.wiki/w/Function_(Java_Edition)>) that the server can execute when the skill is used. Having this in the registry will let other datapacks customize the logic of any skill, or add new skills with custom functions.

### Registering the Registry {#registering-the-registry}

Each registry is registered with a key that uniquely identifies it, so let's declare a key in a Registries class:

::: tip

Declaring the registry keys in a common class is recommended because it will make it easier to manage multiple registries.

:::

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#main

Call `ExampleModRegistries.initialize()` from your [mod's initializer](./getting-started/project-structure#entrypoints).

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModDynamicRegistries.java#main

Then register it with Fabric API's `DynamicRegistries`, which provides two distinct strategies: `DynamicRegistries.register()`, or `DynamicRegistries.registerSynced()`.

#### Using `register()` {#using-register}

`DynamicRegistries.register()` creates a non-synced registry. It is loaded on the server only, and is not available on the client. Use this when the client never needs to read the registry.

It's not relevant in our example, but here's how to do it otherwise:

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#simple

#### Using `registerSynced()` {#using-register-synced}

`DynamicRegistries.registerSynced()` creates a synced registry. When a client joins a world, the server automatically synchronizes that registry's data with the client. Use this when the client needs the data for rendering, UI, tooltips, or other client-side logic.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#synced

`DynamicRegistries.registerSynced()` has an overload that accepts a second codec for client-side decoding. This is useful if the client does not need every field from the full server entry.

In our case, we only need the [`name`](#class-setup) and [`manaCost`](#class-setup) field on the client side, so let's create a [`Codec`](./codecs) that doesn't include [`onUseMcFunction`](#class-setup):

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/MagicSkillsRegistryEntry.java#client_codec
<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#double_codec

#### `SyncOption` {#sync-option}

Both overloads of `DynamicRegistries.registerSynced()` accept `SyncOption` arguments at the end to configure synchronization behavior. The only available option to use is:

- `SKIP_WHEN_EMPTY`: Synchronizes the registry only when it contains entries. This can help with compatibility for clients that may not need the registry.

Example:

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#with_option

### Populating the Registry {#populating-the-registry}

JSON files are used for creating registry entries. The JSON structure must match the [`MagicSkillsRegistryEntry`](#class-setup). In this example, our entry class has three fields, so the JSON file for `healing_skill` entry might look like this:

<<< @/reference/latest/src/main/generated/data/example-mod/example-mod/magic_skills_synced_registry/healing_skill.json

Entry JSON files are stored under `src/main/resources/data/example-mod/example-mod/magic_skills_registry/`.

::: info

The repeated `example-mod/example-mod` is not a mistake.

The first `example-mod` is the namespace of the entry being added. The second `example-mod` comes from the registry ID itself. Using your mod ID for both is normal, and it allows other mods or data packs to add entries to your registry under their own namespace.

For example, `another-mod` might want to add elements to our `magic_skills_registry`, and it would do that with files under `src/main/resources/data/another-mod/example-mod/magic_skills_registry/`.

:::

#### Entry ID {#entry-id}

The entry ID is a unique key for each entry, and it can be useful for accessing a specific entry from a registry. It is composed of the filename and the [registry key](#registering-the-registry). For example, since our entry JSON file is named `healing_skill.json`, the entry ID is:

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#entry_id

### Accessing the Registry Data {#accessing-the-registry-data}

Dynamic registries are loaded with the world, and can be accessed through the `RegistryAccess` class by using your registry key.
Instances of `RegistryAccess` can be acquired from many classes, but the most common ones are `MinecraftServer`, `ServerLevel`, `ClientLevel`, `Entity`, and more.

::: warning

When accessing the `RegistryAccess` instance from a client-only class, such as `ClientLevel`, only [synced registries](#using-register-synced) are available.

:::

#### Get the Entire Registry {#get-the-entire-registry}

Registries can be accessed using the `lookup` method of `RegistryAccess` which returns a `Optional<Registry<T>>` where `T` is the type of the registry.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#get_registry

#### Get a Specific Entry {#get-a-specific-entry}

Specific entries can be accessed using the `get` method of `RegistryAccess` which returns a `Optional<Holder.Reference<T>>` where `T` is the type of the registry.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModRegistries.java#get_specific_registry_entry

Read [Entry ID](#entry-id) to know how to get the `HEALING_SKILL_ENTRY_ID`.

In our case we can use this method to get the entry for magic skill used by user on server, then extract the [`onUseMcFunction`](#class-setup) field to execute the mcfunction.

#### Iterate Over All Entries {#iterate-over-all-entries}

Registry entries can be iterated over for various purposes like UI population. In our case we can use this method to populate a screen with custom widgets like this:

<<< @/reference/latest/src/client/java/com/example/docs/dynamic_registries/screens/ExampleModMagicSkillsScreen.java#iterate_over_registry_entries

::: details Screen Example

![Magic Skills Screen Example](/assets/develop/dynamic_registry/magic_skills_screen.png)

Learn more about creating [Custom Screens](./rendering/gui/custom-screens) and [Custom Widgets](./rendering/gui/custom-widgets)

:::

### Tags For Custom Registry Entries {#tags-for-custom-registry-entries}

Tags are a way to group multiple entries together. For example, we can create tags like _attack_ and _defense_ to group similar magic skills together.

For example, the attacking tag would be defined under `data/example-mod/tags/example-mod/magic_skills_registry/attacking_skills.json`:

<<< @/reference/latest/src/main/generated/data/example-mod/tags/example-mod/magic_skills_synced_registry/attacking_skills.json

#### Using Tags In Code {#using-tags-in-code}

Create a tag key for the tag to check if entries are present in the tag or not.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModTags.java#tag

We can use this method to check if a skill is an attacking skill or not.

<<< @/reference/latest/src/main/java/com/example/docs/dynamic_registries/ExampleModTags.java#tag_usage
