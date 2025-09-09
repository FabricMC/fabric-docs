package com.example.docs.block;

import net.fabricmc.api.ModInitializer;

// :::1
public class ExampleModBlocks implements ModInitializer {
	@Override
	public void onInitialize() {
		ModBlocks.initialize();
	}
}
// :::1
