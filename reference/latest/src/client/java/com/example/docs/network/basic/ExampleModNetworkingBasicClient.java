package com.example.docs.network.basic;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;

import com.example.docs.networking.basic.GiveGlowingEffectC2SPayload;
import com.example.docs.networking.basic.SummonLightningS2CPayload;

public class ExampleModNetworkingBasicClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// :::client_global_receiver
		ClientPlayNetworking.registerGlobalReceiver(SummonLightningS2CPayload.ID, (payload, context) -> {
			ClientLevel level = context.client().level;

			if (level == null) {
				return;
			}

			BlockPos lightningPos = payload.pos();
			LightningBolt entity = EntityType.LIGHTNING_BOLT.create(level, EntitySpawnReason.TRIGGERED);

			if (entity != null) {
				entity.setPos(lightningPos.getX(), lightningPos.getY(), lightningPos.getZ());
				level.addEntity(entity);
			}
		});
		// :::client_global_receiver

		// :::use_entity_callback
		UseEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
			if (!level.isClientSide()) {
				return InteractionResult.PASS;
			}

			ItemStack usedItemStack = player.getItemInHand(hand);

			if (entity instanceof LivingEntity && usedItemStack.is(Items.POISONOUS_POTATO) && hand == InteractionHand.MAIN_HAND) {
				GiveGlowingEffectC2SPayload payload = new GiveGlowingEffectC2SPayload(hitResult.getEntity().getId());
				ClientPlayNetworking.send(payload);

				return InteractionResult.SUCCESS;
			}

			return InteractionResult.PASS;
		});
		// :::use_entity_callback
	}
}
