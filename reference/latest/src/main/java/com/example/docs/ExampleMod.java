package com.example.docs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;

import com.example.docs.component.ModComponents;
import com.example.docs.worldgen.ExampleModWorldPlacedFeatures;

// #region entrypoint
public class ExampleMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "example-mod";
	// #region create_logger
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	// #endregion create_logger

	// #endregion entrypoint
	// #region particle_register_main
	// This DefaultParticleType gets called when you want to use your particle in code.
	public static final SimpleParticleType SPARKLE_PARTICLE = FabricParticleTypes.simple();

	// #endregion particle_register_main
	// #region entrypoint
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		// #endregion entrypoint

		// #region particle_register_main
		// Register our custom particle type in the mod initializer.
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "sparkle_particle"), SPARKLE_PARTICLE);
		// #endregion particle_register_main
		// #region datagen_world_biome_modifications
		// Spawns everywhere in the overworld
		BiomeModifications.addFeature(
				BiomeSelectors.foundInOverworld(),
				GenerationStep.Decoration.UNDERGROUND_ORES,
				ExampleModWorldPlacedFeatures.DIAMOND_BLOCK_ORE_PLACED_KEY
		);
		// #endregion datagen_world_biome_modifications

		// #region datagen_world_selective_biome_modifications
		// Spawns in forest biomes only
		BiomeModifications.addFeature(
				BiomeSelectors.tag(BiomeTags.IS_FOREST),
				GenerationStep.Decoration.VEGETAL_DECORATION,
				ExampleModWorldPlacedFeatures.DIAMOND_TREE_PLACED_KEY
		);
		// #endregion datagen_world_selective_biome_modifications

		// #region tooltip_provider
		ItemComponentTooltipProviderRegistry.addAfter(DataComponents.DAMAGE, ModComponents.COMPONENT_WITH_TOOLTIP);
		// #endregion tooltip_provider
		// #region advanced_tooltip_provider
		ItemComponentTooltipProviderRegistry.addAfter(DataComponents.DAMAGE, ModComponents.ADVANCED_CUSTOM_COMPONENT);
		// #endregion advanced_tooltip_provider
		// #region entrypoint
	}
}
// #endregion entrypoint
