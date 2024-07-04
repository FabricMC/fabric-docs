package com.example.docs.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

// :::1
public class CustomSounds {
	private CustomSounds() {
		// private empty constructor to avoid accidental instantiation
	}

	// ITEM_METAL_WHISTLE is the name of the custom sound event
	// and is called in the mod to use the custom sound
	public static final SoundEvent ITEM_METAL_WHISTLE = registerSound("metal_whistle");

	// actual registration of all the custom SoundEvents
	private static SoundEvent registerSound(String id) {
		Identifier identifier = Identifier.of(FabricDocsReferenceSounds.MOD_ID, id);
		return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
	}

	// This static method starts class initialization, which then initializes
	// the static class variables (e.g. ITEM_METAL_WHISTLE).
	public static void initialize() {
		FabricDocsReferenceSounds.LOGGER.info("Registering " + FabricDocsReferenceSounds.MOD_ID + " Sounds");
		// Technically this method can stay empty, but some developers like to notify
		// the console, that certain parts of the mod have been successfully initialized
	}
}
// :::1
