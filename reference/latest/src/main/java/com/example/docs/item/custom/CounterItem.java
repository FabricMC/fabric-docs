package com.example.docs.item.custom;

import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import com.example.docs.component.ModComponents;

//::1
public class CounterItem extends Item {
	public CounterItem(Settings settings) {
		super(settings);
	}

	//::1

	@Override
	//::2
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);

		// Don't do anything on the client
		if (world.isClient()) {
			return TypedActionResult.success(stack);
		}

		// Read the current count and increase it by one
		int count = stack.getOrDefault(ModComponents.CLICK_COUNT_COMPONENT, 0);
		stack.set(ModComponents.CLICK_COUNT_COMPONENT, ++count);

		// Return the original stack
		return TypedActionResult.success(stack);
	}

	//::2

	@Override
	//::3
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		if (stack.contains(ModComponents.CLICK_COUNT_COMPONENT)) {
			int count = stack.get(ModComponents.CLICK_COUNT_COMPONENT);
			tooltip.add(Text.translatable("item.fabric-docs-reference.counter.info", count).formatted(Formatting.GOLD));
		}
	}

	//::3

	//::1
}
//::1
