package com.example.docs.datagen;

import java.util.List;
import java.util.Optional;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.numeric.Count;
import net.minecraft.client.renderer.item.properties.select.ContextDimension;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import com.example.docs.ExampleMod;
import com.example.docs.block.ModBlocks;
import com.example.docs.block.custom.VerticalSlabBlock;
import com.example.docs.item.ModItems;

// :::provider
public class ExampleModModelProvider extends FabricModelProvider {
	public ExampleModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
		// :::provider

		// :::cube-all
		blockStateModelGenerator.createTrivialCube(ModBlocks.STEEL_BLOCK);
		// :::cube-all

		// :::cube-top-for-ends
		blockStateModelGenerator.createTrivialBlock(ModBlocks.PIPE_BLOCK, TexturedModel.COLUMN_ALT);
		// :::cube-top-for-ends

		// :::block-texture-pool-normal
		blockStateModelGenerator.family(ModBlocks.RUBY_BLOCK)
				.stairs(ModBlocks.RUBY_STAIRS)
				.slab(ModBlocks.RUBY_SLAB)
				.fence(ModBlocks.RUBY_FENCE);
		// :::block-texture-pool-normal

		// :::door-and-trapdoor
		blockStateModelGenerator.createDoor(ModBlocks.RUBY_DOOR);
		blockStateModelGenerator.createTrapdoor(ModBlocks.RUBY_TRAPDOOR);
		// blockStateModelGenerator.registerOrientableTrapdoor(ModBlocks.RUBY_TRAPDOOR);
		// :::door-and-trapdoor

		// :::custom-method-call
		CustomBlockStateModelGenerator.registerVerticalSlab(
				blockStateModelGenerator,
				ModBlocks.VERTICAL_OAK_LOG_SLAB,
				Blocks.OAK_LOG,
				CustomBlockStateModelGenerator.blockAndTopForEnds(Blocks.OAK_LOG)
		);
		// :::custom-method-call

