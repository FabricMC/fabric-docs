package com.example.docs.networking.basic;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

// :::lightning_tater_item
public class LightningTaterItem extends Item {
	public LightningTaterItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player user, InteractionHand hand) {
		if (level.isClientSide()) {
			return InteractionResult.PASS;
		}

		SummonLightningS2CPayload payload = new SummonLightningS2CPayload(user.blockPosition());

		for (ServerPlayer player : PlayerLookup.world((ServerLevel) level)) {
			ServerPlayNetworking.send(player, payload);
		}

		return InteractionResult.SUCCESS;
	}
}
// :::lightning_tater_item
