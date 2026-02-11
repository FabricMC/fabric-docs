package com.example.docs;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

// :::datagen-loot-tables:mod-loot-tables
public class ModLootTables {
	public static ResourceKey<LootTable> TEST_CHEST_LOOT = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(FabricDocsReference.MOD_ID, "chests/test_loot"));
}
// :::datagen-loot-tables:mod-loot-tables