		// :::provider
	}
	// :::provider

	// used just for examples, not for actual data generation
	@SuppressWarnings("unused")
	public void exampleBlockStateGeneration(BlockModelGenerators blockStateModelGenerator) {
		// :::block-texture-pool-family
		blockStateModelGenerator.family(ModBlocks.RUBY_BLOCK).generateFor(ModBlocks.RUBY_FAMILY);
		// :::block-texture-pool-family
	}

	// :::provider

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		// :::provider

		//:::generated
		itemModelGenerator.generateFlatItem(ModItems.RUBY, ModelTemplates.FLAT_ITEM);
		//:::generated

		// :::datagen-model:provider

		itemModelGenerator.generateFlatItem(ModItems.MINI_GOLEM_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
		//:::handheld
		itemModelGenerator.generateFlatItem(ModItems.GUIDITE_AXE, ModelTemplates.FLAT_HANDHELD_ITEM);
		//:::handheld

		//:::spawn-egg
		itemModelGenerator.generateFlatItem(ModItems.CUSTOM_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
		//:::spawn-egg

		//:::dyeable
		itemModelGenerator.generateDyedItem(ModItems.LEATHER_GLOVES, 0xFFA06540);
		//:::dyeable

		//:::condition
		itemModelGenerator.generateBooleanDispatch(
						ModItems.FLASHLIGHT,
						ItemModelUtils.isUsingItem(),
						ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(ModItems.FLASHLIGHT, "_lit", ModelTemplates.FLAT_ITEM)),
						ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(ModItems.FLASHLIGHT, ModelTemplates.FLAT_ITEM))
		);
		//:::condition

		//:::composite
		ItemModel.Unbaked hoe = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(ModItems.ENHANCED_HOE, ModelTemplates.FLAT_ITEM));
		ItemModel.Unbaked hoePlus = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(ModItems.ENHANCED_HOE, "_plus", ModelTemplates.FLAT_ITEM));

		itemModelGenerator.itemModelOutput.accept(
						ModItems.ENHANCED_HOE,
						ItemModelUtils.composite(hoe, hoePlus)
		);
		//:::composite

		//:::select
		ItemModel.Unbaked crystalOverworld = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(ModItems.DIMENSIONAL_CRYSTAL, "_overworld", ModelTemplates.FLAT_ITEM));
		ItemModel.Unbaked crystalNether = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(ModItems.DIMENSIONAL_CRYSTAL, "_nether", ModelTemplates.FLAT_ITEM));
		ItemModel.Unbaked crystalEnd = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(ModItems.DIMENSIONAL_CRYSTAL, "_end", ModelTemplates.FLAT_ITEM));

		itemModelGenerator.itemModelOutput.accept(
						ModItems.DIMENSIONAL_CRYSTAL,
						ItemModelUtils.select(new ContextDimension(),
										ItemModelUtils.when(Level.OVERWORLD, crystalOverworld),
										ItemModelUtils.when(Level.NETHER, crystalNether),
										ItemModelUtils.when(Level.END, crystalEnd)
						)
		);
		//:::select

		//:::range-dispatch
		ItemModel.Unbaked knifeOne = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(ModItems.THROWING_KNIVES, "_one", ModelTemplates.FLAT_ITEM));
		ItemModel.Unbaked knifeTwo = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(ModItems.THROWING_KNIVES, "_two", ModelTemplates.FLAT_ITEM));
		ItemModel.Unbaked knifeThree = ItemModelUtils.plainModel(itemModelGenerator.createFlatItemModel(ModItems.THROWING_KNIVES, "_three", ModelTemplates.FLAT_ITEM));

		itemModelGenerator.itemModelOutput.accept(
						ModItems.THROWING_KNIVES,
						ItemModelUtils.rangeSelect(
										new Count(false),
										List.of(
														ItemModelUtils.override(knifeOne, 1.0F),
														ItemModelUtils.override(knifeTwo, 2.0F),
														ItemModelUtils.override(knifeThree, 3.0F)
										)
						)
		);
		//:::range-dispatch

		//:::custom-balloon
		CustomItemModelGenerator.registerScaled2x(ModItems.BALLOON, itemModelGenerator);
		//:::custom-balloon

		// :::provider
	}
	// :::provider

	// Inner class containing custom objects for item model generation.
	// :::custom-item-model-generator:::
	public static class CustomItemModelGenerator {
		//:::custom-item-model:::
		public static final ModelTemplate SCALED2X = item("scaled2x", TextureSlot.LAYER0);
		//:::custom-item-model:::

		//:::custom-item-datagen-method
		public static void registerScaled2x(Item item, ItemModelGenerators generator) {
			Identifier itemModel = SCALED2X.create(item, TextureMapping.singleSlot(TextureSlot.LAYER0, ModelLocationUtils.getModelLocation(item)), generator.modelOutput);
			generator.itemModelOutput.accept(item, ItemModelUtils.plainModel(itemModel));
		}

		//:::custom-item-datagen-method

		@SuppressWarnings("SameParameterValue")
		//:::custom-item-model:::

		private static ModelTemplate item(String parent, TextureSlot requiredTextureKeys) {
			return new ModelTemplate(Optional.of(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "item/" + parent)), Optional.empty(), requiredTextureKeys);
		}

		//:::custom-item-model:::
	}
	// :::custom-item-model-generator:::

	// Inner class containing all Objects needed for the custom datagen tutorial.
	// :::custom-blockstate-model-generator
	public static class CustomBlockStateModelGenerator {
		// :::custom-model
		public static final ModelTemplate VERTICAL_SLAB = block("vertical_slab", TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE);

		//helper method for creating Models
		private static ModelTemplate block(String parent, TextureSlot... requiredTextureKeys) {
			return new ModelTemplate(Optional.of(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "block/" + parent)), Optional.empty(), requiredTextureKeys);
		}

		//helper method for creating Models with variants
		private static ModelTemplate block(String parent, String variant, TextureSlot... requiredTextureKeys) {
			return new ModelTemplate(Optional.of(Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "block/" + parent)), Optional.of(variant), requiredTextureKeys);
		}

		// :::custom-model

		// :::custom-texture-map
		public static TextureMapping blockAndTopForEnds(Block block) {
			return new TextureMapping()
					.put(TextureSlot.TOP, ModelLocationUtils.getModelLocation(block, "_top"))
					.put(TextureSlot.BOTTOM, ModelLocationUtils.getModelLocation(block, "_top"))
					.put(TextureSlot.SIDE, ModelLocationUtils.getModelLocation(block));
		}

		// :::custom-texture-map

		// :::custom-supplier
		private static BlockModelDefinitionGenerator createVerticalSlabBlockStates(Block vertSlabBlock, Identifier vertSlabId, Identifier fullBlockId) {
			MultiVariant vertSlabModel = BlockModelGenerators.plainVariant(vertSlabId);
			MultiVariant fullBlockModel = BlockModelGenerators.plainVariant(fullBlockId);
			return MultiVariantGenerator.dispatch(vertSlabBlock)
					.with(PropertyDispatch.initial(VerticalSlabBlock.FACING, VerticalSlabBlock.SINGLE)
						.select(Direction.NORTH, true, vertSlabModel.with(BlockModelGenerators.UV_LOCK))
						.select(Direction.EAST, true, vertSlabModel.with(BlockModelGenerators.UV_LOCK).with(BlockModelGenerators.Y_ROT_90))
						.select(Direction.SOUTH, true, vertSlabModel.with(BlockModelGenerators.UV_LOCK).with(BlockModelGenerators.Y_ROT_180))
						.select(Direction.WEST, true, vertSlabModel.with(BlockModelGenerators.UV_LOCK).with(BlockModelGenerators.Y_ROT_270))
						.select(Direction.NORTH, false, fullBlockModel.with(BlockModelGenerators.UV_LOCK))
						.select(Direction.EAST, false, fullBlockModel.with(BlockModelGenerators.UV_LOCK))
						.select(Direction.SOUTH, false, fullBlockModel.with(BlockModelGenerators.UV_LOCK))
						.select(Direction.WEST, false, fullBlockModel.with(BlockModelGenerators.UV_LOCK))
					);
		}

		// :::custom-supplier

		// :::custom-gen
		public static void registerVerticalSlab(BlockModelGenerators generator, Block vertSlabBlock, Block fullBlock, TextureMapping textures) {
			Identifier slabModel = VERTICAL_SLAB.create(vertSlabBlock, textures, generator.modelOutput);
			Identifier fullBlockModel = ModelLocationUtils.getModelLocation(fullBlock);
			generator.blockStateOutput.accept(createVerticalSlabBlockStates(vertSlabBlock, slabModel, fullBlockModel));
			generator.registerSimpleItemModel(vertSlabBlock, slabModel);
		}

		// :::custom-gen
	}
	// :::custom-blockstate-model-generator

	// :::provider

	@Override
	public String getName() {
		return "ExampleModModelProvider";
	}
}
// :::provider
