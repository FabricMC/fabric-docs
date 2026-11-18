package com.example.docs.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

import net.fabricmc.api.ModInitializer;

import com.example.docs.ExampleMod;

// #region register_effect
public class ExampleModEffects implements ModInitializer {
	public static final Holder<MobEffect> TATER =
			Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ExampleMod.id("tater"), new TaterEffect());

	@Override
	public void onInitialize() {
		// ...
	}
}
// #endregion register_effect
