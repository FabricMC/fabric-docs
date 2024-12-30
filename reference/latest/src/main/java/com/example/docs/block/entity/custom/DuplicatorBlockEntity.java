package com.example.docs.block.entity.custom;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import com.example.docs.block.entity.ModBlockEntities;
import com.example.docs.inventory.ImplementedInventory;

/*
The following is a dummy piece of code to not have `implements SidedInventory` in the first code block where we implement `ImplementedInventory`.
lmk if you have a better idea on how to handle this.
// :::1
public class DuplicatorBlockEntity extends BlockEntity implements ImplementedInventory {
// :::1
*/

public class DuplicatorBlockEntity extends BlockEntity implements ImplementedInventory, SidedInventory {
	// :::1

	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(1, ItemStack.EMPTY);

	public DuplicatorBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.DUPLICATOR_BLOCK_ENTITY, pos, state);
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return items;
	}

	// :::1

	// :::2
	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		Inventories.readNbt(nbt, items, registryLookup);
	}

	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		Inventories.writeNbt(nbt, items, registryLookup);
		super.writeNbt(nbt, registryLookup);
	}

	// :::2

	// :::3
	public static void tick(World world, BlockPos blockPos, BlockState blockState, DuplicatorBlockEntity duplicatorBlockEntity) {
		if (!duplicatorBlockEntity.isEmpty()) {
			ItemStack stack = duplicatorBlockEntity.getStack(0);
			duplicatorBlockEntity.clear();

			Block.dropStack(world, blockPos, stack);
			Block.dropStack(world, blockPos, stack);
		}
	}

	// :::3

	// :::4
	@Override
	public int[] getAvailableSlots(Direction side) {
		return new int[]{ 0 };
	}

	@Override
	public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
		return dir == Direction.UP;
	}

	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction dir) {
		return true;
	}

	// :::4

	// :::1
}
// :::1
