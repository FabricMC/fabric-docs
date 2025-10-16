package com.example.docs.networking.basic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import com.example.docs.FabricDocsReference;

public class FabricDocsReferenceNetworkingBasic implements ModInitializer {
	public static final RegistryKey<Item> LIGHTNING_TATER_REGISTRY_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(FabricDocsReference.MOD_ID, "lightning_tater"));
	public static final Item LIGHTNING_TATER = Registry.register(Registries.ITEM, Identifier.of(FabricDocsReference.MOD_ID, "lightning_tater"), new LightningTaterItem(new Item.Settings().registryKey(LIGHTNING_TATER_REGISTRY_KEY)));

	public void onInitialize() {
		PayloadTypeRegistry.playS2C().register(SummonLightningS2CPayload.ID, SummonLightningS2CPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(GiveGlowingEffectC2SPayload.ID, GiveGlowingEffectC2SPayload.CODEC);

		// :::server_global_receiver
		ServerPlayNetworking.registerGlobalReceiver(GiveGlowingEffectC2SPayload.ID, (payload, context) -> {
			Entity entity = context.player().getWorld().getEntityById(payload.entityId());

			if (entity instanceof LivingEntity livingEntity && livingEntity.isInRange(context.player(), 5)) {
				livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100));
			}
		});
		// :::server_global_receiver
	}
}
