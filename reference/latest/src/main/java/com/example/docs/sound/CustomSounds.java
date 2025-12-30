package com.example.docs.sound;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

// :::1
public class CustomSounds {
	private CustomSounds() {
		// private empty constructor to avoid accidental instantiation
	}

	// ITEM_METAL_WHISTLE is the name of the custom sound event
	// and is called in the mod to use the custom sound
	public static final SoundEvent ITEM_METAL_WHISTLE = registerSound("metal_whistle");
	public static final SoundEvent ENGINE_LOOP = registerSound("engine");

	// actual registration of all the custom SoundEvents
	private static SoundEvent registerSound(String id) {
		Identifier identifier = Identifier.fromNamespaceAndPath(ExampleModSounds.MOD_ID, id);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, identifier, SoundEvent.createVariableRangeEvent(identifier));
	}

	// This static method starts class initialization, which then initializes
	// the static class variables (e.g. ITEM_METAL_WHISTLE).
	public static void initialize() {
		ExampleModSounds.LOGGER.info("Registering " + ExampleModSounds.MOD_ID + " Sounds");
		// Technically this method can stay empty, but some developers like to notify
		// the console, that certain parts of the mod have been successfully initialized
	}
}
// :::1
