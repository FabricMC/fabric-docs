package com.example.docs.potion;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import com.example.docs.effect.ExampleModEffects;

// :::1
public class ExampleModPotions implements ModInitializer {
	public static final Potion TATER_POTION =
			Registry.register(
					BuiltInRegistries.POTION,
					new ResourceLocation("example-mod", "tater"),
					new Potion(
							new MobEffectInstance(
									ExampleModEffects.TATER_EFFECT,
									3600,
									0)));

	@Override
	public void onInitialize() {
		PotionBrewing.addMix(Potions.WATER, Items.POTATO, TATER_POTION);

		// Use the mixin invoker if you are not using Fabric API
		// BrewingRecipeRegistryInvoker.invokeRegisterPotionRecipe(Potions.WATER, Items.POTATO, TATER_POTION);
	}
}
// :::1
