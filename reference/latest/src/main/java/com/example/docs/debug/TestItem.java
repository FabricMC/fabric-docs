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

import com.example.docs.ExampleMod;

public class TestItem extends Item {
	public TestItem(Properties settings) {
		super(settings);
	}

	// #region problems_logger_usage_example
	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
		Level level = user.level();
		// #endregion problems_logger_usage_example
		if (level.isClientSide()) {
			// #region problems_using_logger
			ExampleMod.LOGGER.info("You interacted with an entity!");
			// #endregion problems_using_logger
		}
		// #region problems_logger_usage_example

		// Extra parameters will be interpolated into the format string
		ExampleMod.LOGGER.info(
				"Is Client World: {} | Health: {} / {} | The item was used with the {}",
				level.isClientSide(), entity.getHealth(), entity.getMaxHealth(), hand
		);

		if (!level.isClientSide()) {
			// you can log non-critical issues differently as a warning
			ExampleMod.LOGGER.warn("Don't touch that!");

			// The LOGGER can print the Stacktrace too in addition to the logging message
			if (stack.getCount() > 1) {
				IllegalArgumentException exception = new IllegalArgumentException("Only one item is allowed");
				ExampleMod.LOGGER.error("Error while interacting with an entity", exception);
				throw exception;
			}
		}

		return InteractionResult.SUCCESS;
	}
	// #endregion problems_logger_usage_example

	// #region problems_breakpoints
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
				user.sendOverlayMessage(Component.literal("Changed Item Name"));
			}

			return InteractionResult.SUCCESS;
		}

		return InteractionResult.PASS;
	}
	// #endregion problems_breakpoints
}
