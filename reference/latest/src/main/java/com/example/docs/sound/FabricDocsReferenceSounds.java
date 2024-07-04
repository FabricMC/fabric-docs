package com.example.docs.sound;

import org.slf4j.Logger;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

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
		Registry.register(Registries.SOUND_EVENT, Identifier.of(MOD_ID, "metal_whistle"),
				SoundEvent.of(Identifier.of(MOD_ID, "metal_whistle")));

		// ... the cleaner approach. // [!code focus]
		// CustomSounds.initialize(); // [!code focus]
	}
}
// :::2
