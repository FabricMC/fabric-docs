package com.example.docs.networking;

import com.example.docs.FabricDocsReference;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;

public class FabricDocsReferenceNetworking implements ModInitializer {
	public static final String MOD_ID = FabricDocsReference.MOD_ID;

	@Override
	public void onInitialize() {
		NetworkPayloads.initialize();
	}

	public static Identifier getId(String input) {
		return Identifier.of(MOD_ID, input);
	}
}
