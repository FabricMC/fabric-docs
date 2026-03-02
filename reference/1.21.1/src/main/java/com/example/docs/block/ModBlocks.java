package com.example.docs.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import com.example.docs.ExampleMod;
import com.example.docs.block.custom.CounterBlock;
import com.example.docs.block.custom.EngineBlock;
import com.example.docs.block.custom.PrismarineLampBlock;
import com.example.docs.item.ModItems;

// :::1
public class ModBlocks {
	// :::1

	// :::2
	public static final Block CONDENSED_DIRT = register(
			new Block(BlockBehaviour.Properties.of().sound(SoundType.GRASS)),
			"condensed_dirt",
			true
	);
	// :::2
	// :::3
	public static final Block CONDENSED_OAK_LOG = register(
			new RotatedPillarBlock(
					BlockBehaviour.Properties.of()
							.sound(SoundType.WOOD)
			), "condensed_oak_log", true
	);
	// :::3
	// :::4
	public static final Block PRISMARINE_LAMP = register(
			new PrismarineLampBlock(
					BlockBehaviour.Properties.of()
							.sound(SoundType.LANTERN)
							.lightLevel(PrismarineLampBlock::getLuminance)
			), "prismarine_lamp", true
	);
	// :::4
	public static final Block ENGINE_BLOCK = register(
			new EngineBlock(BlockBehaviour.Properties.of()), "engine", true
	);

	// :::5
	public static final Block COUNTER_BLOCK = register(
			new CounterBlock(BlockBehaviour.Properties.of()), "counter_block", true
	);
	// :::5

	// :::1
	public static Block register(Block block, String name, boolean shouldRegisterItem) {
		// Register the block and its item.
		ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, name);

		// Sometimes, you may not want to register an item for the block.
		// Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
		if (shouldRegisterItem) {
			BlockItem blockItem = new BlockItem(block, new Item.Properties());
			Registry.register(BuiltInRegistries.ITEM, id, blockItem);
		}

		return Registry.register(BuiltInRegistries.BLOCK, id, block);
	}

	// :::1
	public static void initialize() {
		// :::3
		ItemGroupEvents.modifyEntriesEvent(ModItems.CUSTOM_ITEM_GROUP_KEY).register((itemGroup) -> {
			itemGroup.accept(ModBlocks.CONDENSED_DIRT.asItem());
		});
		// :::3

		ItemGroupEvents.modifyEntriesEvent(ModItems.CUSTOM_ITEM_GROUP_KEY).register((itemGroup) -> {
			itemGroup.accept(ModBlocks.CONDENSED_OAK_LOG.asItem());
			itemGroup.accept(ModBlocks.PRISMARINE_LAMP.asItem());
			itemGroup.accept(ModBlocks.COUNTER_BLOCK.asItem());
		});
	}

	// :::1
}
// :::1
