package com.example.docs.datagen;

import com.example.docs.block.ModBlocks;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;
// :::datagen-loot-tables:block-provider
public class FabricDocsReferenceBlockLootTableProvider extends FabricBlockLootTableProvider {

	protected FabricDocsReferenceBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
		// :::datagen-loot-tables:block-provider
		// :::datagen-loot-tables:block-drops
		// Make condensed dirt drop its block item.
		// Also adds the condition that it survives the explosion that broke it, if applicable,
		addDrop(ModBlocks.CONDENSED_DIRT);
		// Make prismarine lamps drop themselves with silk touch only
		addDropWithSilkTouch(ModBlocks.PRISMARINE_LAMP);
		// Make condensed oak logs drop between 7 and 9 oak logs
		addDrop(ModBlocks.CONDENSED_OAK_LOG, new LootTable.Builder().pool(
				new LootPool.Builder().rolls(new UniformLootNumberProvider(new ConstantLootNumberProvider(7), new ConstantLootNumberProvider(9)))
		));
		// :::datagen-loot-tables:block-drops
		// :::datagen-loot-tables:block-provider
	}
}
// :::datagen-loot-tables:block-provider
