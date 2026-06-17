package com.example.docs.fluid;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.material.Fluid;

import com.example.docs.ExampleMod;

// #region register
public class ModFluidIds {
	public static final ResourceKey<Fluid> ACID_FLOWING = register("flowing_acid");
	public static final ResourceKey<Fluid> ACID_STILL = register("acid");

	public static ResourceKey<Fluid> register(String name) {
		// Create the item key.
		return ResourceKey.create(Registries.FLUID, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name));
	}
}
// #region register
