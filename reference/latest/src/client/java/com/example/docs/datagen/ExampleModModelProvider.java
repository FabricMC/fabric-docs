package com.example.docs.datagen;

import java.util.Optional;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.data.BlockModelDefinitionCreator;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Model;
import net.minecraft.client.data.ModelIds;
import net.minecraft.client.data.TextureKey;
import net.minecraft.client.data.TextureMap;
import net.minecraft.client.data.TexturedModel;
import net.minecraft.client.data.VariantsBlockModelDefinitionCreator;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import com.example.docs.ExampleMod;
import com.example.docs.block.ModBlocks;
import com.example.docs.block.custom.VerticalSlabBlock;

// :::datagen-model:provider
public class ExampleModModelProvider extends FabricModelProvider {
	public ExampleModModelProvider(FabricDataOutput output) {
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

		// :::datagen-model:provider
	}

	// :::datagen-model:provider

	// Inner class containing all Objects needed for the custom datagen tutorial.
	public static class CustomBlockStateModelGenerator {
		// :::datagen-model-custom:model
		public static final Model VERTICAL_SLAB = block("vertical_slab", TextureKey.BOTTOM, TextureKey.TOP, TextureKey.SIDE);

		//helper method for creating Models
		private static Model block(String parent, TextureKey... requiredTextureKeys) {
			return new Model(Optional.of(Identifier.of(ExampleMod.MOD_ID, "block/" + parent)), Optional.empty(), requiredTextureKeys);
		}

		//helper method for creating Models with variants
		private static Model block(String parent, String variant, TextureKey... requiredTextureKeys) {
			return new Model(Optional.of(Identifier.of(ExampleMod.MOD_ID, "block/" + parent)), Optional.of(variant), requiredTextureKeys);
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
		private static BlockModelDefinitionCreator createVerticalSlabBlockStates(Block vertSlabBlock, Identifier vertSlabId, Identifier fullBlockId) {
			WeightedVariant vertSlabModel = BlockStateModelGenerator.createWeightedVariant(vertSlabId);
			WeightedVariant fullBlockModel = BlockStateModelGenerator.createWeightedVariant(fullBlockId);
			return VariantsBlockModelDefinitionCreator.of(vertSlabBlock)
					.with(BlockStateVariantMap.models(VerticalSlabBlock.FACING, VerticalSlabBlock.SINGLE)
						.register(Direction.NORTH, true, vertSlabModel.apply(BlockStateModelGenerator.UV_LOCK))
						.register(Direction.EAST, true, vertSlabModel.apply(BlockStateModelGenerator.UV_LOCK).apply(BlockStateModelGenerator.ROTATE_Y_90))
						.register(Direction.SOUTH, true, vertSlabModel.apply(BlockStateModelGenerator.UV_LOCK).apply(BlockStateModelGenerator.ROTATE_Y_180))
						.register(Direction.WEST, true, vertSlabModel.apply(BlockStateModelGenerator.UV_LOCK).apply(BlockStateModelGenerator.ROTATE_Y_270))
						.register(Direction.NORTH, false, fullBlockModel.apply(BlockStateModelGenerator.UV_LOCK))
						.register(Direction.EAST, false, fullBlockModel.apply(BlockStateModelGenerator.UV_LOCK))
						.register(Direction.SOUTH, false, fullBlockModel.apply(BlockStateModelGenerator.UV_LOCK))
						.register(Direction.WEST, false, fullBlockModel.apply(BlockStateModelGenerator.UV_LOCK))
					);
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
		return "ExampleModModelProvider";
	}
}
// :::datagen-model:provider
