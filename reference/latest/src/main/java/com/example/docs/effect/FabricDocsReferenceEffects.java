package com.example.docs.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;

// :::1
public class FabricDocsReferenceEffects implements ModInitializer {
	public static final StatusEffect TATER_EFFECT = new TaterEffect();

	@Override
	public void onInitialize() {
		Registry.register(Registries.STATUS_EFFECT, new Identifier("fabric-docs-reference", "tater"), TATER_EFFECT);
	}
}
// :::1
