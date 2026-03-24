package com.example.docs.sound;

import com.example.docs.ExampleMod;
import net.minecraft.resources.ResourceLocation;

// :::1
public enum TransitionState {
	STARTING("starting_phase"),
	RUNNING("idle_phase"),
	ENDING("ending_phase");

	private final ResourceLocation identifier;

	TransitionState(String name) {
		this.identifier = ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, name);
	}

	public ResourceLocation getIdentifier() {
		return identifier;
	}
}
// :::1
