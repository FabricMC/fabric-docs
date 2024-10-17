package com.example.docs;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import com.example.docs.data.EnchantmentGenerator;

public class FabricDocsReferenceDataGenerator implements DataGeneratorEntrypoint {
	//#initdatagen
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(EnchantmentGenerator::new);
	}
}
