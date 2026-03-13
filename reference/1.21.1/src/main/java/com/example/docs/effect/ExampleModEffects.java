package com.example.docs.effect;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

// :::1
public class ExampleModEffects implements ModInitializer {
	public static final Holder<MobEffect> TATER;

	static {
		TATER = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath("example-mod", "tater"), new TaterEffect());
	}

	@Override
	public void onInitialize() {
		// ...
	}
}
// :::1
