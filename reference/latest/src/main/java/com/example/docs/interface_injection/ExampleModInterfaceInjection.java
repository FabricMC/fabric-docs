package com.example.docs.interface_injection;

import java.util.Optional;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.material.FlowingFluid;

// Class to hold example usages for methods added with interface injection.
class ExampleModInterfaceInjection {
	// #region interface-injection-using-added-method
	void example(FlowingFluid flowingFluid) {
		Optional<SoundEvent> sound = flowingFluid.example_mod$getBucketEmptySound();
		/* ... */
	}
	// #endregion interface-injection-using-added-method
}
