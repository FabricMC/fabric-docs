package com.example.docs.potion;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;

import com.example.docs.effect.FabricDocsReferenceEffects;

// :::1
public class FabricDocsReferencePotions implements ModInitializer {
	public static final Potion TATER_POTION =
			Registry.register(
					Registries.POTION,
					new Identifier("fabric-docs-reference", "tater"),
					new Potion(
							new StatusEffectInstance(
									FabricDocsReferenceEffects.TATER_EFFECT,
									3600,
									0)));

	@Override
	public void onInitialize() {
		BrewingRecipeRegistry.registerPotionRecipe(Potions.WATER, Items.POTATO, TATER_POTION);

		// Use the mixin invoker if you are not using Fabric API
		// BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(Potions.WATER, Items.POTATO, TATER_POTION);
	}
}
// :::1
