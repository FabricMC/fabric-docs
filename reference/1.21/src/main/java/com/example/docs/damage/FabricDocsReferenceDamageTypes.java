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

public class FabricDocsReferenceDamageTypes implements ModInitializer {
	public static final Block TATER_BLOCK = new TaterBlock(AbstractBlock.Settings.create());
	// :::1
	public static final RegistryKey<DamageType> TATER_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of("fabric-docs-reference", "tater"));
	// :::1

	@Override
	public void onInitialize() {
		Registry.register(Registries.BLOCK, Identifier.of("fabric-docs-reference", "tater"), TATER_BLOCK);
		Registry.register(Registries.ITEM, Identifier.of("fabric-docs-reference", "tater"), new BlockItem(TATER_BLOCK, new Item.Settings()));
	}
}
