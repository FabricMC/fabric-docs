package com.example.docs.block.entity.custom;

import com.example.docs.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

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
		setChanged();
	}

	// :::2

	// :::3
	@Override
	protected void saveAdditional(ValueOutput writeView) {
		writeView.putInt("clicks", clicks);

		super.saveAdditional(writeView);
	}

	// :::3

	// :::4
	@Override
	protected void loadAdditional(ValueInput readView) {
		super.loadAdditional(readView);

		clicks = readView.getIntOr("clicks", 0);
	}

	// :::4

	// :::5
	public static void tick(Level world, BlockPos blockPos, BlockState blockState, CounterBlockEntity entity) {
		entity.ticksSinceLast++;
	}

	// :::5

	// :::7
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
		return saveWithoutMetadata(registryLookup);
	}

	// :::7

	// :::1
}
// :::1
