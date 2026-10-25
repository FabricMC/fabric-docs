package com.example.docs.fluid;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.material.Fluid;

import com.example.docs.ExampleMod;

// #region register
public class ModFluidIds {
	public static final ResourceKey<Fluid> ACID_FLOWING = create("flowing_acid");
	public static final ResourceKey<Fluid> ACID_STILL = create("acid");

	public static ResourceKey<Fluid> create(String name) {
		// Create the fluid key.
		return ResourceKey.create(Registries.FLUID, ExampleMod.id(name));
	}
}
// #endregion register
