package com.example.docs.network.basic;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

import com.example.docs.networking.basic.GiveGlowingEffectPayload;
import com.example.docs.networking.basic.SummonLightningPayload;

public class FabricDocsReferenceNetworkingBasicClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// :::client_global_receiver
		ClientPlayNetworking.registerGlobalReceiver(SummonLightningPayload.ID, (payload, context) -> {
			ClientWorld world = context.client().world;

			if (world != null) {
				BlockPos entityPos = payload.pos();

				LightningEntity entity = EntityType.LIGHTNING_BOLT.create(world, SpawnReason.TRIGGERED);

				if (entity != null) {
					entity.setPosition(entityPos.getX(), entityPos.getY(), entityPos.getZ());
					world.addEntity(entity);
				}
			}
		});
		// :::client_global_receiver

		// :::use_item_callback
		UseItemCallback.EVENT.register((playerEntity, world, hand) -> {
			if (world.isClient()) {
				ItemStack usedItemStack = playerEntity.getStackInHand(hand);

				if (usedItemStack.isOf(Items.POISONOUS_POTATO) && hand == Hand.MAIN_HAND) {
					HitResult target = MinecraftClient.getInstance().crosshairTarget;

					if (target != null && target.getType() == HitResult.Type.ENTITY) {
						Entity targettedEntity = ((EntityHitResult) target).getEntity();
						GiveGlowingEffectPayload payload = new GiveGlowingEffectPayload(targettedEntity.getId());
						ClientPlayNetworking.send(payload);

						return ActionResult.SUCCESS;
					}
				}
			}

			return ActionResult.PASS;
		});
		// :::use_item_callback
	}
}
