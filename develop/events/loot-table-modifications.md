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

### Replacing Loot Tables {#replacing-loot-tables}

### Modifying Loot Table Drops {#modifying-loot-table-drops}
