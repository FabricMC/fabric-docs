package com.example.docs.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

// :::datagen-model-custom:constructor
public class VerticalSlabBlock extends Block {
	// :::datagen-model-custom:constructor

	// :::datagen-model-custom:properties
	public static final BooleanProperty SINGLE = BooleanProperty.of("single");
	public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
	// :::datagen-model-custom:properties
	// :::datagen-model-custom:voxels
	public static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 8.0);
	public static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 16.0, 16.0);
	public static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 16.0, 16.0);
	public static final VoxelShape EAST_SHAPE = Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 16.0, 16.0);
	// :::datagen-model-custom:voxels

	// :::datagen-model-custom:constructor
	public VerticalSlabBlock(Settings settings) {
		super(settings);
	}
	// :::datagen-model-custom:constructor

	// :::datagen-model-custom:collision
	@Override
	protected VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
		boolean type = state.get(SINGLE);
		Direction direction = state.get(FACING);
		VoxelShape voxelShape;
		if (type) {
			switch (direction) {
				case WEST -> voxelShape = WEST_SHAPE.asCuboid();
				case EAST -> voxelShape = EAST_SHAPE.asCuboid();
				case SOUTH -> voxelShape = SOUTH_SHAPE.asCuboid();
				case NORTH -> voxelShape = NORTH_SHAPE.asCuboid();
				default -> throw new MatchException(null, null);
			}
			return voxelShape;
		} else {
			return VoxelShapes.fullCube();
		}
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return this.getSidesShape(state, world, pos);
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return this.getSidesShape(state, world, pos);
	}
	// :::datagen-model-custom:collision

	// :::datagen-model-custom:replace
	@Override
	protected boolean canReplace(BlockState state, ItemPlacementContext context) {
		Direction direction = state.get(FACING);
		if (context.getStack().isOf(this.asItem()) && state.get(SINGLE)) {
			if (context.canReplaceExisting()) {
				return context.getSide().getOpposite() == direction;
			}
		}
		return false;
	}
	// :::datagen-model-custom:replace


	// :::datagen-model-custom:placement
	@Override
	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockPos pos = ctx.getBlockPos();
		Direction direction = ctx.getHorizontalPlayerFacing();
		BlockState state = ctx.getWorld().getBlockState(pos);
		BlockState state2 = Objects.requireNonNull(super.getPlacementState(ctx));

		if (state.isOf(this) && state.get(FACING) == ctx.getSide().getOpposite()) {
			return state.isOf(this) ? state2.with(SINGLE, false) : super.getPlacementState(ctx);
		}

		if (direction == Direction.NORTH && ctx.getHitPos().z - pos.getZ() > 0.5) {
			return state2.with(FACING, Direction.SOUTH).with(SINGLE, true);
		} else if (direction == Direction.SOUTH && ctx.getHitPos().z - pos.getZ() < 0.5) {
			return state2.with(FACING, Direction.NORTH).with(SINGLE, true);
		} else if (direction == Direction.WEST && ctx.getHitPos().x - pos.getX() > 0.5) {
			return state2.with(FACING, Direction.EAST).with(SINGLE, true);
		} else if (direction == Direction.EAST && ctx.getHitPos().x - pos.getX() < 0.5) {
			return state2.with(FACING, Direction.WEST).with(SINGLE, true);
		} else {
			return state2.with(FACING, direction);
		}
	}

	// :::datagen-model-custom:placement

	// :::datagen-model-custom:append
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(SINGLE, FACING);
	}
	// :::datagen-model-custom:append


	// :::datagen-model-custom:constructor
}
// :::datagen-model-custom:constructor
