package com.example.docs.enchantment;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import com.example.docs.ExampleMod;
import com.example.docs.enchantment.effect.LightningEnchantmentEffect;

//#entrypoint
public class ModEnchantmentEffects {
	public static final ResourceKey<Enchantment> THUNDERING = of("thundering");
	public static MapCodec<LightningEnchantmentEffect> LIGHTNING_EFFECT = register("lightning_effect", LightningEnchantmentEffect.CODEC);

	private static ResourceKey<Enchantment> of(String path) {
		ResourceLocation id = ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, path);
		return ResourceKey.create(Registries.ENCHANTMENT, id);
	}

	private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> codec) {
		return Registry.register(BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE, ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, id), codec);
	}

	public static void registerModEnchantmentEffects() {
		ExampleMod.LOGGER.info("Registering EnchantmentEffects for" + ExampleMod.MOD_ID);
	}
}
