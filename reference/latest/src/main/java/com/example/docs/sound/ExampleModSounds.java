package com.example.docs.sound;

import org.slf4j.Logger;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

import net.fabricmc.api.ModInitializer;

import com.example.docs.ExampleMod;

// :::2
public class ExampleModSounds implements ModInitializer {
	public static final String MOD_ID = ExampleMod.MOD_ID;
	public static final Logger LOGGER = ExampleMod.LOGGER;

	@Override
	public void onInitialize() {
		// This is the basic registering. Use a new class for registering sounds
		// instead, to keep the ModInitializer implementing class clean!
		Registry.register(BuiltInRegistries.SOUND_EVENT, Identifier.fromNamespaceAndPath(MOD_ID, "metal_whistle_simple"),
				SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(MOD_ID, "metal_whistle_simple")));

		// ... the cleaner approach. // [!code focus]
		CustomSounds.initialize(); // [!code focus]
	}

	public static Identifier identifierOf(String path) {
		return Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, path);
	}
}
// :::2
