package com.example.docs.damage;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.fabricmc.api.ModInitializer;

import com.example.docs.ExampleMod;

public class ExampleModDamageTypes implements ModInitializer {
	public static final ResourceKey<Block> TATER_BLOCK_KEY = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "tater"));
	public static final Block TATER_BLOCK = new TaterBlock(BlockBehaviour.Properties.of().setId(TATER_BLOCK_KEY));
	// :::1
	public static final ResourceKey<DamageType> TATER_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "tater"));
	// :::1

	@Override
	public void onInitialize() {
		Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "tater"), TATER_BLOCK);
		Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "tater"), new BlockItem(TATER_BLOCK, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, TATER_BLOCK_KEY.identifier()))));
	}
}
