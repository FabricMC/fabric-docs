package com.example.docs.block.entity.custom;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import com.example.docs.block.entity.ModBlockEntities;
import com.example.docs.container.ImplementedContainer;

/*
The following is a dummy piece of code to not have `implements WorldlyContainer` in the first code block where we implement `ImplementedContainer`.
lmk if you have a better idea on how to handle this.
// #region be
public class DuplicatorBlockEntity extends BlockEntity implements ImplementedContainer {
// #endregion be
*/

public class DuplicatorBlockEntity extends BlockEntity implements ImplementedContainer, WorldlyContainer {
	// #region be
	private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
	// #endregion be
	// #region tick
	private int timeSinceDropped = 0;

	// #endregion tick
	// #region be

	public DuplicatorBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.DUPLICATOR_BLOCK_ENTITY, pos, state);
	}

	@Override
	public NonNullList<ItemStack> getItems() {
		return items;
	}
	// #endregion be

	// #region save
	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		ContainerHelper.loadAllItems(input, items);
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		ContainerHelper.saveAllItems(output, items);
		super.saveAdditional(output);
	}
	// #endregion save

	// #region tick
	public static void tick(Level world, BlockPos blockPos, BlockState blockState, DuplicatorBlockEntity duplicatorBlockEntity) {
		if (duplicatorBlockEntity.isEmpty()) return;
		duplicatorBlockEntity.timeSinceDropped++;

		if (duplicatorBlockEntity.timeSinceDropped < 10) return;
		duplicatorBlockEntity.timeSinceDropped = 0;

		ItemStack duplicate = duplicatorBlockEntity.getItem(0).split(1);

		Block.popResourceFromFace(world, blockPos, Direction.UP, duplicate);
		Block.popResourceFromFace(world, blockPos, Direction.UP, duplicate);
	}
	// #endregion tick

	// #region accept
	@Override
	public int[] getSlotsForFace(Direction side) {
		return new int[]{ 0 };
	}

	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
		return dir == Direction.UP;
	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
		return true;
	}
	// #endregion accept
	// #region be
}
// #endregion be
