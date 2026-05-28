package com.example.docs.item.custom;

import java.util.function.Consumer;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import com.example.docs.component.ModComponents;

// #region item
public class CounterItem extends Item {
	public CounterItem(Properties properties) {
		super(properties);
	}
	// #endregion item

	@Override
	// #region use
	public InteractionResult use(Level level, Player user, InteractionHand hand) {
		ItemStack stack = user.getItemInHand(hand);

		// Don't do anything on the client
		if (level.isClientSide()) {
			return InteractionResult.SUCCESS;
		}

		// Read the current count and increase it by one
		int count = stack.getOrDefault(ModComponents.CLICK_COUNT_COMPONENT, 0);
		stack.set(ModComponents.CLICK_COUNT_COMPONENT, ++count);

		return InteractionResult.SUCCESS;
	}
	// #endregion use

	@Override
	// #region fixed-append-hover-text
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
		if (stack.has(ModComponents.CLICK_COUNT_COMPONENT)) {
			int count = stack.get(ModComponents.CLICK_COUNT_COMPONENT);
			textConsumer.accept(Component.translatable("item.example-mod.counter.info", count).withStyle(ChatFormatting.GOLD));
		}
	}
	// #endregion fixed-append-hover-text

	// #region item
}
// #endregion item
