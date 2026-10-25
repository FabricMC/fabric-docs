package com.example.docs.fluid;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

import com.example.docs.ExampleMod;

// #region tags
public class ModFluidTags {
	public static TagKey<Fluid> ACID = TagKey.create(Registries.FLUID, ExampleMod.id("acid"));
}
// #endregion tags
