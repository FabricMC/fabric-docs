package com.example.docs.sound;

import net.minecraft.resources.ResourceLocation;

import com.example.docs.FabricDocsReference;

// :::1
public enum TransitionState {
	STARTING("starting_phase"),
	RUNNING("idle_phase"),
	ENDING("ending_phase");

	private final ResourceLocation identifier;

	TransitionState(String name) {
		this.identifier = ResourceLocation.fromNamespaceAndPath(FabricDocsReference.MOD_ID, name);
	}

	public ResourceLocation getIdentifier() {
		return identifier;
	}
}
// :::1
