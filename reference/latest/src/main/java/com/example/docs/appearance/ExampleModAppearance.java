package com.example.docs.appearance;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.fabricmc.api.ModInitializer;

import com.example.docs.ExampleMod;

public class ExampleModAppearance implements ModInitializer {
	private static final ResourceKey<Item> WAXCAP_BLOCK_ITEM_KEY = ResourceKey.create(
					Registries.ITEM,
					ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "waxcap")
	);
	private static final ResourceKey<Block> WAXCAP_BLOCK_KEY = ResourceKey.create(
					Registries.BLOCK,
					ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "waxcap")
	);
	// :::block
	public static final Block WAXCAP_BLOCK = Registry.register(
					BuiltInRegistries.BLOCK,
					ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "waxcap"),
					new Block(BlockBehaviour.Properties.of()
									.noCollision()
									.instabreak()
									.offsetType(BlockBehaviour.OffsetType.XYZ)
									.setId(WAXCAP_BLOCK_KEY)
					));
	// :::block
	// :::item
	public static final Item WAXCAP_BLOCK_ITEM = Registry.register(
					BuiltInRegistries.ITEM,
					ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "waxcap"),
					new BlockItem(WAXCAP_BLOCK, new Item.Properties()
									.setId(WAXCAP_BLOCK_ITEM_KEY))
	);
	// :::item
	@Override
	public void onInitialize() {
	}
}
