package com.example.docs.menu;

import net.fabricmc.api.ModInitializer;

public class ExampleModMenuType implements ModInitializer {
	/**
	 * Runs the mod initializer.
	 */
	@Override
	public void onInitialize() {
		ModMenuType.initialize();
	}
}
