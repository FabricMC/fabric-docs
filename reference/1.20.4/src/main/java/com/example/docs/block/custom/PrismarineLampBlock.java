package com.example.docs.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

// :::1
public class PrismarineLampBlock extends Block {
	public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

	// :::1
	// :::3
	public PrismarineLampBlock(Properties settings) {
		super(settings);

		// Set the default state of the block to be deactivated.
		registerDefaultState(defaultBlockState().setValue(ACTIVATED, false));
	}

	// :::3
	// :::2
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(ACTIVATED);
	}

	// :::2
	// :::4
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (!player.getAbilities().mayBuild) {
			// Skip if the player isn't allowed to modify the world.
			return InteractionResult.PASS;
		} else {
			// Get the current value of the "activated" property
			boolean activated = state.getValue(ACTIVATED);

			// Flip the value of activated and save the new blockstate.
			world.setBlockAndUpdate(pos, state.setValue(ACTIVATED, !activated));

			// Play a click sound to emphasise the interaction.
			world.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 1.0F, 1.0F);

			return InteractionResult.SUCCESS;
		}
	}

	// :::4
	// :::5
	public static int getLuminance(BlockState currentBlockState) {
		// Get the value of the "activated" property.
		boolean activated = currentBlockState.getValue(PrismarineLampBlock.ACTIVATED);

		// Return a light level if activated = true
		return activated ? 15 : 0;
	}

	// :::5
	// :::1
}
// :::1
