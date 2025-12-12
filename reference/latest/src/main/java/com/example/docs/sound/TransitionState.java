package com.example.docs.sound;

import net.minecraft.resources.Identifier;

import com.example.docs.ExampleMod;

// :::1
public enum TransitionState {
	STARTING("starting_phase"),
	RUNNING("idle_phase"),
	ENDING("ending_phase");

	private final Identifier identifier;

	TransitionState(String name) {
		this.identifier = Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name);
	}

	public Identifier getIdentifier() {
		return identifier;
	}
}
// :::1
