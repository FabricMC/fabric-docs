package com.example.docs.sound;

import org.slf4j.Logger;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

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
		Registry.register(Registries.SOUND_EVENT, new Identifier(MOD_ID, "metal_whistle"),
				SoundEvent.of(new Identifier(MOD_ID, "metal_whistle")));

		// ... the cleaner approach. // [!code focus]
		// CustomSounds.initialize(); // [!code focus]
	}
}
// :::2
