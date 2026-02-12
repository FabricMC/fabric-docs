package com.example.docs.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class CustomSoundItem extends Item {
	public CustomSoundItem(Properties properties) {
		super(properties);
	}

	// :::1
	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
		// As stated above, don't use the playSound() method on the client side
		// ... it won't work!
		if (!entity.level().isClientSide()) {
			// Play the sound as if it was coming from the entity.
			entity.playSound(SoundEvents.PILLAGER_AMBIENT, 2f, 0.7f);
		}

		return super.interactLivingEntity(stack, user, entity, hand);
	}

	// :::1
	// :::2
	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (!context.getLevel().isClientSide()) {
			// Play the sound and specify location, category and who made the sound.
			// No entity made the sound, so we specify null.
			context.getLevel().playSound(null, context.getClickedPos(),
					SoundEvents.COPPER_PLACE, SoundSource.PLAYERS,
					1f, 1f);
		}

		return super.useOn(context);
	}

	// :::2
}
