package com.example.docs.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

// :::datagen-tags:generator
public class FabricDocsReferenceItemTagGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(FabricDocsReferenceItemTagProvider::new); // [!code ++]
	}
}
// :::datagen-tags:generator
