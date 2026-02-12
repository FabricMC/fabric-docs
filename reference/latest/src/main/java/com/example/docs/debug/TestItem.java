package com.example.docs.debug;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;

public class TestItem extends Item {
	public TestItem(Properties settings) {
		super(settings);
	}

	// ::::::problems:logger-usage-example
	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
		Level level = user.level();

		// ::::::problems:logger-usage-example
		if (level.isClientSide()) {
			// :::problems:using-logger
			ExampleModDebug.LOGGER.info("You interacted with an entity!");
			// :::problems:using-logger
		}

		// ::::::problems:logger-usage-example

		// Values are used in a String to provide more information in the console
		String output = "Is Client World: %s | Health: %s / %s | The item was used with the %s"
				.formatted(user.level().isClientSide(), entity.getHealth(), entity.getMaxHealth(), hand.name());

		ExampleModDebug.LOGGER.info(output);

		if (!user.level().isClientSide()) {
			// you can log non-critical issues differently as a warning
			ExampleModDebug.LOGGER.warn("Don't touch that!");

			// The LOGGER can print the Stacktrace too in addition to the logging message
			if (stack.getCount() > 1) {
				IllegalArgumentException exception = new IllegalArgumentException("Only one item is allowed");
				ExampleModDebug.LOGGER.error("Error while interacting with an entity", exception);
				throw exception;
			}
		}

		return InteractionResult.SUCCESS;
	}

	// ::::::problems:logger-usage-example

	// :::problems:breakpoints
	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		Player user = context.getPlayer();
		BlockPos targetPos = context.getClickedPos();
		ItemStack itemStack = context.getItemInHand();
		BlockState state = level.getBlockState(targetPos);

		if (state.is(ConventionalBlockTags.STONES) || state.is(ConventionalBlockTags.COBBLESTONES)) {
			Component newName = Component.literal("[").append(state.getBlock().getName()).append(Component.literal("]"));
			itemStack.set(DataComponents.CUSTOM_NAME, newName);

			if (user != null) {
				user.displayClientMessage(Component.literal("Changed Item Name"), true);
			}

			return InteractionResult.SUCCESS;
		}

		return InteractionResult.PASS;
	}

	// :::problems:breakpoints
}
