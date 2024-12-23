package com.example.docs.datagen;
// :::data-generation-setup:2
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class FabricDocsReferenceDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		// :::data-generation-setup:2
		// :::data-generation-setup:3
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		// :::data-generation-setup:3
		// :::data-generation-setup:2
	}
}
// :::data-generation-setup:2