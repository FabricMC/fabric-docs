package com.example.docs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.GenerationStep;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;

import com.example.docs.component.ModComponents;
import com.example.docs.worldgen.ExampleModWorldPlacedFeatures;

//#entrypoint
public class ExampleMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "example-mod";
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
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(MOD_ID, "sparkle_particle"), SPARKLE_PARTICLE);
		//#particle_register_main
		// :::datagen-world:biome-modifications
		// Spawns everywhere in the overworld
		BiomeModifications.addFeature(
				BiomeSelectors.foundInOverworld(),
				GenerationStep.Decoration.UNDERGROUND_ORES,
				ExampleModWorldPlacedFeatures.DIAMOND_BLOCK_ORE_PLACED_KEY
		);
		// :::datagen-world:biome-modifications

		// :::datagen-world:selective-biome-modifications
		// Spawns in forest biomes only
		BiomeModifications.addFeature(
				BiomeSelectors.tag(TagKey.create(Registries.BIOME, Identifier.withDefaultNamespace("is_forest"))),
				GenerationStep.Decoration.VEGETAL_DECORATION,
				ExampleModWorldPlacedFeatures.DIAMOND_TREE_PLACED_KEY
		);
		// :::datagen-world:selective-biome-modifications
		//#entrypoint

		// #tooltip_provider
		ComponentTooltipAppenderRegistry.addAfter(DataComponents.DAMAGE, ModComponents.COMPONENT_WITH_TOOLTIP);
		// #tooltip_provider
	}
}
