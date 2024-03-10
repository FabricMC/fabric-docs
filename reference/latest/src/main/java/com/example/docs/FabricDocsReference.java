package com.example.docs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;

public class FabricDocsReference implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "fabric-docs-reference";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	//#particle_register_main
	// This DefaultParticleType gets called when you want to use your particle in code.
	public static final DefaultParticleType SPARKLE_PARTICLE = FabricParticleTypes.simple();

	//#particle_register_main

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		//#particle_register_main
		// Register our custom particle type in the mod initializer.
		Registry.register(Registries.PARTICLE_TYPE, new Identifier(MOD_ID, "sparkle_particle"), SPARKLE_PARTICLE);
		//#particle_register_main
	}
}
