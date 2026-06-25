package com.example.docs.item;

import net.fabricmc.api.ModInitializer;

// #region initialize
public class ExampleModItems implements ModInitializer {
	@Override
	public void onInitialize() {
		ModItems.initialize();
	}
}
// #endregion initialize
