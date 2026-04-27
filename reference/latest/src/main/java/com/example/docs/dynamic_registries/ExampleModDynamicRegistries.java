package com.example.docs.dynamic_registries;

import net.fabricmc.api.ModInitializer;

// #region main
public class ExampleModDynamicRegistries implements ModInitializer {
		@Override
		public void onInitialize() {
				MyRegistries.initialize();
		}
}
// #endregion main
