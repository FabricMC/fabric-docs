package com.example.docs;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

// :::datagen-loot-tables:mod-loot-tables
public class ModLootTables {
	public static ResourceKey<LootTable> TEST_CHEST_LOOT = ResourceKey.create(Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "chests/test_loot"));
}
// :::datagen-loot-tables:mod-loot-tables
