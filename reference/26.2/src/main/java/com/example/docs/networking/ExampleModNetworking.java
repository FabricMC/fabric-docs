package com.example.docs.networking;

import net.fabricmc.api.ModInitializer;

public class ExampleModNetworking implements ModInitializer {
	@Override
	public void onInitialize() {
		NetworkPayloads.initialize();
	}
}
