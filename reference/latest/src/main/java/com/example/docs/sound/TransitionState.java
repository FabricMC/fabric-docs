package com.example.docs.sound;

import net.minecraft.util.Identifier;

import com.example.docs.FabricDocsReference;

// :::1
public enum TransitionState {
	STARTING("starting_phase"),
	RUNNING("idle_phase"),
	ENDING("ending_phase");

	private final Identifier identifier;

	TransitionState(String name) {
		this.identifier = Identifier.of(FabricDocsReference.MOD_ID, name);
	}

	public Identifier getIdentifier() {
		return identifier;
	}
}
// :::1
