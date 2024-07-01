package com.example.docs.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class CustomSoundItem extends Item {
	public CustomSoundItem(Settings settings) {
		super(settings);
	}

	// :::1
	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		// As stated above, don't use the playSound() method on the client side
		// ... it wont work!
		if (!entity.getWorld().isClient()) {
			// Play the sound as if it was coming from the entity.
			entity.playSound(SoundEvents.ENTITY_PILLAGER_AMBIENT, 2f, 0.7f);
		}

		return super.useOnEntity(stack, user, entity, hand);
	}

	// :::1
	// :::2
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (!context.getWorld().isClient()) {
			// Play the sound and specify location, category and who made the sound.
			// No entity made the sound, so we specify null.
			context.getWorld().playSound(null, context.getBlockPos(),
					SoundEvents.BLOCK_COPPER_PLACE, SoundCategory.PLAYERS,
					1f, 1f);
		}

		return super.useOnBlock(context);
	}

	// :::2
}
