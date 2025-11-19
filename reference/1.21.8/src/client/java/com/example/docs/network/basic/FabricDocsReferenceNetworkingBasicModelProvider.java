package com.example.docs.network.basic;

import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

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
