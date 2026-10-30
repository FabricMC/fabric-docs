package com.example.docs.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;

import com.example.docs.ExampleMod;

//:::datagen-dimension:noiseSettingsProvider
public class ExampleModNoiseSettingsProvider extends FabricDynamicRegistryProvider {
	public static final ResourceKey<NoiseGeneratorSettings> CUSTOM_NOISE_SETTINGS_KEY = ResourceKey.create(
					Registries.NOISE_SETTINGS,
					Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "custom_noise")
	);

	public ExampleModNoiseSettingsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.Provider registries, Entries entries) {
		entries.addAll(registries.lookupOrThrow(Registries.NOISE_SETTINGS));
	}

	public static void bootstrap(BootstrapContext<NoiseGeneratorSettings> context) {
		//:::datagen-dimension:noiseSettingsProvider

		//:::datagen-dimension:noiseSettingsRouter
		var customNoiseRouter = new NoiseRouter(
						DensityFunctions.zero(), // barrierNoise
						DensityFunctions.zero(), // fluidLevelFloodednessNoise
						DensityFunctions.zero(), // fluidLevelSpreadNoise
						DensityFunctions.zero(), // lavaNoise
						DensityFunctions.constant(0.25), // temperature
						DensityFunctions.constant(0.0), // vegetation
						DensityFunctions.constant(0.5), // continents
						DensityFunctions.constant(0.2), // erosion
						DensityFunctions.constant(0.0), // depth
						DensityFunctions.constant(0.0), // ridges
						DensityFunctions.zero(), // initialDensityWithoutJaggedness
						DensityFunctions.yClampedGradient(0, 128, 1.0, -1.0), // finalDensity
						DensityFunctions.zero(), // veinToggle
						DensityFunctions.zero(), // veinRidged
						DensityFunctions.zero() // veinGap
		);
		//:::datagen-dimension:noiseSettingsRouter

		//:::datagen-dimension:noiseSettingsSurfaceRule
		SurfaceRules.RuleSource customSurfaceRule = SurfaceRules.sequence(
						SurfaceRules.ifTrue(
										SurfaceRules.isBiome(ExampleModDimensionStemProvider.TATER_BIOME_KEY),
										SurfaceRules.sequence(
														// Mountain Peaks (Y >= 110)
														SurfaceRules.ifTrue(
																		SurfaceRules.yStartCheck(VerticalAnchor.absolute(110), 1),
																		SurfaceRules.state(Blocks.CALCITE.defaultBlockState())
														),

														// Steep Cliffs
														SurfaceRules.ifTrue(
																		SurfaceRules.steep(),
																		SurfaceRules.state(Blocks.TUFF.defaultBlockState())
														),
														// Top Surface Layer (ON_FLOOR)
														SurfaceRules.ifTrue(
																		SurfaceRules.ON_FLOOR,
																		SurfaceRules.sequence(
																						SurfaceRules.ifTrue(
																										SurfaceRules.waterBlockCheck(-1, 0),
																										SurfaceRules.sequence(
																														// Podzol Patch
																														SurfaceRules.ifTrue(
																																		SurfaceRules.noiseCondition(Noises.SURFACE, 0.2, 1.0),
																																		SurfaceRules.state(Blocks.PODZOL.defaultBlockState())
																														),
																														// Moss Patch
																														SurfaceRules.ifTrue(
																																		SurfaceRules.noiseCondition(Noises.GRAVEL, 0.1, 1.0),
																																		SurfaceRules.state(Blocks.MOSS_BLOCK.defaultBlockState())
																														),
																														// Surface Grass
																														SurfaceRules.state(Blocks.GRASS_BLOCK.defaultBlockState())
																										)
																						),
																						// Underwater floor
																						SurfaceRules.state(Blocks.MUD.defaultBlockState())
																		)
														),

														// Sub-surface soil layer (UNDER_FLOOR)
														SurfaceRules.ifTrue(
																		SurfaceRules.UNDER_FLOOR,
																		SurfaceRules.state(Blocks.PACKED_MUD.defaultBlockState())
														)
										)
						),
						SurfaceRuleData.overworld()
		);
		//:::datagen-dimension:noiseSettingsSurfaceRule

		//:::datagen-dimension:customNoiseSettings
		NoiseGeneratorSettings customSettings = new NoiseGeneratorSettings(
						NoiseSettings.create(0, 256, 1, 2), // minY, height, noiseSizeHorizontal, noiseSizeVertical
						Blocks.STONE.defaultBlockState(), // defaultBlock
						Blocks.WATER.defaultBlockState(), // defaultFluid
						customNoiseRouter, // NoiseRouter
						customSurfaceRule, // SurfaceRule
						List.of(), // spawnTarget parameter points
						64, // seaLevel
						false, // disableMobGeneration
						true, // aquifersEnabled
						false, // oreVeinsEnabled
						false // useLegacyRandomSource
		);

		context.register(CUSTOM_NOISE_SETTINGS_KEY, customSettings);
		//:::datagen-dimension:customNoiseSettings

		//:::datagen-dimension:noiseSettingsProvider
	}

	@Override
	public String getName() {
		return "ExampleMod Noise Settings Provider";
	}
	//:::datagen-dimension:noiseSettingsProvider
}
