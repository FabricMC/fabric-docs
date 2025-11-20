package com.example.docs;

import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

// :::datagen-loot-tables:mod-loot-tables
public class ModLootTables {
	public static RegistryKey<LootTable> TEST_CHEST_LOOT = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(ExampleMod.MOD_ID, "chests/test_loot"));
}
// :::datagen-loot-tables:mod-loot-tables
