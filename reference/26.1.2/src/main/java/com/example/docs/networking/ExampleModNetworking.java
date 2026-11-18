package com.example.docs.networking;

import net.minecraft.resources.Identifier;

import net.fabricmc.api.ModInitializer;

import com.example.docs.ExampleMod;

public class ExampleModNetworking implements ModInitializer {
	@Override
	public void onInitialize() {
		NetworkPayloads.initialize();
	}

	public static Identifier getId(String input) {
		return Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, input);
	}
}
