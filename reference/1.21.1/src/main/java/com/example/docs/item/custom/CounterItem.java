package com.example.docs.item.custom;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import com.example.docs.component.ModComponents;

//::1
public class CounterItem extends Item {
	public CounterItem(Properties settings) {
		super(settings);
	}

	//::1

	@Override
	//::2
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		ItemStack stack = user.getItemInHand(hand);

		// Don't do anything on the client
		if (world.isClientSide()) {
			return InteractionResultHolder.success(stack);
		}

		// Read the current count and increase it by one
		int count = stack.getOrDefault(ModComponents.CLICK_COUNT_COMPONENT, 0);
		stack.set(ModComponents.CLICK_COUNT_COMPONENT, ++count);

		// Return the original stack
		return InteractionResultHolder.success(stack);
	}

	//::2

	@Override
	//::3
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		if (stack.has(ModComponents.CLICK_COUNT_COMPONENT)) {
			int count = stack.get(ModComponents.CLICK_COUNT_COMPONENT);
			tooltip.add(Component.translatable("item.example-mod.counter.info", count).withStyle(ChatFormatting.GOLD));
		}
	}

	//::3

	//::1
}
//::1
