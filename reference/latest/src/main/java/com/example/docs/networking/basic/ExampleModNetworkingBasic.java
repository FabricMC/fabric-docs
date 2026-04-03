package com.example.docs.networking.basic;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ExampleModNetworkingBasic implements ModInitializer {
	public void onInitialize() {
		PayloadTypeRegistry.clientboundPlay().register(ClientboundSummonLightningPayload.TYPE, ClientboundSummonLightningPayload.CODEC);
		PayloadTypeRegistry.serverboundPlay().register(GiveGlowingEffectServerboundPayload.TYPE, GiveGlowingEffectServerboundPayload.CODEC);

		// :::server_global_receiver
		ServerPlayNetworking.registerGlobalReceiver(GiveGlowingEffectServerboundPayload.TYPE, (payload, context) -> {
			Entity entity = context.player().level().getEntity(payload.entityId());

			if (entity instanceof LivingEntity livingEntity && livingEntity.closerThan(context.player(), 5)) {
				livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100));
			}
		});
		// :::server_global_receiver
	}
}
