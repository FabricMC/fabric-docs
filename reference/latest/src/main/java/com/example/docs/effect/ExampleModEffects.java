package com.example.docs.effect;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;

import net.fabricmc.api.ModInitializer;

import com.example.docs.ExampleMod;

// :::1
public class ExampleModEffects implements ModInitializer {
	public static final Holder<MobEffect> TATER =
			Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "tater"), new TaterEffect());

	@Override
	public void onInitialize() {
		// ...
	}
}
// :::1
