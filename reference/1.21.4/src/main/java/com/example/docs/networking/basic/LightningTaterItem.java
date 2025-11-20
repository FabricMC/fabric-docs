package com.example.docs.networking.basic;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

// :::lightning_tater_item
public class LightningTaterItem extends Item {
	public LightningTaterItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		if (world.isClient()) {
			return ActionResult.PASS;
		}

		SummonLightningS2CPayload payload = new SummonLightningS2CPayload(user.getBlockPos());

		for (ServerPlayerEntity player : PlayerLookup.world((ServerWorld) world)) {
			ServerPlayNetworking.send(player, payload);
		}

		return ActionResult.SUCCESS;
	}
}
// :::lightning_tater_item
