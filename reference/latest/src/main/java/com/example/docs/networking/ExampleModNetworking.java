package com.example.docs.networking;

import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;

import com.example.docs.ExampleMod;

public class ExampleModNetworking implements ModInitializer {
	public static final String MOD_ID = ExampleMod.MOD_ID;

	@Override
	public void onInitialize() {
		NetworkPayloads.initialize();
	}

	public static Identifier getId(String input) {
		return Identifier.of(MOD_ID, input);
	}
}
