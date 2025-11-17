package com.example.docs.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

import com.example.docs.block.ModBlocks;

// :::datagen-loot-tables:block-provider
public class ExampleModBlockLootTableProvider extends FabricBlockLootTableProvider {
	protected ExampleModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
		// :::datagen-loot-tables:block-provider
		// :::datagen-loot-tables:block-drops
		// Make condensed dirt drop its block item.
		// Also adds the condition that it survives the explosion that broke it, if applicable,
		dropSelf(ModBlocks.CONDENSED_DIRT);
		// Make prismarine lamps drop themselves with silk touch only
		dropWhenSilkTouch(ModBlocks.PRISMARINE_LAMP);
		// Make condensed oak logs drop between 7 and 9 oak logs
		add(ModBlocks.CONDENSED_OAK_LOG, LootTable.lootTable().withPool(applyExplosionCondition(Items.OAK_LOG, LootPool.lootPool()
				.setRolls(new UniformGenerator(new ConstantValue(7), new ConstantValue(9)))
				.add(LootItem.lootTableItem(Items.OAK_LOG))))
		);
		// :::datagen-loot-tables:block-drops
		// :::datagen-loot-tables:block-provider
	}
}
// :::datagen-loot-tables:block-provider
