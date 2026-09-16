package com.example.docs.entity.attribute;

import net.fabricmc.api.ModInitializer;

// #region init
public class ExampleModAttributes implements ModInitializer {
	@Override
	public void onInitialize() {
		ModAttributes.initialize();
	}
}
// #endregion init
