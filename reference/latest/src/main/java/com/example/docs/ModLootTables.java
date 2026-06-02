package com.example.docs;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

// #region datagen_loot_tables__mod_loot_tables
public class ModLootTables {
	public static ResourceKey<LootTable> TEST_CHEST_LOOT = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "chests/test_loot"));
	// #endregion datagen_loot_tables__mod_loot_tables
	public static ResourceKey<LootTable> ADVANCEMENT_COLLECT_NETHER_STAR = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "advancements/collect_nether_star"));
	// #region datagen_loot_tables__mod_loot_tables
}
// #endregion datagen_loot_tables__mod_loot_tables
