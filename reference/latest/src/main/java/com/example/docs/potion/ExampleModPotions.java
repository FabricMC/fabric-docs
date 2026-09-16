package com.example.docs.potion;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

import net.fabricmc.api.ModInitializer;

import com.example.docs.effect.ExampleModEffects;

public class ExampleModPotions implements ModInitializer {
	// #region register_potion
	public static final Holder<Potion> TATER_POTION =
			Registry.registerForHolder(
					BuiltInRegistries.POTION,
					ModPotionIds.TATER_POTION,
					new Potion("tater",
							new MobEffectInstance(
									ExampleModEffects.TATER,
									3600,
									0
							)
					)
			);
	// #endregion register_potion

	@Override
	public void onInitialize() {
	}
}
