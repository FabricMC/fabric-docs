package com.example.docs.item;

import net.fabricmc.api.ModInitializer;

// :::1
public class ExampleModItems implements ModInitializer {
	@Override
	public void onInitialize() {
		ModItems.initialize();
	}
}
// :::1
