package com.example.docs.network.basic;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;

import com.example.docs.networking.basic.GiveGlowingEffectC2SPayload;
import com.example.docs.networking.basic.SummonLightningS2CPayload;

public class FabricDocsReferenceNetworkingBasicClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// :::client_global_receiver
		ClientPlayNetworking.registerGlobalReceiver(SummonLightningS2CPayload.ID, (payload, context) -> {
			ClientWorld world = context.client().world;

			if (world == null) {
				return;
			}

			BlockPos lightningPos = payload.pos();
			LightningEntity entity = EntityType.LIGHTNING_BOLT.create(world, SpawnReason.TRIGGERED);

			if (entity != null) {
				entity.setPosition(lightningPos.getX(), lightningPos.getY(), lightningPos.getZ());
				world.addEntity(entity);
			}
		});
		// :::client_global_receiver

		// :::use_entity_callback
		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (!world.isClient()) {
				return ActionResult.PASS;
			}

			ItemStack usedItemStack = player.getStackInHand(hand);

			if (entity instanceof LivingEntity && usedItemStack.isOf(Items.POISONOUS_POTATO) && hand == Hand.MAIN_HAND) {
				GiveGlowingEffectC2SPayload payload = new GiveGlowingEffectC2SPayload(hitResult.getEntity().getId());
				ClientPlayNetworking.send(payload);

				return ActionResult.SUCCESS;
			}

			return ActionResult.PASS;
		});
		// :::use_entity_callback
	}
}
