package com.example.docs.datagen;

import java.util.Optional;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.BlockStateGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.blockstates.VariantProperty;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import com.example.docs.FabricDocsReference;
import com.example.docs.block.ModBlocks;
import com.example.docs.block.custom.VerticalSlabBlock;

// :::datagen-model:provider
public class FabricDocsReferenceModelProvider extends FabricModelProvider {
	public FabricDocsReferenceModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
		// :::datagen-model:provider

		// :::datagen-model:cube-all
		blockStateModelGenerator.createTrivialCube(ModBlocks.STEEL_BLOCK);
		// :::datagen-model:cube-all

		// :::datagen-model:cube-top-for-ends
		blockStateModelGenerator.createTrivialBlock(ModBlocks.PIPE_BLOCK, TexturedModel.COLUMN_ALT);
		// :::datagen-model:cube-top-for-ends

		// :::datagen-model:block-texture-pool-normal
		blockStateModelGenerator.family(ModBlocks.RUBY_BLOCK)
				.stairs(ModBlocks.RUBY_STAIRS)
				.slab(ModBlocks.RUBY_SLAB)
				.fence(ModBlocks.RUBY_FENCE);
		// :::datagen-model:block-texture-pool-normal

		// :::datagen-model:door-and-trapdoor
		blockStateModelGenerator.createDoor(ModBlocks.RUBY_DOOR);
		blockStateModelGenerator.createTrapdoor(ModBlocks.RUBY_TRAPDOOR);
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
	public void exampleBlockStateGeneration(BlockModelGenerators blockStateModelGenerator) {
		// :::datagen-model:block-texture-pool-family
		blockStateModelGenerator.family(ModBlocks.RUBY_BLOCK).generateFor(ModBlocks.RUBY_FAMILY);
		// :::datagen-model:block-texture-pool-family
	}

	// :::datagen-model:provider

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		// :::datagen-model:provider

		//TODO Since I have little experience with generating item models, I will leave this to someone more experienced (Fellteros)

		// :::datagen-model:provider
	}

	// :::datagen-model:provider

	// Inner class containing all Objects needed for the custom datagen tutorial.
	public static class CustomBlockStateModelGenerator {
		// :::datagen-model-custom:model
		public static final ModelTemplate VERTICAL_SLAB = block("vertical_slab", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE);

		//helper method for creating Models
		private static ModelTemplate block(String parent, TextureSlot... requiredTextureKeys) {
			return new ModelTemplate(Optional.of(ResourceLocation.fromNamespaceAndPath(FabricDocsReference.MOD_ID, "block/" + parent)), Optional.empty(), requiredTextureKeys);
		}

		//helper method for creating Models with variants
		private static ModelTemplate block(String parent, String variant, TextureSlot... requiredTextureKeys) {
			return new ModelTemplate(Optional.of(ResourceLocation.fromNamespaceAndPath(FabricDocsReference.MOD_ID, "block/" + parent)), Optional.of(variant), requiredTextureKeys);
		}

		// :::datagen-model-custom:model

		// :::datagen-model-custom:texture-map
		public static TextureMapping blockAndTopForEnds(Block block) {
			return new TextureMapping()
					.put(TextureSlot.TOP, ModelLocationUtils.getModelLocation(block, "_top"))
					.put(TextureSlot.BOTTOM, ModelLocationUtils.getModelLocation(block, "_top"))
					.put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(block));
		}

		// :::datagen-model-custom:texture-map

		// :::datagen-model-custom:supplier
		private static BlockStateGenerator createVerticalSlabBlockStates(Block vertSlabBlock, ResourceLocation vertSlabId, ResourceLocation fullBlockId) {
			VariantProperty<Boolean> uvlock = VariantProperties.UV_LOCK;
			VariantProperty<VariantProperties.Rotation> yRot = VariantProperties.Y_ROT;
			return MultiVariantGenerator.multiVariant(vertSlabBlock).with(PropertyDispatch.properties(VerticalSlabBlock.FACING, VerticalSlabBlock.SINGLE)
					.select(Direction.NORTH, true, Variant.variant().with(VariantProperties.MODEL, vertSlabId).with(uvlock, true))
					.select(Direction.EAST, true, Variant.variant().with(VariantProperties.MODEL, vertSlabId).with(uvlock, true).with(yRot, VariantProperties.Rotation.R90))
					.select(Direction.SOUTH, true, Variant.variant().with(VariantProperties.MODEL, vertSlabId).with(uvlock, true).with(yRot, VariantProperties.Rotation.R180))
					.select(Direction.WEST, true, Variant.variant().with(VariantProperties.MODEL, vertSlabId).with(uvlock, true).with(yRot, VariantProperties.Rotation.R270))
					.select(Direction.NORTH, false, Variant.variant().with(VariantProperties.MODEL, fullBlockId).with(uvlock, true))
					.select(Direction.EAST, false, Variant.variant().with(VariantProperties.MODEL, fullBlockId).with(uvlock, true))
					.select(Direction.SOUTH, false, Variant.variant().with(VariantProperties.MODEL, fullBlockId).with(uvlock, true))
					.select(Direction.WEST, false, Variant.variant().with(VariantProperties.MODEL, fullBlockId).with(uvlock, true)));
		}

		// :::datagen-model-custom:supplier

		// :::datagen-model-custom:gen
		public static void registerVerticalSlab(BlockModelGenerators generator, Block vertSlabBlock, Block fullBlock, TextureMapping textures) {
			ResourceLocation slabModel = VERTICAL_SLAB.create(vertSlabBlock, textures, generator.modelOutput);
			ResourceLocation fullBlockModel = ModelLocationUtils.getModelLocation(fullBlock);
			generator.blockStateOutput.accept(createVerticalSlabBlockStates(vertSlabBlock, slabModel, fullBlockModel));
			generator.registerSimpleItemModel(vertSlabBlock, slabModel);
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
