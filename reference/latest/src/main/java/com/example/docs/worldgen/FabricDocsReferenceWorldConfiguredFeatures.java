package com.example.docs.worldgen;

import java.util.List;

import com.example.docs.FabricDocsReference;

import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public class FabricDocsReferenceWorldConfiguredFeatures {
    // :::datagen-world:configured-key
    public static final RegistryKey<ConfiguredFeature<?, ?>> DIAMOND_BLOCK_VEIN_CONFIGURED_KEY =
    RegistryKey.of(
        RegistryKeys.CONFIGURED_FEATURE,
        Identifier.of(FabricDocsReference.MOD_ID, "diamond_block_vein")
    );
    // :::datagen-world:configured-key

    public static final RegistryKey<ConfiguredFeature<?, ?>> DIAMOND_TREE_CONFIGURED_KEY =
    RegistryKey.of(
        RegistryKeys.CONFIGURED_FEATURE,
        Identifier.of(FabricDocsReference.MOD_ID, "diamond_tree")
    );

    public static void configure(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceableRule = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);

        // :::datagen-world:ruletest
        RuleTest deepslateReplaceableRule = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        // :::datagen-world:ruletest

        // :::datagen-world:ore-feature-config
        List<OreFeatureConfig.Target> diamondBlockOreConfig =
            List.of(
                OreFeatureConfig.createTarget(deepslateReplaceableRule, Blocks.DIAMOND_BLOCK.getDefaultState())
            );
        // :::datagen-world:ore-feature-config

        // :::datagen-world:multi-ore-feature-config
        List<OreFeatureConfig.Target> ironAndDiamondBlockOreConfig =
            List.of(
                OreFeatureConfig.createTarget(deepslateReplaceableRule, Blocks.DIAMOND_BLOCK.getDefaultState()),
                OreFeatureConfig.createTarget(stoneReplaceableRule, Blocks.IRON_BLOCK.getDefaultState())
            );
        // :::datagen-world:multi-ore-feature-config

        // :::datagen-world:conf-feature-register
        context.register(
            DIAMOND_BLOCK_VEIN_CONFIGURED_KEY,
            new ConfiguredFeature<>(
                Feature.ORE,
                new OreFeatureConfig(diamondBlockOreConfig, 10)) // 10 is the blocks per vein
        );
        // :::datagen-world:conf-feature-register

        // Trees below
        // :::datagen-world:tree-feature-config
        TreeFeatureConfig diamondTree = new TreeFeatureConfig.Builder(
            // Trunk / Logs
            BlockStateProvider.of(Blocks.DIAMOND_BLOCK),
            new StraightTrunkPlacer(4, 2, 0),
            // Leaves
            BlockStateProvider.of(Blocks.GOLD_BLOCK),
            new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),

            new TwoLayersFeatureSize(0, 0, 0)
        ).build();
        // :::datagen-world:tree-feature-config

        // :::datagen-world:tree-register
        context.register(DIAMOND_TREE_CONFIGURED_KEY, new ConfiguredFeature<>(Feature.TREE, diamondTree));
        // :::datagen-world:tree-register
    }
}
