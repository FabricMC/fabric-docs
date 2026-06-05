package com.example.docs.stats;

import net.fabricmc.api.ModInitializer;

public class ExampleModStats implements ModInitializer {
	@Override
	public void onInitialize() {
		ModStats.initialize();
	}
}
