package com.example.docs.worldgen;

import java.util.List;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import com.example.docs.ExampleMod;

public class ExampleModWorldConfiguredFeatures {
	// :::datagen-world:configured-key
	public static final ResourceKey<ConfiguredFeature<?, ?>> DIAMOND_BLOCK_VEIN_CONFIGURED_KEY =
				ResourceKey.create(
									Registries.CONFIGURED_FEATURE,
					ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "diamond_block_vein")
				);
	// :::datagen-world:configured-key

	public static final ResourceKey<ConfiguredFeature<?, ?>> DIAMOND_TREE_CONFIGURED_KEY =
				ResourceKey.create(
								Registries.CONFIGURED_FEATURE,
								ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "diamond_tree")
				);

	public static void configure(BootstrapContext<ConfiguredFeature<?, ?>> context) {
		RuleTest stoneReplaceableRule = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);

		// :::datagen-world:ruletest
		RuleTest deepslateReplaceableRule = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
		// :::datagen-world:ruletest

		// :::datagen-world:ore-feature-config
		List<OreConfiguration.TargetBlockState> diamondBlockOreConfig =
				List.of(
											OreConfiguration.target(deepslateReplaceableRule, Blocks.DIAMOND_BLOCK.defaultBlockState())
				);
		// :::datagen-world:ore-feature-config

		// :::datagen-world:multi-ore-feature-config
		List<OreConfiguration.TargetBlockState> ironAndDiamondBlockOreConfig =
				List.of(
									OreConfiguration.target(deepslateReplaceableRule, Blocks.DIAMOND_BLOCK.defaultBlockState()),
									OreConfiguration.target(stoneReplaceableRule, Blocks.IRON_BLOCK.defaultBlockState())
				);
		// :::datagen-world:multi-ore-feature-config

		// :::datagen-world:conf-feature-register
		context.register(
				DIAMOND_BLOCK_VEIN_CONFIGURED_KEY,
				new ConfiguredFeature<>(
					Feature.ORE,
					new OreConfiguration(diamondBlockOreConfig, 10)) // 10 is the blocks per vein
		);
		// :::datagen-world:conf-feature-register

		// Trees below
		// :::datagen-world:tree-feature-config
		TreeConfiguration diamondTree = new TreeConfiguration.TreeConfigurationBuilder(
					// Trunk / Logs
					BlockStateProvider.simple(Blocks.DIAMOND_BLOCK),
					new StraightTrunkPlacer(4, 2, 0),
					// Leaves
					BlockStateProvider.simple(Blocks.GOLD_BLOCK),
					new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),

					new TwoLayersFeatureSize(0, 0, 0)
		).build();
		// :::datagen-world:tree-feature-config

		// :::datagen-world:tree-register
		context.register(DIAMOND_TREE_CONFIGURED_KEY, new ConfiguredFeature<>(Feature.TREE, diamondTree));
		// :::datagen-world:tree-register
	}
}
