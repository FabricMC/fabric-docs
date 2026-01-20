package com.example.docs.potion;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;

import com.example.docs.ExampleMod;
import com.example.docs.effect.ExampleModEffects;

// :::1
public class ExampleModPotions implements ModInitializer {
	public static final Holder<Potion> TATER_POTION =
			Registry.registerForHolder(
					BuiltInRegistries.POTION,
					Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "tater"),
					new Potion("tater",
							new MobEffectInstance(
									ExampleModEffects.TATER,
									3600,
									0)));

	@Override
	public void onInitialize() {
		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.addMix(
					// Input potion.
					Potions.WATER,
					// Ingredient
					Items.POTATO,
					// Output potion.
					TATER_POTION
			);
		});
	}
}
// :::1
