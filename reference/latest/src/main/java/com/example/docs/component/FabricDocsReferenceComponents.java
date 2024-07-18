package com.example.docs.component;

import net.fabricmc.api.ModInitializer;

// :::1
public class FabricDocsReferenceComponents implements ModInitializer {
	@Override
	public void onInitialize() {
		ModComponents.initialize();
	}
}
// :::1
