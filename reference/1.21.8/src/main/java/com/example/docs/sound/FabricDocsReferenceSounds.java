package com.example.docs.sound;

import org.slf4j.Logger;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import net.fabricmc.api.ModInitializer;

import com.example.docs.FabricDocsReference;

// :::2
public class FabricDocsReferenceSounds implements ModInitializer {
	public static final String MOD_ID = FabricDocsReference.MOD_ID;
	public static final Logger LOGGER = FabricDocsReference.LOGGER;

	@Override
	public void onInitialize() {
		// This is the basic registering. Use a new class for registering sounds
		// instead, to keep the ModInitializer implementing class clean!
		Registry.register(BuiltInRegistries.SOUND_EVENT, ResourceLocation.fromNamespaceAndPath(MOD_ID, "metal_whistle_simple"),
				SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MOD_ID, "metal_whistle_simple")));

		// ... the cleaner approach. // [!code focus]
		CustomSounds.initialize(); // [!code focus]
	}

	public static ResourceLocation identifierOf(String path) {
		return ResourceLocation.fromNamespaceAndPath(FabricDocsReference.MOD_ID, path);
	}
}
// :::2
