package com.example.docs.datagen;

import java.util.Optional;

import com.example.docs.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateSupplier;
import net.minecraft.client.data.BlockStateVariant;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Model;
import net.minecraft.client.data.ModelIds;
import net.minecraft.client.data.TextureKey;
import net.minecraft.client.data.TextureMap;
import net.minecraft.client.data.TexturedModel;
import net.minecraft.client.data.VariantSetting;
import net.minecraft.client.data.VariantSettings;
import net.minecraft.client.data.VariantsBlockStateSupplier;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import com.example.docs.FabricDocsReference;
import com.example.docs.block.ModBlocks;
import com.example.docs.block.custom.VerticalSlabBlock;

// :::datagen-model:provider
public class FabricDocsReferenceModelProvider extends FabricModelProvider {
	public FabricDocsReferenceModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		// :::datagen-model:provider

		// :::datagen-model:cube-all
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.STEEL_BLOCK);
		// :::datagen-model:cube-all

		// :::datagen-model:cube-top-for-ends
		blockStateModelGenerator.registerSingleton(ModBlocks.PIPE_BLOCK, TexturedModel.END_FOR_TOP_CUBE_COLUMN);
		// :::datagen-model:cube-top-for-ends

		// :::datagen-model:block-texture-pool-normal
		blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.RUBY_BLOCK)
				.stairs(ModBlocks.RUBY_STAIRS)
				.slab(ModBlocks.RUBY_SLAB)
				.fence(ModBlocks.RUBY_FENCE);
		// :::datagen-model:block-texture-pool-normal

		// :::datagen-model:door-and-trapdoor
		blockStateModelGenerator.registerDoor(ModBlocks.RUBY_DOOR);
		blockStateModelGenerator.registerTrapdoor(ModBlocks.RUBY_TRAPDOOR);
		// blockStateModelGenerator.registerOrientableTrapdoor(ModBlocks.RUBY_TRAPDOOR);
		// :::datagen-model:door-and-trapdoor

		// :::datagen-model-custom:method-call
		CustomBlockStateModelGenerator.registerVerticalSlab(
				blockStateModelGenerator,
				ModBlocks.VERTICAL_OAK_LOG_SLAB,
				Blocks.OAK_LOG,
				CustomBlockStateModelGenerator.blockAndTopForEnds(Blocks.OAK_LOG)
		);
		// :::datagen-model-custom:method-call

		// :::datagen-model:provider
	}

	// :::datagen-model:provider

	// used just for examples, not for actual data generation
	@SuppressWarnings("unused")
	public void exampleBlockStateGeneration(BlockStateModelGenerator blockStateModelGenerator) {
		// :::datagen-model:block-texture-pool-family
		blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.RUBY_BLOCK).family(ModBlocks.RUBY_FAMILY);
		// :::datagen-model:block-texture-pool-family
	}

	// :::datagen-model:provider

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		// :::datagen-model:provider

		//TODO Since I have little experience with generating item models, I will leave this to someone more experienced (Fellteros)

		// :::datagen-model:spawn-egg
		int shellColor = 0x000000;
		int spotsColor = 0xffffff;
		itemModelGenerator.registerSpawnEgg(ModItems.CUSTOM_SPAWN_EGG_DATA_GEN, shellColor, spotsColor);
		// :::datagen-model:spawn-egg

		// :::datagen-model:provider
	}

	// :::datagen-model:provider

	// Inner class containing all Objects needed for the custom datagen tutorial.
	public static class CustomBlockStateModelGenerator {
		// :::datagen-model-custom:model
		public static final Model VERTICAL_SLAB = block("vertical_slab", TextureKey.BOTTOM, TextureKey.TOP, TextureKey.SIDE);

		//helper method for creating Models
		private static Model block(String parent, TextureKey... requiredTextureKeys) {
			return new Model(Optional.of(Identifier.of(FabricDocsReference.MOD_ID, "block/" + parent)), Optional.empty(), requiredTextureKeys);
		}

		//helper method for creating Models with variants
		private static Model block(String parent, String variant, TextureKey... requiredTextureKeys) {
			return new Model(Optional.of(Identifier.of(FabricDocsReference.MOD_ID, "block/" + parent)), Optional.of(variant), requiredTextureKeys);
		}

		// :::datagen-model-custom:model

		// :::datagen-model-custom:texture-map
		public static TextureMap blockAndTopForEnds(Block block) {
			return new TextureMap()
					.put(TextureKey.TOP, ModelIds.getBlockSubModelId(block, "_top"))
					.put(TextureKey.BOTTOM, ModelIds.getBlockSubModelId(block, "_top"))
					.put(TextureKey.SIDE, ModelIds.getBlockModelId(block));
		}

		// :::datagen-model-custom:texture-map

		// :::datagen-model-custom:supplier
		private static BlockStateSupplier createVerticalSlabBlockStates(Block vertSlabBlock, Identifier vertSlabId, Identifier fullBlockId) {
			VariantSetting<Boolean> uvlock = VariantSettings.UVLOCK;
			VariantSetting<VariantSettings.Rotation> yRot = VariantSettings.Y;
			return VariantsBlockStateSupplier.create(vertSlabBlock).coordinate(BlockStateVariantMap.create(VerticalSlabBlock.FACING, VerticalSlabBlock.SINGLE)
					.register(Direction.NORTH, true, BlockStateVariant.create().put(VariantSettings.MODEL, vertSlabId).put(uvlock, true))
					.register(Direction.EAST, true, BlockStateVariant.create().put(VariantSettings.MODEL, vertSlabId).put(uvlock, true).put(yRot, VariantSettings.Rotation.R90))
					.register(Direction.SOUTH, true, BlockStateVariant.create().put(VariantSettings.MODEL, vertSlabId).put(uvlock, true).put(yRot, VariantSettings.Rotation.R180))
					.register(Direction.WEST, true, BlockStateVariant.create().put(VariantSettings.MODEL, vertSlabId).put(uvlock, true).put(yRot, VariantSettings.Rotation.R270))
					.register(Direction.NORTH, false, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockId).put(uvlock, true))
					.register(Direction.EAST, false, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockId).put(uvlock, true))
					.register(Direction.SOUTH, false, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockId).put(uvlock, true))
					.register(Direction.WEST, false, BlockStateVariant.create().put(VariantSettings.MODEL, fullBlockId).put(uvlock, true)));
		}

		// :::datagen-model-custom:supplier

		// :::datagen-model-custom:gen
		public static void registerVerticalSlab(BlockStateModelGenerator generator, Block vertSlabBlock, Block fullBlock, TextureMap textures) {
			Identifier slabModel = VERTICAL_SLAB.upload(vertSlabBlock, textures, generator.modelCollector);
			Identifier fullBlockModel = ModelIds.getBlockModelId(fullBlock);
			generator.blockStateCollector.accept(createVerticalSlabBlockStates(vertSlabBlock, slabModel, fullBlockModel));
			generator.registerParentedItemModel(vertSlabBlock, slabModel);
		}

		// :::datagen-model-custom:gen
	}

	// :::datagen-model:provider
	@Override
	public String getName() {
		return "FabricDocsReference Model Provider";
	}
}
// :::datagen-model:provider
