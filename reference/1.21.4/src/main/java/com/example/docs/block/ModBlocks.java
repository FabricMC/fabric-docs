package com.example.docs.block;

import java.util.function.Function;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.data.family.BlockFamily;
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
import com.example.docs.block.custom.VerticalSlabBlock;
import com.example.docs.item.ModItems;

// :::1
public class ModBlocks {
	// :::1

	// :::2
	public static final Block CONDENSED_DIRT = register(
			"condensed_dirt",
			Block::new,
			AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRASS),
			true
	);

	// :::2
	// :::3
	public static final Block CONDENSED_OAK_LOG = register(
			"condensed_oak_log",
			PillarBlock::new,
			AbstractBlock.Settings.create().sounds(BlockSoundGroup.WOOD),
			true
	);

	// :::3
	// :::4
	public static final Block PRISMARINE_LAMP = register(
			"prismarine_lamp",
			PrismarineLampBlock::new,
			AbstractBlock.Settings.create()
					.sounds(BlockSoundGroup.LANTERN)
					.luminance(PrismarineLampBlock::getLuminance),
			true
	);
	// :::4
	public static final RegistryKey<Block> ENGINE_BLOCK_KEY = RegistryKey.of(
			RegistryKeys.BLOCK,
			Identifier.of(FabricDocsReference.MOD_ID, "engine")
	);
	public static final Block ENGINE_BLOCK = register(
			"engine",
			EngineBlock::new,
			AbstractBlock.Settings.create().registryKey(ENGINE_BLOCK_KEY),
			true
	);

	// :::5
	public static final Block COUNTER_BLOCK = register(
			"counter_block",
			CounterBlock::new,
			AbstractBlock.Settings.create(),
			true
	);
	// :::5

	public static final Block STEEL_BLOCK = register(
			"steel_block", PillarBlock::new, AbstractBlock.Settings.create(), true
	);
	public static final Block PIPE_BLOCK = register(
			"pipe_block", Block::new, AbstractBlock.Settings.create(), true
	);

	public static final Block RUBY_BLOCK = register(
			"ruby_block", Block::new, AbstractBlock.Settings.create(), true
	);
	public static final Block RUBY_STAIRS = register(
			"ruby_stairs", settings -> new StairsBlock(RUBY_BLOCK.getDefaultState(), settings), AbstractBlock.Settings.create(), true
	);
	public static final Block RUBY_SLAB = register(
			"ruby_slab", SlabBlock::new, AbstractBlock.Settings.create(), true
	);
	public static final Block RUBY_FENCE = register(
			"ruby_fence", FenceBlock::new, AbstractBlock.Settings.create(), true
	);

	public static final Block RUBY_DOOR = register(
			"ruby_door", settings -> new DoorBlock(BlockSetType.STONE, settings), AbstractBlock.Settings.create(), true
	);
	public static final Block RUBY_TRAPDOOR = register(
			"ruby_trapdoor", settings -> new TrapdoorBlock(BlockSetType.STONE, settings), AbstractBlock.Settings.create(), true
	);

	public static final Block VERTICAL_OAK_LOG_SLAB = register(
			"vertical_oak_log_slab", VerticalSlabBlock::new, AbstractBlock.Settings.create(), true
	);

	// :::datagen-model:family-declaration
	public static final BlockFamily RUBY_FAMILY =
			new BlockFamily.Builder(ModBlocks.RUBY_BLOCK)
			.stairs(ModBlocks.RUBY_STAIRS)
			.slab(ModBlocks.RUBY_SLAB)
			.fence(ModBlocks.RUBY_FENCE)
			.build();
	// :::datagen-model:family-declaration

	// :::1
	private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
		// Create a registry key for the block
		RegistryKey<Block> blockKey = keyOfBlock(name);
		// Create the block instance
		Block block = blockFactory.apply(settings.registryKey(blockKey));

		// Sometimes, you may not want to register an item for the block.
		// Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
		if (shouldRegisterItem) {
			// Items need to be registered with a different type of registry key, but the ID
			// can be the same.
			RegistryKey<Item> itemKey = keyOfItem(name);

			BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
			Registry.register(Registries.ITEM, itemKey, blockItem);
		}

		return Registry.register(Registries.BLOCK, blockKey, block);
	}

	private static RegistryKey<Block> keyOfBlock(String name) {
		return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(FabricDocsReference.MOD_ID, name));
	}

	private static RegistryKey<Item> keyOfItem(String name) {
		return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FabricDocsReference.MOD_ID, name));
	}

	// :::1

	public static void initialize() {
		setupItemGroups();
	}

	public static void setupItemGroups() {
		// :::6
		ItemGroupEvents.modifyEntriesEvent(ModItems.CUSTOM_ITEM_GROUP_KEY).register((itemGroup) -> {
			itemGroup.add(ModBlocks.CONDENSED_DIRT.asItem());
		});
		// :::6

		ItemGroupEvents.modifyEntriesEvent(ModItems.CUSTOM_ITEM_GROUP_KEY).register((itemGroup) -> {
			itemGroup.add(ModBlocks.CONDENSED_OAK_LOG.asItem());
			itemGroup.add(ModBlocks.PRISMARINE_LAMP.asItem());
			itemGroup.add(ModBlocks.COUNTER_BLOCK.asItem());
			itemGroup.add(ModBlocks.ENGINE_BLOCK.asItem());
			itemGroup.add(RUBY_BLOCK);
			itemGroup.add(RUBY_STAIRS);
			itemGroup.add(RUBY_SLAB);
			itemGroup.add(RUBY_FENCE);
			itemGroup.add(RUBY_DOOR);
			itemGroup.add(RUBY_TRAPDOOR);
			itemGroup.add(VERTICAL_OAK_LOG_SLAB);
		});
	}

	// :::1
}
// :::1
