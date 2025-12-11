package com.example.docs.worldgen;

import java.util.List;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

import com.example.docs.ExampleMod;

public class ExampleModWorldPlacedFeatures {
	// :::datagen-world:placed-key
	public static final ResourceKey<PlacedFeature> DIAMOND_BLOCK_ORE_PLACED_KEY =
			ResourceKey.create(
				Registries.PLACED_FEATURE,
				ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "diamond_block_ore_placed")
			);
	// :::datagen-world:placed-key

	public static final ResourceKey<PlacedFeature> DIAMOND_TREE_PLACED_KEY =
			ResourceKey.create(
				Registries.PLACED_FEATURE,
				ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "diamond_tree_placed")
			);

	public static void configure(BootstrapContext<PlacedFeature> context) {
		// :::datagen-world:conf-feature-register
		HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
		// :::datagen-world:conf-feature-register

		// :::datagen-world:placement-modifiers
		List<PlacementModifier> diamondBlockVeinModifiers = List.of(
				CountPlacement.of(6),
					BiomeFilter.biome(),
					InSquarePlacement.spread(),
				HeightRangePlacement.of(BiasedToBottomHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(0), 3))
		);
		// :::datagen-world:placement-modifiers

		List<PlacementModifier> diamondTreeModifiers = List.of(
				RarityFilter.onAverageOnceEvery(10), // spawns once every 10 chunks on average
				BiomeFilter.biome(),
				InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE

		);

		// :::datagen-world:register-placed-feature
		context.register(
				DIAMOND_BLOCK_ORE_PLACED_KEY,
				new PlacedFeature(
					configuredFeatures.getOrThrow(ExampleModWorldConfiguredFeatures.DIAMOND_BLOCK_VEIN_CONFIGURED_KEY),
					diamondBlockVeinModifiers
				)
		);
		// :::datagen-world:register-placed-feature

		context.register(
				DIAMOND_TREE_PLACED_KEY,
				new PlacedFeature(
					configuredFeatures.getOrThrow(ExampleModWorldConfiguredFeatures.DIAMOND_TREE_CONFIGURED_KEY),
					diamondTreeModifiers
				)
		);
	}
}
