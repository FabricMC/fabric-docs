package com.example.docs.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;

// :::1
public class FabricDocsReferenceEffects implements ModInitializer {
	public static final StatusEffect TATER_EFFECT;

	static {
		TATER_EFFECT = Registry.register(Registries.STATUS_EFFECT, Identifier.of("fabric-docs-reference", "tater"), new TaterEffect());
	}

	@Override
	public void onInitialize() {
		// ...
	}
}
// :::1
