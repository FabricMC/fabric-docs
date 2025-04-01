package com.example.docs.block.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.example.docs.block.entity.ModBlockEntities;

// :::1
public class CounterBlockEntity extends BlockEntity {
	// :::1

	// :::2
	private int clicks = 0;
	// :::2

	private int ticksSinceLast = 0;

	// :::1
	public CounterBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.COUNTER_BLOCK_ENTITY, pos, state);
	}

	// :::1

	// :::2
	public int getClicks() {
		return clicks;
	}

	public void incrementClicks() {
		// :::2

		// :::6
		if (ticksSinceLast < 10) return;
		ticksSinceLast = 0;
		// :::6

		// :::2
		clicks++;
		markDirty();
	}

	// :::2

	// :::3
	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		nbt.putInt("clicks", clicks);

		super.writeNbt(nbt, registryLookup);
	}

	// :::3

	// :::4
	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);

		clicks = nbt.getInt("clicks");
	}

	// :::4

	// :::5
	public static void tick(World world, BlockPos blockPos, BlockState blockState, CounterBlockEntity entity) {
		entity.ticksSinceLast++;
	}

	// :::5

	// :::7
	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		return createNbt(registryLookup);
	}

	// :::7

	// :::1
}
// :::1
