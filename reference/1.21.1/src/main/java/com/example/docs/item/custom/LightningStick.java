package com.example.docs.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

// :::1
public class LightningStick extends Item {
	public LightningStick(Properties settings) {
		super(settings);
	}

	// :::1
	// :::2
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		// Ensure we don't spawn the lightning only on the client.
		// This is to prevent desync.
		if (world.isClientSide) {
			return InteractionResultHolder.pass(user.getItemInHand(hand));
		}

		BlockPos frontOfPlayer = user.blockPosition().relative(user.getDirection(), 10);

		// Spawn the lightning bolt.
		LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, world);
		lightningBolt.setPos(frontOfPlayer.getCenter());
		world.addFreshEntity(lightningBolt);

		// Nothing has changed to the item stack,
		// so we just return it how it was.
		return InteractionResultHolder.success(user.getItemInHand(hand));
	}

	// :::2
	// :::3
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		tooltip.add(Component.translatable("itemTooltip.example-mod.lightning_stick").withStyle(ChatFormatting.GOLD));
	}

	// :::3
	// :::1
}
// :::1
