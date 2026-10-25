package com.example.docs.datagen;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.Sum;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableSubProvider;

import com.example.docs.ModLootTables;

public class ExampleModAdvancementRewardLootTableProvider extends SimpleFabricLootTableSubProvider {
	public ExampleModAdvancementRewardLootTableProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, registryLookupFuture, LootContextParamSets.ADVANCEMENT_REWARD);
	}

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
		output.accept(ModLootTables.ADVANCEMENT_COLLECT_NETHER_STAR, LootTable.lootTable()
				.withPool(LootPool.lootPool()
						.setRolls(Sum.sum(ConstantValue.exactly(1), BinomialDistributionGenerator.binomial(2, 0.2f)))
						.add(LootItem.lootTableItem(Items.WITHER_SKELETON_SKULL))
				)
		);
	}
}
