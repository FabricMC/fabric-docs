package com.example.docs.debug;

import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;

public class TestItem extends Item {
	public TestItem(Settings settings) {
		super(settings);
	}

	// ::::::problems:logger-usage-example
	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		World world = user.getWorld();

		// ::::::problems:logger-usage-example
		if (world.isClient()) {
			// :::problems:using-logger
			ExampleModDebug.LOGGER.info("You interacted with an entity!");
			// :::problems:using-logger
		}

		// ::::::problems:logger-usage-example

		// Values are used in a String to provide more information in the console
		String output = "Is Client World: %s | Health: %s / %s | The item was used with the %s"
				.formatted(user.getWorld().isClient(), entity.getHealth(), entity.getMaxHealth(), hand.name());

		ExampleModDebug.LOGGER.info(output);

		if (!user.getWorld().isClient()) {
			// you can log non-critical issues differently as a warning
			ExampleModDebug.LOGGER.warn("Don't touch that!");

			// The LOGGER can print the Stacktrace too in addition to the logging message
			if (stack.getCount() > 1) {
				IllegalArgumentException exception = new IllegalArgumentException("Only one item is allowed");
				ExampleModDebug.LOGGER.error("Error while interacting with an entity", exception);
				throw exception;
			}
		}

		return ActionResult.SUCCESS;
	}

	// ::::::problems:logger-usage-example

	// :::problems:breakpoints
	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		PlayerEntity user = context.getPlayer();
		BlockPos targetPos = context.getBlockPos();
		ItemStack itemStack = context.getStack();
		BlockState state = world.getBlockState(targetPos);

		if (state.isIn(ConventionalBlockTags.STONES) || state.isIn(ConventionalBlockTags.COBBLESTONES)) {
			Text newName = Text.literal("[").append(state.getBlock().getName()).append(Text.literal("]"));
			itemStack.set(DataComponentTypes.CUSTOM_NAME, newName);

			if (user != null) {
				user.sendMessage(Text.literal("Changed Item Name"), true);
			}

			return ActionResult.SUCCESS;
		}

		return ActionResult.PASS;
	}

	// :::problems:breakpoints
}
