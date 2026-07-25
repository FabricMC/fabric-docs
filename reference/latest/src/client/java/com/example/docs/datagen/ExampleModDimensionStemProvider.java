package com.example.docs.datagen;

import com.example.docs.ExampleMod;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.concurrent.CompletableFuture;

public class ExampleModDimensionStemProvider extends FabricDynamicRegistryProvider {

	public static final ResourceKey<LevelStem> EXAMPLE_DIMENSION_STEM_KEY = ResourceKey.create(
					Registries.LEVEL_STEM,
					Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "example_dimension")
	);

	public static final ResourceKey<Biome> TATER_BIOME_KEY = ResourceKey.create(
					Registries.BIOME,
					Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "tater_biome")
	);

	public ExampleModDimensionStemProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.Provider registries, Entries entries) {
		entries.addAll(registries.lookupOrThrow(Registries.LEVEL_STEM));
		entries.addAll(registries.lookupOrThrow(Registries.DIMENSION_TYPE));
		entries.addAll(registries.lookupOrThrow(Registries.BIOME));
		entries.addAll(registries.lookupOrThrow(Registries.NOISE_SETTINGS));
	}

	public static void bootstrap(BootstrapContext<LevelStem> context) {
		var dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
		var biomes = context.lookup(Registries.BIOME);
		var noiseSettings = context.lookup(Registries.NOISE_SETTINGS);

		LevelStem exampleDimensionStem = new LevelStem(
						dimensionTypes.getOrThrow(ExampleModDimensionTypeProvider.EXAMPLE_DIMENSION_TYPE_KEY),
						new NoiseBasedChunkGenerator(
										new FixedBiomeSource(biomes.getOrThrow(TATER_BIOME_KEY)),
										noiseSettings.getOrThrow(NoiseGeneratorSettings.OVERWORLD)
						)
		);

		context.register(EXAMPLE_DIMENSION_STEM_KEY, exampleDimensionStem);
	}

	@Override
	public String getName() {
		return "ExampleMod Dimension Stem Provider";
	}
}
