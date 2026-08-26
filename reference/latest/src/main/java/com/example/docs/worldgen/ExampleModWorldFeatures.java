package com.example.docs.worldgen;

import java.util.List;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BlockStateProviders;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.BlockReplacement;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import com.example.docs.ExampleMod;

// #region datagen_world_configure_features_class
public class ExampleModWorldFeatures {
	// #endregion datagen_world_configure_features_class
	// #region datagen_world_configured_key
	public static final ResourceKey<Feature> DIAMOND_BLOCK_VEIN_CONFIGURED_KEY =
			ResourceKey.create(
				Registries.FEATURE,
				ExampleMod.id("diamond_block_vein")
			);
	// #endregion datagen_world_configured_key

	public static final ResourceKey<Feature> DIAMOND_TREE_CONFIGURED_KEY =
			ResourceKey.create(
					Registries.FEATURE,
					ExampleMod.id("diamond_tree")
			);

	// #region datagen_world_configure_features_class
	public static void configure(BootstrapContext<Feature> context) {
		// #endregion datagen_world_configure_features_class
		RuleTest stoneReplaceableRule = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);

		// #region datagen_world_ruletest
		RuleTest deepslateReplaceableRule = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
		// #endregion datagen_world_ruletest

		// #region datagen_world_ore_feature_config
		List<BlockReplacement> diamondBlockOreConfig =
				List.of(
						BlockReplacement.replace(deepslateReplaceableRule, Blocks.DIAMOND_BLOCK.defaultBlockState())
				);
		// #endregion datagen_world_ore_feature_config

		// #region datagen_world_multi_ore_feature_config
		List<BlockReplacement> ironAndDiamondBlockOreConfig =
				List.of(
						BlockReplacement.replace(deepslateReplaceableRule, Blocks.DIAMOND_BLOCK.defaultBlockState()),
						BlockReplacement.replace(stoneReplaceableRule, Blocks.IRON_BLOCK.defaultBlockState())
				);
		// #endregion datagen_world_multi_ore_feature_config

		// #region datagen_world_conf_feature_register
		context.register(
				DIAMOND_BLOCK_VEIN_CONFIGURED_KEY,
				new OreFeature(diamondBlockOreConfig, 10) // 10 is the blocks per vein
		);
		// #endregion datagen_world_conf_feature_register

		// Trees below
		// #region datagen_world_tree_feature_config
		TreeFeature diamondTree = new TreeFeature.Builder(
				// Trunk / Logs
				BlockStateProvider.of(Blocks.DIAMOND_BLOCK),
				new StraightTrunkPlacer(4, 2, 0),
				// Leaves
				BlockStateProvider.of(Blocks.GOLD_BLOCK),
				new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),

				new TwoLayersFeatureSize(0, 0, 0),
				// Block underneath the tree
				context.lookup(Registries.BLOCK_STATE_PROVIDER).getOrThrow(BlockStateProviders.SOIL_BENEATH_TREE)
				).build();
		// #endregion datagen_world_tree_feature_config

		// #region datagen_world_tree_register
		context.register(DIAMOND_TREE_CONFIGURED_KEY, diamondTree);
		// #endregion datagen_world_tree_register
		// #region datagen_world_configure_features_class
	}
}
// #endregion datagen_world_configure_features_class
