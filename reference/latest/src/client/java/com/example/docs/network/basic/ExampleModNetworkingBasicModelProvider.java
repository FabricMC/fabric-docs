package com.example.docs.network.basic;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import com.example.docs.networking.basic.ExampleModNetworkingBasic;

public class ExampleModNetworkingBasicModelProvider extends FabricModelProvider {
	public ExampleModNetworkingBasicModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		itemModelGenerator.generateFlatItem(ExampleModNetworkingBasic.LIGHTNING_TATER, ModelTemplates.FLAT_HANDHELD_ITEM);
	}
}
