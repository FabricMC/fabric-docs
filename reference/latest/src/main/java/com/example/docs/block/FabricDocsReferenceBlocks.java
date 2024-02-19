package com.example.docs.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;

public class FabricDocsReferenceBlocks implements ModInitializer {
	// :::1
	public static final Block BORDER_BLOCK = new Block(AbstractBlock.Settings.create().strength(1.5f));
	// :::1
	// :::4
	public static final Block MACHINE_PROTOTYPE_BLOCK = new MachinePrototypeBlock(AbstractBlock.Settings.create().strength(1.5f).requiresTool());
	// :::4

	@Override
	public void onInitialize() {
		// :::2
		Registry.register(Registries.BLOCK, new Identifier("fabric-docs-reference", "border"), BORDER_BLOCK);
		// :::2
		// :::3
		Registry.register(Registries.ITEM, new Identifier("fabric-docs-reference", "border"), new BlockItem(BORDER_BLOCK, new Item.Settings()));
		// :::3

		Registry.register(Registries.BLOCK, new Identifier("fabric-docs-reference", "machine_prototype"), MACHINE_PROTOTYPE_BLOCK);
		Registry.register(Registries.ITEM, new Identifier("fabric-docs-reference", "machine_prototype"), new BlockItem(MACHINE_PROTOTYPE_BLOCK, new Item.Settings()));

	}
}
