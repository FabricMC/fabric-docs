package com.example.docs.item.custom;

import java.util.function.Consumer;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

// :::1
public class LightningStick extends Item {
	public LightningStick(Properties settings) {
		super(settings);
	}

	// :::1
	// :::2
	@Override
	public InteractionResult use(Level world, Player user, InteractionHand hand) {
		// Ensure we don't spawn the lightning only on the client.
		// This is to prevent desync.
		if (world.isClientSide) {
			return InteractionResult.PASS;
		}

		BlockPos frontOfPlayer = user.blockPosition().relative(user.getDirection(), 10);

		// Spawn the lightning bolt.
		LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, world);
		lightningBolt.setPos(frontOfPlayer.getCenter());
		world.addFreshEntity(lightningBolt);

		return InteractionResult.SUCCESS;
	}

	// :::2
	// :::3
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
		textConsumer.accept(Component.translatable("itemTooltip.fabric-docs-reference.lightning_stick").withStyle(ChatFormatting.GOLD));
	}

	// :::3
	// :::1
}
// :::1
