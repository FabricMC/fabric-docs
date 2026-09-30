package com.example.docs.fluid;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

import com.example.docs.fluid.custom.AcidFluid;

// #region register
public class ModFluids {
	public static final FlowingFluid ACID_FLOWING = register(ModFluidIds.ACID_FLOWING, new AcidFluid.Flowing());
	public static final FlowingFluid ACID_STILL = register(ModFluidIds.ACID_STILL, new AcidFluid.Source());

	private static FlowingFluid register(ResourceKey<Fluid> key, FlowingFluid fluid) {
		return Registry.register(BuiltInRegistries.FLUID, key, fluid);
	}

	public static void initialize() {
	}
}
// #endregion register
