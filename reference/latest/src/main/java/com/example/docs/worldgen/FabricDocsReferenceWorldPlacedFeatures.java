package com.example.docs.worldgen;

import java.util.List;

import com.example.docs.FabricDocsReference;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.heightprovider.BiasedToBottomHeightProvider;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class FabricDocsReferenceWorldPlacedFeatures {
    // :::datagen-world:placed-key
    public static final RegistryKey<PlacedFeature> DIAMOND_BLOCK_ORE_PLACED_KEY =
        RegistryKey.of(
            RegistryKeys.PLACED_FEATURE,
            Identifier.of(FabricDocsReference.MOD_ID, "diamond_block_ore_placed")
        );
    // :::datagen-world:placed-key

    public static final RegistryKey<PlacedFeature> DIAMOND_TREE_PLACED_KEY =
        RegistryKey.of(
            RegistryKeys.PLACED_FEATURE,
            Identifier.of(FabricDocsReference.MOD_ID, "diamond_tree_placed")
        );

    public static void configure(Registerable<PlacedFeature> context) {
        // :::datagen-world:conf-feature-register
        RegistryEntryLookup<ConfiguredFeature<?,?>> configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        // :::datagen-world:conf-feature-register

        // :::datagen-world:placement-modifiers
        List<PlacementModifier> diamondBlockVeinModifiers = List.of(
            CountPlacementModifier.of(6),
            BiomePlacementModifier.of(),
            SquarePlacementModifier.of(),
            HeightRangePlacementModifier.of(BiasedToBottomHeightProvider.create(YOffset.BOTTOM, YOffset.fixed(0),3))
        );
        // :::datagen-world:placement-modifiers

        List<PlacementModifier> diamondTreeModifiers = List.of(
            RarityFilterPlacementModifier.of(10), // spawns once every 10 chunks on average
            BiomePlacementModifier.of(),
            SquarePlacementModifier.of(),
            PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP

        );

        // :::datagen-world:register-placed-feature
        context.register(
            DIAMOND_BLOCK_ORE_PLACED_KEY,
            new PlacedFeature(
                configuredFeatures.getOrThrow(FabricDocsReferenceWorldConfiguredFeatures.DIAMOND_BLOCK_VEIN_CONFIGURED_KEY),
                diamondBlockVeinModifiers
            )
        );
        // :::datagen-world:register-placed-feature

        context.register(
            DIAMOND_TREE_PLACED_KEY,
            new PlacedFeature(
                configuredFeatures.getOrThrow(FabricDocsReferenceWorldConfiguredFeatures.DIAMOND_TREE_CONFIGURED_KEY),
                diamondTreeModifiers
            )
        );

    }
}
