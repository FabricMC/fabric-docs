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

// #region lightning_tater_item
public class LightningTaterItem extends Item {
	public LightningTaterItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player user, InteractionHand hand) {
		// #region client_check
		if (level.isClientSide()) {
			return InteractionResult.PASS;
		}
		// #endregion client_check

		// #region payload_instance
		ClientboundSummonLightningPayload payload = new ClientboundSummonLightningPayload(user.blockPosition());
		// #endregion payload_instance

		// #region lookup
		for (ServerPlayer player : PlayerLookup.level((ServerLevel) level)) {
			ServerPlayNetworking.send(player, payload);
		}
		// #endregion lookup

		return InteractionResult.SUCCESS;
	}
}
// #endregion lightning_tater_item
