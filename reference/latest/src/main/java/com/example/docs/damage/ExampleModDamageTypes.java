package com.example.docs.damage;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;

import com.example.docs.ExampleMod;

public class ExampleModDamageTypes implements ModInitializer {
	public static final RegistryKey<Block> TATER_BLOCK_KEY = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(ExampleMod.MOD_ID, "tater"));
	public static final Block TATER_BLOCK = new TaterBlock(AbstractBlock.Settings.create().registryKey(TATER_BLOCK_KEY));
	// :::1
	public static final RegistryKey<DamageType> TATER_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(ExampleMod.MOD_ID, "tater"));
	// :::1

	@Override
	public void onInitialize() {
		Registry.register(Registries.BLOCK, Identifier.of(ExampleMod.MOD_ID, "tater"), TATER_BLOCK);
		Registry.register(Registries.ITEM, Identifier.of(ExampleMod.MOD_ID, "tater"), new BlockItem(TATER_BLOCK, new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, TATER_BLOCK_KEY.getValue()))));
	}
}
