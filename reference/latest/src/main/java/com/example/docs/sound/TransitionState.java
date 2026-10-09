package com.example.docs.sound;

import net.minecraft.resources.Identifier;

import com.example.docs.ExampleMod;

// #region transition_state
public enum TransitionState {
	STARTING("starting_phase"),
	RUNNING("idle_phase"),
	ENDING("ending_phase");

	private final Identifier identifier;

	TransitionState(String name) {
		this.identifier = ExampleMod.id(name);
	}

	public Identifier getIdentifier() {
		return this.identifier;
	}
}
// #endregion transition_state
