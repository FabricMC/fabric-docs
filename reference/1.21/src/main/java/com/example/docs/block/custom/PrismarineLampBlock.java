package com.example.docs.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// :::1
public class PrismarineLampBlock extends Block {
	public static final BooleanProperty ACTIVATED = BooleanProperty.of("activated");

	// :::1
	// :::3
	public PrismarineLampBlock(Settings settings) {
		super(settings);

		// Set the default state of the block to be deactivated.
		setDefaultState(getDefaultState().with(ACTIVATED, false));
	}

	// :::3
	// :::2
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(ACTIVATED);
	}

	// :::2
	// :::4
	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (!player.getAbilities().allowModifyWorld) {
			// Skip if the player isn't allowed to modify the world.
			return ActionResult.PASS;
		} else {
			// Get the current value of the "activated" property
			boolean activated = state.get(ACTIVATED);

			// Flip the value of activated and save the new blockstate.
			world.setBlockState(pos, state.with(ACTIVATED, !activated));

			// Play a click sound to emphasise the interaction.
			world.playSound(player, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 1.0F, 1.0F);

			return ActionResult.SUCCESS;
		}
	}

	// :::4
	// :::5
	public static int getLuminance(BlockState currentBlockState) {
		// Get the value of the "activated" property.
		boolean activated = currentBlockState.get(PrismarineLampBlock.ACTIVATED);

		// Return a light level if activated = true
		return activated ? 15 : 0;
	}

	// :::5
	// :::1
}
// :::1
