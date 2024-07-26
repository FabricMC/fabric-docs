package com.example.docs.potion;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;

import com.example.docs.effect.FabricDocsReferenceEffects;

// :::1
public class FabricDocsReferencePotions implements ModInitializer {
	public static final Potion TATER_POTION =
			Registry.register(
					Registries.POTION,
					Identifier.of("fabric-docs-reference", "tater"),
					new Potion(
							new StatusEffectInstance(
									Registries.STATUS_EFFECT.getEntry(FabricDocsReferenceEffects.TATER_EFFECT),
									3600,
									0)));

	@Override
	public void onInitialize() {
		FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
			builder.registerPotionRecipe(
					// Input potion.
					Potions.WATER,
					// Ingredient
					Items.POTATO,
					// Output potion.
					Registries.POTION.getEntry(TATER_POTION)
			);
		});
	}
}
// :::1
