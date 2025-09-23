package com.example.docs;

import com.example.docs.worldgen.FabricDocsReferenceWorldPlacedFeatures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;

//#entrypoint
public class FabricDocsReference implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "fabric-docs-reference";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	//#entrypoint
	//#particle_register_main
	// This DefaultParticleType gets called when you want to use your particle in code.
	public static final SimpleParticleType SPARKLE_PARTICLE = FabricParticleTypes.simple();

	//#particle_register_main
	//#entrypoint
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		//#entrypoint

		//#particle_register_main
		// Register our custom particle type in the mod initializer.
		Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "sparkle_particle"), SPARKLE_PARTICLE);
		//#particle_register_main

        // :::datagen-world:biome-modifications
		// Spawns everywhere in the overworld
		BiomeModifications.addFeature(
			BiomeSelectors.foundInOverworld(),
			GenerationStep.Feature.UNDERGROUND_ORES,
			FabricDocsReferenceWorldPlacedFeatures.DIAMOND_BLOCK_ORE_PLACED_KEY
		);
        // :::datagen-world:biome-modifications

		// :::datagen-world:selective-biome-modifications
		// Spawns in forest biomes only
		BiomeModifications.addFeature(
			BiomeSelectors.tag(TagKey.of(RegistryKeys.BIOME, Identifier.of("minecraft", "is_forest"))),
			GenerationStep.Feature.VEGETAL_DECORATION,
			FabricDocsReferenceWorldPlacedFeatures.DIAMOND_TREE_PLACED_KEY
		);
		// :::datagen-world:selective-biome-modifications

		//#entrypoint
	}
}
