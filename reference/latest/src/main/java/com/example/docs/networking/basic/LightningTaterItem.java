package com.example.docs.networking.basic;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

// :::lightning_tater_item
public class LightningTaterItem extends Item {
	public LightningTaterItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (world.isClient) {
			return TypedActionResult.pass(user.getStackInHand(hand));
		}

		SummonLightningPayload payload = new SummonLightningPayload(user.getBlockPos());

		for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, user.getBlockPos())) {
			ServerPlayNetworking.send(player, payload);
		}

		return TypedActionResult.success(user.getStackInHand(hand));
	}
}
// :::lightning_tater_item
