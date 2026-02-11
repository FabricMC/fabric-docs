package com.example.docs.datagen;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import com.example.docs.ModLootTables;

// :::datagen-loot-tables:chest-provider
public class ExampleModChestLootTableProvider extends SimpleFabricLootTableProvider {
	public ExampleModChestLootTableProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(output, registryLookup, LootContextParamSets.CHEST);
	}

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
		// :::datagen-loot-tables:chest-provider
		// :::datagen-loot-tables:chest-loot
		lootTableBiConsumer.accept(ModLootTables.TEST_CHEST_LOOT, LootTable.lootTable()
				.withPool(LootPool.lootPool() // One pool
						.setRolls(ConstantValue.exactly(2.0f)) // That has two rolls
						.add(LootItem.lootTableItem(Items.DIAMOND) // With an entry that has diamond(s)
								.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f)))) // One diamond
						.add(LootItem.lootTableItem(Items.DIAMOND_SWORD) // With an entry that has a plain diamond sword
						)
				));
		// :::datagen-loot-tables:chest-loot
		// :::datagen-loot-tables:chest-provider
	}
}
// :::datagen-loot-tables:chest-provider
