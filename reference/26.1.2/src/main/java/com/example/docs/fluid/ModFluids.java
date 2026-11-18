package com.example.docs.fluid;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.FlowingFluid;

import com.example.docs.ExampleMod;
import com.example.docs.fluid.custom.AcidFluid;

// #region register
public class ModFluids {
	public static final FlowingFluid ACID_FLOWING = register("flowing_acid", new AcidFluid.Flowing());
	public static final FlowingFluid ACID_STILL = register("acid", new AcidFluid.Source());

	private static FlowingFluid register(String name, FlowingFluid fluid) {
		return Registry.register(BuiltInRegistries.FLUID, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name), fluid);
	}

	public static void initialize() {
	}
}
// #endregion register
