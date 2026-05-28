package com.example.docs.conditions;

import net.fabricmc.api.ModInitializer;

//#region init
public class ExampleModResourceConditions implements ModInitializer {
	@Override
	public void onInitialize() {
		ModResourceConditions.register();
	}
}
//#endregion init
