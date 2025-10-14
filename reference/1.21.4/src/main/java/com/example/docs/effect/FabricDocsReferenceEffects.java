package com.example.docs.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;

import com.example.docs.FabricDocsReference;

// :::1
public class FabricDocsReferenceEffects implements ModInitializer {
	public static final RegistryEntry<StatusEffect> TATER =
			Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(FabricDocsReference.MOD_ID, "tater"), new TaterEffect());

	@Override
	public void onInitialize() {
		// ...
	}
}
// :::1
