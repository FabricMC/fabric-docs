package com.example.docs.enchantment;

import com.mojang.serialization.MapCodec;

import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import com.example.docs.FabricDocsReference;
import com.example.docs.enchantment.effect.LightningEnchantmentEffect;

//#entrypoint
public class ModEnchantmentEffects {
	public static MapCodec<LightningEnchantmentEffect> LIGHTNING_EFFECT = register("lightning_effect", LightningEnchantmentEffect.CODEC);

	private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
		return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(FabricDocsReference.MOD_ID, id), codec);
	}

	public static void registerModEnchantmentEffects() {
		System.out.println("Registering EnchantmentEffects for" + FabricDocsReference.MOD_ID);
	}
}
