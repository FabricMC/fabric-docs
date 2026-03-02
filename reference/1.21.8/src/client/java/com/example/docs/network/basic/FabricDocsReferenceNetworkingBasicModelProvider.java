package com.example.docs.network.basic;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import com.example.docs.networking.basic.FabricDocsReferenceNetworkingBasic;

public class FabricDocsReferenceNetworkingBasicModelProvider extends FabricModelProvider {
	public FabricDocsReferenceNetworkingBasicModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
	}

	@Override
	public void generateItemModels(ItemModelGenerators itemModelGenerator) {
		itemModelGenerator.generateFlatItem(FabricDocsReferenceNetworkingBasic.LIGHTNING_TATER, ModelTemplates.FLAT_HANDHELD_ITEM);
	}
}
