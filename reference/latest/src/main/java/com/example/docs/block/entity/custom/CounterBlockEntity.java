package com.example.docs.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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
		return this.clicks;
	}

	public void incrementClicks() {
		// #endregion clicks

		// #region ticks-since-last
		if (this.ticksSinceLast < 10) return;
		this.ticksSinceLast = 0;
		// #endregion ticks-since-last

		// #region clicks
		this.clicks++;
		this.setChanged();
	}
	// #endregion clicks

	// #region broadcast-update
	@Override
	public void setChanged() {
		super.setChanged();

		if (level == null) return;

		BlockState state = getBlockState();
		level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_ALL);
	}
	// #endregion broadcast-update

	// #region saving
	@Override
	protected void saveAdditional(ValueOutput output) {
		output.putInt("clicks", this.clicks);

		super.saveAdditional(output);
	}
	// #endregion saving

	// #region loading
	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);

		this.clicks = input.getIntOr("clicks", 0);
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

	// #region update-packet
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
	// #endregion update-packet

	// #region block-entity
}
// #endregion block-entity
