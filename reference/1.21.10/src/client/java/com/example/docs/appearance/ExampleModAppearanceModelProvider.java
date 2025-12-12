package com.example.docs.appearance;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.BlockModelGenerators.PlantType;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

public class ExampleModAppearanceModelProvider extends FabricModelProvider {
	public ExampleModAppearanceModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
		blockStateModelGenerator.createCrossBlock(ExampleModAppearance.WAXCAP_BLOCK, PlantType.TINTED);
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		ResourceLocation modelLocation = itemModelGenerator.createFlatItemModel(ExampleModAppearance.WAXCAP_BLOCK_ITEM, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.itemModelOutput.accept(ExampleModAppearance.WAXCAP_BLOCK_ITEM, ItemModelUtils.tintedModel(
						modelLocation,
						new RainTintSource()
		));
	}

	@Override
	public @NotNull String getName() {
		return "ExampleModAppearanceModelProvider";
	}
}
