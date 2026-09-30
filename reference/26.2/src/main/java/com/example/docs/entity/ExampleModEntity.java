package com.example.docs.entity;

import net.fabricmc.api.ModInitializer;

// #region entrypoint
public class ExampleModEntity implements ModInitializer {
	@Override
	public void onInitialize() {
		ModEntityTypes.registerModEntityTypes();

		ModEntityTypes.registerAttributes();
	}
}
// #endregion entrypoint
