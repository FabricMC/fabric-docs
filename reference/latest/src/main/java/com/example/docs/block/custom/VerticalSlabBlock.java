package com.example.docs.block.custom;

import java.util.Objects;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

// #region custom_constructor
public class VerticalSlabBlock extends Block {
	// #endregion custom_constructor

	// #region custom_properties
	public static final BooleanProperty SINGLE = BooleanProperty.create("single");
	public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
	// #endregion custom_properties
	// #region custom_voxels
	public static final VoxelShape NORTH_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 8.0);
	public static final VoxelShape SOUTH_SHAPE = Block.box(0.0, 0.0, 8.0, 16.0, 16.0, 16.0);
	public static final VoxelShape WEST_SHAPE = Block.box(0.0, 0.0, 0.0, 8.0, 16.0, 16.0);
	public static final VoxelShape EAST_SHAPE = Block.box(8.0, 0.0, 0.0, 16.0, 16.0, 16.0);
	// #endregion custom_voxels

	// #region custom_constructor
	public VerticalSlabBlock(Properties settings) {
		super(settings);
	}

	// #endregion custom_constructor

	// #region custom_collision
	@Override
	protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
		boolean type = state.getValue(SINGLE);
		Direction direction = state.getValue(FACING);
		VoxelShape voxelShape;

		if (type) {
			switch (direction) {
				case WEST -> voxelShape = WEST_SHAPE.singleEncompassing();
				case EAST -> voxelShape = EAST_SHAPE.singleEncompassing();
				case SOUTH -> voxelShape = SOUTH_SHAPE.singleEncompassing();
				case NORTH -> voxelShape = NORTH_SHAPE.singleEncompassing();
				default -> throw new MatchException(null, null);
			}

			return voxelShape;
		} else {
			return Shapes.block();
		}
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return this.getBlockSupportShape(state, level, pos);
	}

	@Override
	protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return this.getBlockSupportShape(state, level, pos);
	}
	// #endregion custom_collision

	// #region custom_replace
	@Override
	protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
		Direction direction = state.getValue(FACING);

		if (context.getItemInHand().is(this.asItem()) && state.getValue(SINGLE)) {
			if (context.replacingClickedOnBlock()) {
				return context.getClickedFace().getOpposite() == direction;
			}
		}

		return false;
	}
	// #endregion custom_replace

	// #region custom_placement
	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		BlockPos pos = ctx.getClickedPos();
		Direction direction = ctx.getHorizontalDirection();
		BlockState state = ctx.getLevel().getBlockState(pos);
		BlockState state2 = Objects.requireNonNull(super.getStateForPlacement(ctx));

		if (state.is(this) && state.getValue(FACING) == ctx.getClickedFace().getOpposite()) {
			return state.is(this) ? state2.setValue(SINGLE, false) : super.getStateForPlacement(ctx);
		}

		if (direction == Direction.NORTH && ctx.getClickLocation().z - pos.getZ() > 0.5) {
			return state2.setValue(FACING, Direction.SOUTH).setValue(SINGLE, true);
		} else if (direction == Direction.SOUTH && ctx.getClickLocation().z - pos.getZ() < 0.5) {
			return state2.setValue(FACING, Direction.NORTH).setValue(SINGLE, true);
		} else if (direction == Direction.WEST && ctx.getClickLocation().x - pos.getX() > 0.5) {
			return state2.setValue(FACING, Direction.EAST).setValue(SINGLE, true);
		} else if (direction == Direction.EAST && ctx.getClickLocation().x - pos.getX() < 0.5) {
			return state2.setValue(FACING, Direction.WEST).setValue(SINGLE, true);
		} else {
			return state2.setValue(FACING, direction);
		}
	}
	// #endregion custom_placement

	// #region custom_append
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(SINGLE, FACING);
	}
	// #endregion custom_append

	// #region custom_constructor
}
// #endregion custom_constructor
