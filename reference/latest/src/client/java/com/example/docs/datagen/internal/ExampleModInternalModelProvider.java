package com.example.docs.datagen.internal;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import com.example.docs.block.ModBlocks;
import com.example.docs.block.custom.PrismarineLampBlock;
import com.example.docs.item.ModItems;

/**
 * This generator is just for the reference item and block models.
 * Not for describing how to use the model provider.
 */
public class ExampleModInternalModelProvider extends FabricModelProvider {
	public ExampleModInternalModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
		blockStateModelGenerator.createTrivialCube(ModBlocks.CONDENSED_DIRT);
		blockStateModelGenerator.createTrivialCube(ModBlocks.COUNTER_BLOCK);

		// TODO: This would be a good example for the model generation page. Move when needed.
		// TODO: Actually make the model for the prismarine lamp - not sure how to do it via datagen.
		blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.dispatch(ModBlocks.PRISMARINE_LAMP)
				.with(BlockModelGenerators.createBooleanModelDispatch(PrismarineLampBlock.ACTIVATED,
						BlockModelGenerators.variant(blockStateModelGenerator.createSuffixedVariant(ModBlocks.PRISMARINE_LAMP, "_on", ModelTemplates.CUBE_ALL, TextureMapping::all)),
						BlockModelGenerators.variant(TexturedModel.CUBE.create(ModBlocks.PRISMARINE_LAMP, blockStateModelGenerator.modelOutput)))));

		blockStateModelGenerator.woodProvider(ModBlocks.CONDENSED_OAK_LOG).log(ModBlocks.CONDENSED_OAK_LOG);
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		itemModelGenerator.generateFlatItem(ModItems.COUNTER, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.LIGHTNING_STICK, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.GUIDITE_BOOTS, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.GUIDITE_CHESTPLATE, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.GUIDITE_HELMET, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.GUIDITE_LEGGINGS, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.POISONOUS_APPLE, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.SUSPICIOUS_SUBSTANCE, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.generateFlatItem(ModItems.GUIDITE_SWORD, ModelTemplates.FLAT_HANDHELD_ITEM);
	}

	@Override
	public String getName() {
		return "ExampleModInternalModelProvider";
	}
}
