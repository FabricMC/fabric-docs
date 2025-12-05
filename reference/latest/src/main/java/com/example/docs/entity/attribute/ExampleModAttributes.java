package com.example.docs.entity.attribute;

import net.fabricmc.api.ModInitializer;

// :::init
public class ExampleModAttributes implements ModInitializer {
	@Override
	public void onInitialize() {
		ModAttributes.initialize();
	}
}
// :::init
