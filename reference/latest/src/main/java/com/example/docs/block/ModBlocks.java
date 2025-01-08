package com.example.docs.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import com.example.docs.FabricDocsReference;
import com.example.docs.block.custom.CounterBlock;
import com.example.docs.block.custom.EngineBlock;
import com.example.docs.block.custom.PrismarineLampBlock;
import com.example.docs.item.ModItems;

// :::1
public class ModBlocks {
	// :::1

	// :::2
	public static final RegistryKey<Block> CONDENSED_DIRT_KEY = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FabricDocsReference.MOD_ID, "condensed_dirt"));
	public static final Block CONDENSED_DIRT = register(
			new Block(AbstractBlock.Settings.create().registryKey(CONDENSED_DIRT_KEY).sounds(BlockSoundGroup.GRASS)),
			CONDENSED_DIRT_KEY,
			true
	);
	// :::2
	// :::3
	public static final RegistryKey<Block> CONDENSED_OAK_LOG_KEY = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FabricDocsReference.MOD_ID, "condensed_oak_log"));
	public static final Block CONDENSED_OAK_LOG = register(
			new PillarBlock(
					AbstractBlock.Settings.create()
							.registryKey(CONDENSED_OAK_LOG_KEY)
							.sounds(BlockSoundGroup.WOOD)
			), CONDENSED_OAK_LOG_KEY, true
	);
	// :::3
	// :::4
	public static final RegistryKey<Block> PRISMARINE_LAMP_KEY = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FabricDocsReference.MOD_ID, "prismarine_lamp"));
	public static final Block PRISMARINE_LAMP = register(
			new PrismarineLampBlock(
					AbstractBlock.Settings.create()
							.registryKey(PRISMARINE_LAMP_KEY)
							.sounds(BlockSoundGroup.LANTERN)
							.luminance(PrismarineLampBlock::getLuminance)
			), PRISMARINE_LAMP_KEY, true
	);
	// :::4
	public static final RegistryKey<Block> ENGINE_BLOCK_KEY = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FabricDocsReference.MOD_ID, "engine"));
	public static final Block ENGINE_BLOCK = register(
			new EngineBlock(AbstractBlock.Settings.create().registryKey(ENGINE_BLOCK_KEY)), ENGINE_BLOCK_KEY, true
	);

	// :::5
	public static final RegistryKey<Block> COUNTER_BLOCK_KEY = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FabricDocsReference.MOD_ID, "counter_block"));
	public static final Block COUNTER_BLOCK = register(
			new CounterBlock(AbstractBlock.Settings.create().registryKey(COUNTER_BLOCK_KEY)), COUNTER_BLOCK_KEY, true
	);
	// :::5

	// :::1
	public static Block register(Block block, RegistryKey<Block> blockKey, boolean shouldRegisterItem) {
		// Sometimes, you may not want to register an item for the block.
		// Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
		if (shouldRegisterItem) {
			RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());

			BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
			Registry.register(Registries.ITEM, itemKey, blockItem);
		}

		return Registry.register(Registries.BLOCK, blockKey, block);
	}

	// :::1
	public static void initialize() {
		// :::3
		ItemGroupEvents.modifyEntriesEvent(ModItems.CUSTOM_ITEM_GROUP_KEY).register((itemGroup) -> {
			itemGroup.add(ModBlocks.CONDENSED_DIRT.asItem());
		});
		// :::3

		ItemGroupEvents.modifyEntriesEvent(ModItems.CUSTOM_ITEM_GROUP_KEY).register((itemGroup) -> {
			itemGroup.add(ModBlocks.CONDENSED_OAK_LOG.asItem());
			itemGroup.add(ModBlocks.PRISMARINE_LAMP.asItem());
			itemGroup.add(ModBlocks.COUNTER_BLOCK.asItem());
			itemGroup.add(ModBlocks.ENGINE_BLOCK.asItem());
		});
	}

	// :::1
}
// :::1
