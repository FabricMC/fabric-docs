---
title: Loot Table Modifications
description: A guide for modifying loot tables using events provided by the Fabric API.
authors:
  - NotNightSky
---
### Adding Items to Existing Loot Tables {#adding-items-to-existing-loot-tables}

Sometimes you may want to add items to loot tables. For example, adding your drops to a vanilla block or entity.

The simplest solution, replacing the loot table file, can break other mods. What if they want to change them as well? We'll take a look at how you can add items to loot tables without overriding the table.

We'll be adding eggs to the coal ore loot table.

#### Listening to Loot Table Loading {#listening-to-loot-table-loading}

Fabric API has an event that is fired when loot tables are loaded, `LootTableEvents.MODIFY`. You can register a callback for it in your [mod's initializer](./getting-started/project-structure#entrypoints). Let's also check that the current loot table is the coal ore loot table:

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_table_events

#### Adding Items to the Loot Table {#adding-items-to-the-loot-table}

To add an item, we'll need to add a pool with an item entry to the loot table.

We can make a pool with `LootPool#lootPool`, and add it to the loot table.

Our pool doesn't have any items yet, so we'll make an item entry using `LootItem#lootTableItem` and add it to the pool.

<<< @/reference/latest/src/main/java/com/example/docs/event/ExampleModEvents.java#loot_pool_builder{5-7}
