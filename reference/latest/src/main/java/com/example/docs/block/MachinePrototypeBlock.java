package com.example.docs.block;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

// :::1
public class MachinePrototypeBlock extends Block {
	public MachinePrototypeBlock(Settings settings) {
		super(settings);
		// :::1
		// :::3
		// Set the default facing state to NORTH.
		// BlockState#with method returns the block state with the passed property set to the passed value.
		this.setDefaultState(this.stateManager.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
		// :::3
		// :::1
	}
	// :::1

	// :::2
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		// Add the horizontal "facing" state.
		builder.add(Properties.HORIZONTAL_FACING);
	}
	// :::2

	// :::4
	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		// This determines the state used when a player places the block.
		return getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		// Some features in the game - such as structure blocks - allow rotating blocks.
		// This implements the rotation logic.
		// You might see "deprecation warnings" here. You can safely ignore these.
		return state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		// To get a property's value, use BlockState#get.
		return state.rotate(mirror.getRotation(state.get(Properties.HORIZONTAL_FACING)));
	}
	// :::4
	// :::5
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		// This is one of the methods that are called by both the (logical) server and the clients.
		// However, many of the logics must be performed on the server side alone.
		if (!world.isClient) {
			// Play the explosion sound.
			world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.5f, 0.5f);
		}

		// ActionResult indicates whether the interaction was successful.
		// SUCCESS is used when the block was successfully used, swinging the player hand.
		// For example, if no hand animation is desired, you can use CONSUME here instead.
		return ActionResult.SUCCESS;
	}
	// :::5
	// :::1
}
// :::1
