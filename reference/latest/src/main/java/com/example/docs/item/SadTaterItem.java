package com.example.docs.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SadTaterItem extends Item {
	// :::1
	public SadTaterItem(Settings settings) {
		super(settings);
	}

	// :::1

	// :::2
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		user.playSound(SoundEvents.ENTITY_VILLAGER_HURT, 1.0f, 1.0f);
		return TypedActionResult.success(user.getStackInHand(hand));
	}

	// :::2
}

