package com.example.docs.sound;

import com.example.docs.FabricDocsReference;

import net.minecraft.util.Identifier;

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
