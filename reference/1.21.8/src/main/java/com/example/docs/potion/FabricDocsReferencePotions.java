package com.example.docs.potion;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;

import com.example.docs.FabricDocsReference;
import com.example.docs.effect.FabricDocsReferenceEffects;

// :::1
public class FabricDocsReferencePotions implements ModInitializer {
	public static final Potion TATER_POTION =
			Registry.register(
					BuiltInRegistries.POTION,
					ResourceLocation.fromNamespaceAndPath(FabricDocsReference.MOD_ID, "tater"),
					new Potion("tater",
							new MobEffectInstance(
									FabricDocsReferenceEffects.TATER,
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
					BuiltInRegistries.POTION.wrapAsHolder(TATER_POTION)
			);
		});
	}
}
// :::1
