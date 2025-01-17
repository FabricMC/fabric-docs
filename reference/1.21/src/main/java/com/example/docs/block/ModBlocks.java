package com.example.docs.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import com.example.docs.FabricDocsReference;
import com.example.docs.block.custom.CounterBlock;
import com.example.docs.block.custom.EngineBlock;
import com.example.docs.block.custom.PrismarineLampBlock;
import com.example.docs.item.ModItems;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

// :::1
public class ModBlocks {
	// :::1

	// :::2
	public static final Block CONDENSED_DIRT = register(
			new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRASS)),
			"condensed_dirt",
			true
	);
	// :::2
	// :::3
	public static final Block CONDENSED_OAK_LOG = register(
			new PillarBlock(
					AbstractBlock.Settings.create()
							.sounds(BlockSoundGroup.WOOD)
			), "condensed_oak_log", true
	);
	// :::3
	// :::4
	public static final Block PRISMARINE_LAMP = register(
			new PrismarineLampBlock(
					AbstractBlock.Settings.create()
							.sounds(BlockSoundGroup.LANTERN)
							.luminance(PrismarineLampBlock::getLuminance)
			), "prismarine_lamp", true
	);
	// :::4
	public static final Block ENGINE_BLOCK = register(
			new EngineBlock(AbstractBlock.Settings.create()), "engine", true
	);

	// :::5
	public static final Block COUNTER_BLOCK = register(
			new CounterBlock(AbstractBlock.Settings.create()), "counter_block", true
	);
	// :::5
	public static final Block QUESTION_BLOCK = register(
			new Block(AbstractBlock.Settings.create()), "question_block", true
	);

	// :::1
	public static Block register(Block block, String name, boolean shouldRegisterItem) {
		// Register the block and its item.
		Identifier id = Identifier.of(FabricDocsReference.MOD_ID, name);

		// Sometimes, you may not want to register an item for the block.
		// Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
		if (shouldRegisterItem) {
			BlockItem blockItem = new BlockItem(block, new Item.Settings());
			Registry.register(Registries.ITEM, id, blockItem);
		}

		return Registry.register(Registries.BLOCK, id, block);
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
		});
	}

	// :::1
}
// :::1
