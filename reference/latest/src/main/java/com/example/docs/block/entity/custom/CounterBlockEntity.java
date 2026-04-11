package com.example.docs.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import com.example.docs.block.entity.ModBlockEntities;

// #region block-entity
public class CounterBlockEntity extends BlockEntity {
	// #endregion block-entity

	// #region clicks
	private int clicks = 0;
	// #endregion clicks

	private int ticksSinceLast = 0;

	// #region block-entity
	public CounterBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.COUNTER_BLOCK_ENTITY, pos, state);
	}
	// #endregion block-entity

	// #region clicks
	public int getClicks() {
		return clicks;
	}

	public void incrementClicks() {
		// #endregion clicks

		// #region ticks-since-last
		if (ticksSinceLast < 10) return;
		ticksSinceLast = 0;
		// #endregion ticks-since-last

		// #region clicks
		clicks++;
		setChanged();
	}
	// #endregion clicks

	// #region saving
	@Override
	protected void saveAdditional(ValueOutput output) {
		output.putInt("clicks", clicks);

		super.saveAdditional(output);
	}
	// #endregion saving

	// #region loading
	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);

		clicks = input.getIntOr("clicks", 0);
	}
	// #endregion loading

	// #region tickers
	public static void tick(Level level, BlockPos blockPos, BlockState blockState, CounterBlockEntity entity) {
		entity.ticksSinceLast++;
	}
	// #endregion tickers

	// #region get-update-tag
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
		return saveWithoutMetadata(registryLookup);
	}
	// #endregion get-update-tag

	// #region block-entity
}
// #endregion block-entity
