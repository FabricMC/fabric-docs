package com.example.docs.datagen;
// :::datagen-setup:2
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class FabricDocsReferenceDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		// :::datagen-setup:2
		// :::datagen-setup:3
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		// :::datagen-setup:3
		// :::datagen-setup:2
	}
}
// :::datagen-setup:2