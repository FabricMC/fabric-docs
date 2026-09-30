package com.example.docs.component;

import net.fabricmc.api.ModInitializer;

public class ExampleModComponents implements ModInitializer {
	@Override
	public void onInitialize() {
		ModComponents.initialize();
	}
}
