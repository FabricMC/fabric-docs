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

import com.example.docs.networking.basic.ClientboundSummonLightningPayload;
import com.example.docs.networking.basic.GiveGlowingEffectServerboundPayload;

public class ExampleModNetworkingBasicClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// #region client-global-receiver
		ClientPlayNetworking.registerGlobalReceiver(ClientboundSummonLightningPayload.TYPE, (payload, context) -> {
			ClientLevel level = context.client().level;

			if (level == null) {
				return;
			}

			// #region payload-pos
			BlockPos lightningPos = payload.pos();
			// #endregion payload-pos

			// #region lightning-bolt
			LightningBolt entity = EntityType.LIGHTNING_BOLT.create(level, EntitySpawnReason.TRIGGERED);

			if (entity != null) {
				entity.setPos(lightningPos.getX(), lightningPos.getY(), lightningPos.getZ());
				level.addEntity(entity);
			}
			// #endregion lightning-bolt
		});
		// #endregion client-global-receiver

		// #region use-entity-callback
		UseEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
			if (!level.isClientSide()) {
				return InteractionResult.PASS;
			}

			ItemStack usedItemStack = player.getItemInHand(hand);

			if (entity instanceof LivingEntity && usedItemStack.is(Items.POISONOUS_POTATO) && hand == InteractionHand.MAIN_HAND) {
				// #region payload
				GiveGlowingEffectServerboundPayload payload = new GiveGlowingEffectServerboundPayload(hitResult.getEntity().getId());
				// #endregion payload

				// #region send
				ClientPlayNetworking.send(payload);
				// #endregion send

				return InteractionResult.SUCCESS;
			}

			return InteractionResult.PASS;
		});
		// #endregion use-entity-callback
	}
}
