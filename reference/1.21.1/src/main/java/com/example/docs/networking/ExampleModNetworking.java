package com.example.docs.networking;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import com.example.docs.ExampleMod;

public class ExampleModNetworking implements ModInitializer {
	public static final String MOD_ID = ExampleMod.MOD_ID;

	@Override
	public void onInitialize() {
		NetworkPayloads.initialize();
	}

	public static ResourceLocation getId(String input) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, input);
	}
}
