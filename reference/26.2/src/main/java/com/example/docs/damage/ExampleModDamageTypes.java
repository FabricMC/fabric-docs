package com.example.docs.damage;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

import net.fabricmc.api.ModInitializer;

import com.example.docs.ExampleMod;

public class ExampleModDamageTypes implements ModInitializer {
	// #region damage_type
	public static final ResourceKey<DamageType> TATER_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, ExampleMod.id("tater"));
	// #endregion damage_type

	@Override
	public void onInitialize() {
	}
}
