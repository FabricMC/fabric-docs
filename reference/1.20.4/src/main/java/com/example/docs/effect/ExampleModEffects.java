package com.example.docs.effect;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

// :::1
public class ExampleModEffects implements ModInitializer {
	public static final MobEffect TATER_EFFECT = new TaterEffect();

	@Override
	public void onInitialize() {
		Registry.register(BuiltInRegistries.MOB_EFFECT, new ResourceLocation("example-mod", "tater"), TATER_EFFECT);
	}
}
// :::1
