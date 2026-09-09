---
title: Loot Table Modifications
description: A guide for modifying loot tables using events provided by the Fabric API.
authors:
  - NotNightSky
---

The loot table system is used to determine what items are dropped when a block is broken, an entity is killed, or a chest is opened. These loot tables can be created, modified, and removed by using the Fabric API.

## Loot Table Events {#loot-table-events}

The Fabric Loot API provides several events accessed via the `LootTableEvents` class. These events allow you to modify loot tables at runtime, enabling you to add, remove, or change the items that are dropped in various situations.

- `LootTableEvents.MODIFY`: This event is triggered when a loot table is being modified. You can use this event to add or remove entries from the loot table.
- `LootTableEvents.REPLACE`: This event can be used to modify loot tables. The main use case is to add items to vanilla or mod loot tables
- `LootTableEvents.MODIFY_DROPS`: This event can be used for cases where the `MODIFY` and `REPLACE` events are inconvenient. Such as when you want to modify the final drops of all loot tables at once under specific conditions.
- `LootTableEvents.ALL_LOADED`: This event can be used for post-processing after all loot tables have been loaded and modified by Fabric.

### Modifying Loot Tables {#modifying-loot-tables}

Use `LootTableEvents.MODIFY` when you want to change an existing loot table while keeping its original contents intact. The event provides a `LootTable.Builder`, allowing you to add loot pools or modify existing pools.

This is usually chosen for adding items to vanilla or data-packs, such as adding a custom item to a block's existing drops. The `source` parameter can be checked to determine whether the table came from built-in resources, a data-pack, or another replacement event.

<!--TODO: It seemed like it is possible to modify data-pack LPs but have not tested it yet. -->

Use `MODIFY` when the original loot table should remain mostly intact.

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_modify_event

### Replacing Loot Tables {#replacing-loot-tables}

Use `LootTableEvents.REPLACE` when you want to discard an existing loot table and provide a new one.

The callback receives the original `LootTable`. Returns a new `LootTable` to replace it, or return `null` to leave it unchanged. Once a listener replaces a table, later replacement listeners are not called for that table.

This event is useful when the original table is incompatible with your mod's behavior and modifying individual loot pools would be more complicated than creating a new table.

::: info

Unlike `MODIFY`, `REPLACE` is not intended for simply adding an item to an existing table. Use `MODIFY` for that.

:::

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_replace_event

### Modifying Loot Table Drops {#modifying-loot-table-drops}

Use `LootTableEvents.MODIFY_DROPS` when you need to modify the final list of `ItemStack` drops after a loot table has generated them.

This event is useful when:

- The number of loot tables are unknown or numerous.
- The same rules should apply to many loot tables.
- You want to inspect the `LootContext`, such as the entity, tool, or damage source.
- Adding a custom loot function to every table would be inconvenient.
- The drops list can be modified directly by adding, removing, or changing item stacks. Note that, because this event runs after loot generation, it cannot change the loot table's pools, entries, or conditions.

::: info

The drops may already be separated into stacks if the loot table requested a particular stack size.

:::

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_modify_drops_event

### Loot Table Post-Processing {#loot-table-post-processing}

Use `LootTableEvents.ALL_LOADED` for work that should happen after every loot table has been loaded and after the REPLACE and MODIFY events have run.

The event provides the server's `ResourceManager` and the complete loot table registry. This makes it suitable for inspecting loaded tables, validating them, collecting information, or performing additional setup that depends on all tables being available.

::: info

This event is not normally used to add drops during loot generation. For changing a table, use `MODIFY` or `REPLACE`. For changing generated item stacks, use `MODIFY_DROPS`.

:::

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_all_loaded_event

### Predicates {#predicates}

The following conditions can be used to dynamically control when loot table entries and pools apply.

#### AllOfCondition {#allofcondition}

#### AnyOfCondition {#anyofcondition}

#### WeatherCheck {#weathercheck}

#### TimeCheck {#timecheck}

#### MatchTool {#matchtool}

#### LootItemRandomChanceWithEnchantedBonusCondition {#lootitemrandomchancewithenchantedbonuscondition}

#### LootItemRandomChanceCondition {#lootitemrandomchancecondition}

#### LootItemKilledByPlayerCondition {#lootitemkilledbyplayercondition}

#### LootItemBlockStatePropertyCondition {#lootitemblockstatepropertycondition}

#### LocationCheck {#locationcheck}

#### InvertedLootItemCondition {#invertedlootitemcondition}

#### EnvironmentAttributeCheck {#environmentattributecheck}

#### EnchantmentActiveCheck {#enchantmentactivecheck}

#### DamageSourceCondition {#damagesourcecondition}

#### ConditionReference {#conditionreference}

#### BonusLevelTableCondition {#bonusleveltablecondition}
