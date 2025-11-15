package com.example.docs.networking.basic;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import com.example.docs.ExampleMod;

public class ExampleModNetworkingBasic implements ModInitializer {
	public static final ResourceKey<Item> LIGHTNING_TATER_REGISTRY_KEY = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "lightning_tater"));
	public static final Item LIGHTNING_TATER = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "lightning_tater"), new LightningTaterItem(new Item.Properties().setId(LIGHTNING_TATER_REGISTRY_KEY)));

	public void onInitialize() {
		PayloadTypeRegistry.playS2C().register(SummonLightningS2CPayload.ID, SummonLightningS2CPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(GiveGlowingEffectC2SPayload.ID, GiveGlowingEffectC2SPayload.CODEC);

		// :::server_global_receiver
		ServerPlayNetworking.registerGlobalReceiver(GiveGlowingEffectC2SPayload.ID, (payload, context) -> {
			Entity entity = context.player().level().getEntity(payload.entityId());

			if (entity instanceof LivingEntity livingEntity && livingEntity.closerThan(context.player(), 5)) {
				livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100));
			}
		});
		// :::server_global_receiver
	}
}
