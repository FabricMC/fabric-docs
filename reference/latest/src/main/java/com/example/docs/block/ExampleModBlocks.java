package com.example.docs.block;

import net.fabricmc.api.ModInitializer;

// #region initialize
public class ExampleModBlocks implements ModInitializer {
	@Override
	public void onInitialize() {
		ModBlocks.initialize();
	}
}
// #endregion initialize
