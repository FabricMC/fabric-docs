package com.example.docs.block.entity;

import net.fabricmc.api.ModInitializer;

public class ExampleModEntities implements ModInitializer {
	@Override
	public void onInitialize() {
		ModBlockEntities.initialize();
	}
}
