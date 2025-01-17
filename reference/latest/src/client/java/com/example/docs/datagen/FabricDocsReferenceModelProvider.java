package com.example.docs.datagen;

// :::datagen-model:provider
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;

public class FabricDocsReferenceModelProvider extends FabricModelProvider {
	public FabricDocsReferenceModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		// :::datagen-model:provider

		// :::datagen-model:provider
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		// :::datagen-model:provider

		// :::datagen-model:provider
	}
}
// :::datagen-model:provider
