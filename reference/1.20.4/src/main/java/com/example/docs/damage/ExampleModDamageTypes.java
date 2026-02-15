package com.example.docs.damage;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ExampleModDamageTypes implements ModInitializer {
	public static final Block TATER_BLOCK = new TaterBlock(BlockBehaviour.Properties.of());
	// :::1
	public static final ResourceKey<DamageType> TATER_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("example-mod", "tater"));
	// :::1

	@Override
	public void onInitialize() {
		Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation("example-mod", "tater"), TATER_BLOCK);
		Registry.register(BuiltInRegistries.ITEM, new ResourceLocation("example-mod", "tater"), new BlockItem(TATER_BLOCK, new Item.Properties()));
	}
}
