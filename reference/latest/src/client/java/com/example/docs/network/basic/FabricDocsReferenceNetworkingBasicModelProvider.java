package com.example.docs.network.basic;

import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;

import com.example.docs.networking.basic.FabricDocsReferenceNetworkingBasic;

public class FabricDocsReferenceNetworkingBasicModelProvider extends FabricModelProvider {
	public FabricDocsReferenceNetworkingBasicModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		itemModelGenerator.register(FabricDocsReferenceNetworkingBasic.LIGHTNING_TATER, Models.HANDHELD);
	}
}
