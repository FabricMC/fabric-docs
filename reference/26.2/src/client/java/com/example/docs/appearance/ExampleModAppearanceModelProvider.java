package com.example.docs.appearance;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.BlockModelGenerators.PlantType;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

import com.example.docs.block.ModBlocks;

public class ExampleModAppearanceModelProvider extends FabricModelProvider {
	public ExampleModAppearanceModelProvider(FabricPackOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
		blockStateModelGenerator.createCrossBlock(ModBlocks.WAXCAP, PlantType.TINTED);
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		Item waxcap = ModBlocks.WAXCAP.asItem();
		Identifier modelLocation = itemModelGenerator.createFlatItemModel(waxcap, ModelTemplates.FLAT_ITEM);
		itemModelGenerator.itemModelOutput.accept(waxcap, ItemModelUtils.tintedModel(
						modelLocation,
						new RainTintSource()
		));
	}

	@Override
	public @NotNull String getName() {
		return "ExampleModAppearanceModelProvider";
	}
}
