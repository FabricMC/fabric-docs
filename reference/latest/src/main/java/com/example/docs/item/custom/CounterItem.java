package com.example.docs.item.custom;

import java.util.function.Consumer;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
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
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);

		// Don't do anything on the client
		if (world.isClient()) {
			return ActionResult.SUCCESS;
		}

		// Read the current count and increase it by one
		int count = stack.getOrDefault(ModComponents.CLICK_COUNT_COMPONENT, 0);
		stack.set(ModComponents.CLICK_COUNT_COMPONENT, ++count);

		return ActionResult.SUCCESS;
	}

	//::2

	@Override
	//::3
	public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
		if (stack.contains(ModComponents.CLICK_COUNT_COMPONENT)) {
			int count = stack.get(ModComponents.CLICK_COUNT_COMPONENT);
			textConsumer.accept(Text.translatable("item.fabric-docs-reference.counter.info", count).formatted(Formatting.GOLD));
		}
	}

	//::3

	//::1
}
//::1
