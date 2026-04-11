package com.example.docs.damage;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

import net.fabricmc.api.ModInitializer;

import com.example.docs.ExampleMod;

public class ExampleModDamageTypes implements ModInitializer {
	// #region damage-type
	public static final ResourceKey<DamageType> TATER_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "tater"));
	// #endregion damage-type

	@Override
	public void onInitialize() {
	}
}
