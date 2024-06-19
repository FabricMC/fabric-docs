package com.example.docs.block;

import com.example.docs.FabricDocsReference;

import com.example.docs.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

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

	// :::1
	public static Block register(Block block, String name, boolean shouldRegisterItem) {
		// Register the block and its item.
		Identifier id = new Identifier(FabricDocsReference.MOD_ID, name);

		// Sometimes, you may not want to register an item for the block.
		// Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
		if(shouldRegisterItem) {
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
	};
	// :::1
}
// :::1
