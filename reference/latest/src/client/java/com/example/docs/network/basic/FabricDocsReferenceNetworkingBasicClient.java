package com.example.docs.network.basic;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

import com.example.docs.networking.basic.SummonLightningPayload;
import com.example.docs.networking.basic.UsePoisonousPotatoPayload;

public class FabricDocsReferenceNetworkingBasicClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// :::client_global_receiver
		ClientPlayNetworking.registerGlobalReceiver(SummonLightningPayload.ID, (payload, context) -> {
			ClientWorld world = context.client().world;

			if (world != null) {
				BlockPos entityPos = payload.pos();

				LightningEntity entity = EntityType.LIGHTNING_BOLT.create(world);

				if (entity != null) {
					entity.setPosition(entityPos.getX(), entityPos.getY(), entityPos.getZ());
					world.addEntity(entity);
				}
			}
		});
		// :::client_global_receiver

		// :::use_item_callback
		UseItemCallback.EVENT.register((playerEntity, world, hand) -> {
			ItemStack usedItemStack = playerEntity.getStackInHand(hand);

			if (usedItemStack.isOf(Items.POISONOUS_POTATO) && hand == Hand.MAIN_HAND) {
				UsePoisonousPotatoPayload payload = new UsePoisonousPotatoPayload(playerEntity.getName().getString(), playerEntity.getBlockPos());
				ClientPlayNetworking.send(payload);

				return TypedActionResult.success(playerEntity.getStackInHand(hand));
			}

			return TypedActionResult.pass(playerEntity.getStackInHand(hand));
		});
		// :::use_item_callback
	}
}
