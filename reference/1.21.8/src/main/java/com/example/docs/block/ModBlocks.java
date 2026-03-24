package com.example.docs.block;

import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;

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
			BlockBehaviour.Properties.of().sound(SoundType.GRASS),
			true
	);

	// :::2
	// :::3
	public static final Block CONDENSED_OAK_LOG = register(
			"condensed_oak_log",
			RotatedPillarBlock::new,
			BlockBehaviour.Properties.of().sound(SoundType.WOOD),
			true
	);

	// :::3
	// :::4
	public static final Block PRISMARINE_LAMP = register(
			"prismarine_lamp",
			PrismarineLampBlock::new,
			BlockBehaviour.Properties.of()
					.sound(SoundType.LANTERN)
					.lightLevel(PrismarineLampBlock::getLuminance),
			true
	);
	// :::4
	public static final ResourceKey<Block> ENGINE_BLOCK_KEY = ResourceKey.create(
			Registries.BLOCK,
			ResourceLocation.fromNamespaceAndPath(FabricDocsReference.MOD_ID, "engine")
	);
	public static final Block ENGINE_BLOCK = register(
			"engine",
			EngineBlock::new,
			BlockBehaviour.Properties.of().setId(ENGINE_BLOCK_KEY),
			true
	);

	// :::5
	public static final Block COUNTER_BLOCK = register(
			"counter_block",
			CounterBlock::new,
			BlockBehaviour.Properties.of(),
			true
	);
	// :::5

	public static final Block STEEL_BLOCK = register(
			"steel_block", RotatedPillarBlock::new, BlockBehaviour.Properties.of(), true
	);
	public static final Block PIPE_BLOCK = register(
			"pipe_block", Block::new, BlockBehaviour.Properties.of(), true
	);

	public static final Block RUBY_BLOCK = register(
			"ruby_block", Block::new, BlockBehaviour.Properties.of(), true
	);
	public static final Block RUBY_STAIRS = register(
			"ruby_stairs", settings -> new StairBlock(RUBY_BLOCK.defaultBlockState(), settings), BlockBehaviour.Properties.of(), true
	);
	public static final Block RUBY_SLAB = register(
			"ruby_slab", SlabBlock::new, BlockBehaviour.Properties.of(), true
	);
	public static final Block RUBY_FENCE = register(
			"ruby_fence", FenceBlock::new, BlockBehaviour.Properties.of(), true
	);

	public static final Block RUBY_DOOR = register(
			"ruby_door", settings -> new DoorBlock(BlockSetType.STONE, settings), BlockBehaviour.Properties.of(), true
	);
	public static final Block RUBY_TRAPDOOR = register(
			"ruby_trapdoor", settings -> new TrapDoorBlock(BlockSetType.STONE, settings), BlockBehaviour.Properties.of(), true
	);

	public static final Block VERTICAL_OAK_LOG_SLAB = register(
			"vertical_oak_log_slab", VerticalSlabBlock::new, BlockBehaviour.Properties.of(), true
	);

	// :::datagen-model:family-declaration
	public static final BlockFamily RUBY_FAMILY =
			new BlockFamily.Builder(ModBlocks.RUBY_BLOCK)
			.stairs(ModBlocks.RUBY_STAIRS)
			.slab(ModBlocks.RUBY_SLAB)
			.fence(ModBlocks.RUBY_FENCE)
			.getFamily();
	// :::datagen-model:family-declaration

	// :::1
	private static Block register(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties settings, boolean shouldRegisterItem) {
		// Create a registry key for the block
		ResourceKey<Block> blockKey = keyOfBlock(name);
		// Create the block instance
		Block block = blockFactory.apply(settings.setId(blockKey));

		// Sometimes, you may not want to register an item for the block.
		// Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
		if (shouldRegisterItem) {
			// Items need to be registered with a different type of registry key, but the ID
			// can be the same.
			ResourceKey<Item> itemKey = keyOfItem(name);

			BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
			Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);
		}

		return Registry.register(BuiltInRegistries.BLOCK, blockKey, block);
	}

	private static ResourceKey<Block> keyOfBlock(String name) {
		return ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(FabricDocsReference.MOD_ID, name));
	}

	private static ResourceKey<Item> keyOfItem(String name) {
		return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(FabricDocsReference.MOD_ID, name));
	}

	// :::1

	public static void initialize() {
		setupItemGroups();
	}

	public static void setupItemGroups() {
		// :::6
		ItemGroupEvents.modifyEntriesEvent(ModItems.CUSTOM_ITEM_GROUP_KEY).register((itemGroup) -> {
			itemGroup.accept(ModBlocks.CONDENSED_DIRT.asItem());
		});
		// :::6

		ItemGroupEvents.modifyEntriesEvent(ModItems.CUSTOM_ITEM_GROUP_KEY).register((itemGroup) -> {
			itemGroup.accept(ModBlocks.CONDENSED_OAK_LOG.asItem());
			itemGroup.accept(ModBlocks.PRISMARINE_LAMP.asItem());
			itemGroup.accept(ModBlocks.COUNTER_BLOCK.asItem());
			itemGroup.accept(ModBlocks.ENGINE_BLOCK.asItem());
			itemGroup.accept(RUBY_BLOCK);
			itemGroup.accept(RUBY_STAIRS);
			itemGroup.accept(RUBY_SLAB);
			itemGroup.accept(RUBY_FENCE);
			itemGroup.accept(RUBY_DOOR);
			itemGroup.accept(RUBY_TRAPDOOR);
			itemGroup.accept(VERTICAL_OAK_LOG_SLAB);
		});
	}

	// :::1
}
// :::1
