package com.example.docs.stats;

import net.fabricmc.api.ModInitializer;

// #region initialize
public class ExampleModStats implements ModInitializer {
	@Override
	public void onInitialize() {
		ModStats.initialize();
	}
}
// #endregion initialize
