package com.example.docs.networking.basic;

import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;

public class FabricDocsReferenceNetworkingBasicDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ModelGenerator::new);
	}

	public static class ModelGenerator extends FabricModelProvider {
		public ModelGenerator(FabricDataOutput output) {
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
}
