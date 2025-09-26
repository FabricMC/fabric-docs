package com.example.docs.datagen.internal;

import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.TextureMap;
import net.minecraft.client.data.TexturedModel;
import net.minecraft.client.data.VariantsBlockModelDefinitionCreator;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

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
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CONDENSED_DIRT);
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.COUNTER_BLOCK);

		// TODO: This would be a good example for the model generation page. Move when needed.
		// TODO: Actually make the model for the prismarine lamp - not sure how to do it via datagen.
		blockStateModelGenerator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(ModBlocks.PRISMARINE_LAMP)
				.with(BlockStateModelGenerator.createBooleanModelMap(PrismarineLampBlock.ACTIVATED,
						BlockStateModelGenerator.createWeightedVariant(blockStateModelGenerator.createSubModel(ModBlocks.PRISMARINE_LAMP, "_on", Models.CUBE_ALL, TextureMap::all)),
						BlockStateModelGenerator.createWeightedVariant(TexturedModel.CUBE_ALL.upload(ModBlocks.PRISMARINE_LAMP, blockStateModelGenerator.modelCollector)))));

		blockStateModelGenerator.createLogTexturePool(ModBlocks.CONDENSED_OAK_LOG).log(ModBlocks.CONDENSED_OAK_LOG);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		itemModelGenerator.register(ModItems.COUNTER, Models.GENERATED);
		itemModelGenerator.register(ModItems.LIGHTNING_STICK, Models.GENERATED);
		itemModelGenerator.register(ModItems.GUIDITE_BOOTS, Models.GENERATED);
		itemModelGenerator.register(ModItems.GUIDITE_CHESTPLATE, Models.GENERATED);
		itemModelGenerator.register(ModItems.GUIDITE_HELMET, Models.GENERATED);
		itemModelGenerator.register(ModItems.GUIDITE_LEGGINGS, Models.GENERATED);
		itemModelGenerator.register(ModItems.POISONOUS_APPLE, Models.GENERATED);
		itemModelGenerator.register(ModItems.SUSPICIOUS_SUBSTANCE, Models.GENERATED);
		itemModelGenerator.register(ModItems.GUIDITE_SWORD, Models.HANDHELD);
	}

	@Override
	public String getName() {
		return "ExampleModInternalModelProvider";
	}
}
