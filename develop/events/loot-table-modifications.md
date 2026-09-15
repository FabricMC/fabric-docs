---
title: Loot Table Modifications
description: A guide for modifying loot tables using events provided by the Fabric API.
authors:
  - NotNightSky
  - its-miroma
  - cassiancc
---

The loot table system determines what items are dropped when a block is broken, an entity is killed, or a chest is opened. Fabric API gives you several ways to modify, replace, and post-process loot tables during loading, and to adjust the final drops at runtime.

## Loot Table Events {#loot-table-events}

The Fabric Loot API provides several events through the `LootTableEvents` class. The table below shows what each event is used for:

| Event                          | Function                                                                  | Notes                                                         |
| ------------------------------ | ------------------------------------------------------------------------- | ------------------------------------------------------------- |
| `LootTableEvents.MODIFY`       | Keep the original loot table and add pools and entries.                   | Best for most small changes.                                  |
| `LootTableEvents.REPLACE`      | Discard the original table and provide a new one.                         | Use this when the original structure is no longer useful.     |
| `LootTableEvents.MODIFY_DROPS` | Change the final list of `ItemStack` drops after loot has been generated. | Useful when many tables should follow the same runtime rules. |
| `LootTableEvents.ALL_LOADED`   | Inspect or validate all tables after loading is complete.                 | Good for post-processing and global setup.                    |

These events occur in a specific order during the loot table loading process:

```Text
  Loading World
     |
  REPLACE
     |
  MODIFY
     |
  ALL_LOADED
     |
  Runtime Loot Generation
     |
  MODIFY_DROPS
```

When using these events, remembering this order is important, as it affects how your changes interact with other events and the original loot tables.

### Modifying Loot Tables {#modifying-loot-tables}

Use `LootTableEvents.MODIFY` when you want to change an existing loot table while keeping its original contents intact. The callback gives you a `LootTable.Builder`, so you can add new pools or add entries to existing pools without rebuilding the whole table.

This is usually the best choice for adding items to vanilla or data-pack tables, such as adding a custom item to a block's existing drops. You can inspect the `source` parameter to see whether the table came from built-in resources, a data pack, or another replacement event.

Use `MODIFY` when the original loot table should remain mostly intact.

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_modify_event

Effects of the above example:

<VideoPlayer src="/assets/develop/events/modify_event_example.webm">Modify Event Example</VideoPlayer>

### Replacing Loot Tables {#replacing-loot-tables}

Use `LootTableEvents.REPLACE` when you want to discard an existing loot table and provide a new one.

The callback receives the original `LootTable`. Return a new `LootTable` to replace it, or return `null` to keep it intact. Once a listener replaces a table, no other replacement listeners will be called on it.

This event is useful when the original table is incompatible with your mod's behavior and modifying individual loot pools would be more complicated than creating a new table.

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_replace_event

::: warning

Always return `null` if you are not replacing a loot table. Returning the original table still marks the loot table as replaced, which prevents later replacement listeners from running and fails the `isBuiltIn()` check.

:::

Effects of the above example:

<VideoPlayer src="/assets/develop/events/replace_event_example.webm">Replace Event Example</VideoPlayer>

### Modifying Loot Table Drops {#modifying-loot-table-drops}

`LootTableEvents.MODIFY_DROPS` doesn't run until the loot has been generated at runtime. This event is useful when:

- The number of loot tables is unknown or numerous.
- The same rules should apply to many loot tables.
- You want to inspect the `LootContext`, such as the entity, tool, or damage source.
- Adding a custom loot function to every table would be inconvenient.

The drops list can be modified directly by adding, removing, or changing item stacks. Note that, because this event runs after loot generation, it cannot change the loot table's pools, entries, or conditions.

::: info

The drops may already be separated into stacks if the loot table requested a particular stack size.

:::

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_modify_drops_event

Effects of the above example:

<VideoPlayer src="/assets/develop/events/modify_drops_event_example.webm">Modify Drops Event Example</VideoPlayer>

### Loot Table Post-Processing {#loot-table-post-processing}

Use `LootTableEvents.ALL_LOADED` for work that should happen after every loot table has been loaded, and after the `REPLACE` and `MODIFY` events have triggered.

The event provides the server's `ResourceManager` and the complete loot table registry. This makes it suitable for inspecting loaded tables, validating them, collecting information, or performing additional setup that depends on all tables being available.

::: info

This event is not used to add drops during loot generation. For changing a table, use [`MODIFY`](#modifying-loot-tables) or [`REPLACE`](#replacing-loot-tables). For changing generated item stacks, use [`MODIFY_DROPS`](#modifying-loot-table-drops).

:::

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_all_loaded_event

### Predicates {#predicates}

Loot conditions, internally called predicates, control whether a loot pool, entry, or function can be used. They are especially useful with `MODIFY` and `REPLACE`, where they let you make added or replacement drops conditional without handling every case in Java code. See the `.when(...)` calls in the `MODIFY` and `REPLACE` examples above. The same conditions can help when designing replacement tables, while `MODIFY_DROPS` requires equivalent checks to be performed in the event callback.

Below are examples of commonly used predicates, grouped by their purpose. See the `net.minecraft.world.level.storage.loot.predicates` package and the [Minecraft Wiki predicate list](https://minecraft.wiki/w/Predicate) for the full set:

#### Logic Predicates {#logic-predicates}

These combine or invert other conditions.

- `AllOfCondition`: every condition must pass. Use this when you want an **AND** check.
- `AnyOfCondition`: at least one condition must pass. Use this when you want an **OR** check.
- `InvertedLootItemCondition`: flips another condition so it passes only when the original one fails. Use this when you need **NOT** logic.

::: tip

These logical predicates can be nested to create complex conditions. For example, you can combine `AllOfCondition` and `AnyOfCondition` to create a condition that requires multiple checks to pass, while allowing for some flexibility in the requirements.

:::

#### World-State Predicates {#world-state-predicates}

These check things about the world or the position where loot is generated.

- `WeatherCheck`: checks whether it is raining or thundering.
- `TimeCheck`: checks the time of day or a time range.
- `LocationCheck`: checks where the drop happened, such as the Y level or other location data.
- `EnvironmentAttributeCheck`: checks world-specific environment rules or attributes.

#### Block, Tool, and Entity Predicates {#block-tool-and-entity-predicates}

These look at the block being broken, the tool being used, or the entity that caused the loot.

- `ExplosionCondition`: makes a loot pool or entry apply only when the drop survives an explosion.
- `MatchTool`: checks whether the tool used to break a block matches a given item or item predicate.
- `LootItemBlockStatePropertyCondition`: checks the block state before it was broken, which is useful for crops and other stateful blocks.
- `LootItemKilledByPlayerCondition`: requires the entity to have been killed by a player.
- `DamageSourceCondition`: checks details about the damage source, such as whether the hit was direct or indirect.

#### Chance-Based Predicates {#chance-based-predicates}

These decide drops by probability or by enchantment level.

- `LootItemRandomChanceCondition`: gives a flat random chance for a drop.
- `LootItemRandomChanceWithEnchantedBonusCondition`: changes the chance based on enchantment level.
- `BonusLevelTableCondition`: a helper for enchantment-scaled loot chances, such as Fortune.

::: warning

`LootItemRandomChanceWithEnchantedBonusCondition` and `LootItemRandomChanceCondition` should not be used together in the same pool, as both of them define base chance and may cause unintended behavior.

:::

#### Shared Predicate References {#shared-predicate-references}

- `ConditionReference`: points to a data-driven loot condition defined elsewhere and reused in multiple tables.
