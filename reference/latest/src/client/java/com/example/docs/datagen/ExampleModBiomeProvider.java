package com.example.docs.datagen;

import com.example.docs.ExampleMod;

import com.example.docs.worldgen.ExampleModWorldPlacedFeatures;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.concurrent.CompletableFuture;

// :::datagen-biome:provider-init
public class ExampleModBiomeProvider extends FabricDynamicRegistryProvider {
	public ExampleModBiomeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(net.minecraft.core.HolderLookup.Provider registries, Entries entries) {
		entries.addAll(registries.lookupOrThrow(Registries.BIOME));
	}

	@Override
	public String getName() {
		return "ExampeMod Biome Provider";
	}
	// :::datagen-biome:provider-init

	// :::datagen-biome:bootstrap
	public static void bootstrap(BootstrapContext<Biome> context) {
		HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);
		HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);
		// :::datagen-biome:bootstrap

		// :::datagen-biome:features
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder(features, carvers);

		// Ore Spawning
		generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
						features.getOrThrow(OrePlacements.ORE_DIAMOND));

		// Tree Spawning
		generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
						features.getOrThrow(ExampleModWorldPlacedFeatures.DIAMOND_TREE_PLACED_KEY));
		// :::datagen-biome:features

		// :::datagen-biome:mob-spawning
		// Mob Spawning
		MobSpawnSettings.Builder spawns = new MobSpawnSettings.Builder();
		spawns.creatureGenerationProbability(0.1f);
		spawns.addSpawn(
						MobCategory.CREATURE,
						20, // spawn weight; higher is more common, lower is rarer
						new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 10)
		);
    // :::datagen-biome:mob-spawning

		// :::datagen-biome:special-effects
		// Special Effects
		BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder()
						.waterColor(0x3F76E4)
						.grassColorOverride(123456)
						.foliageColorOverride(0x52BE55);
		// :::datagen-biome:special-effects

		// :::datagen-biome:biome-obj
		// Creating the biome object
		Biome customBiome = new Biome.BiomeBuilder()
						.hasPrecipitation(true)
						.temperature(0.7f)
						.downfall(0.8f)
						.specialEffects(effects.build())
						.mobSpawnSettings(spawns.build())
						.generationSettings(generation.build())
						.build();
		// :::datagen-biome:biome-obj

		// :::datagen-biome:register
		// Register the Biome
		ResourceKey<Biome> taterBiomeResourceKey = ResourceKey.create(
						Registries.BIOME,
						Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "tater_biome")
		);
		context.register(taterBiomeResourceKey, customBiome);
		// :::datagen-biome:register
		// :::datagen-biome:bootstrap
	}
	// :::datagen-biome:bootstrap
	// :::datagen-biome:provider-init
}
// :::datagen-biome:provider-init
