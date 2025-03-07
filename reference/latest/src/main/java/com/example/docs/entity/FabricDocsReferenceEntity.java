package com.example.docs.entity;

import net.fabricmc.api.ModInitializer;

public class FabricDocsReferenceEntity implements ModInitializer {

	@Override
	public void onInitialize() {
		ModEntityTypes.registerModEntityTypes();

		ModEntityTypes.registerAttributes();
	}
}
