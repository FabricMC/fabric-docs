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

	// #region broadcast-update
	@Override
	public void setChanged() {
		super.setChanged();

		if (level == null) return;

		BlockState state = getBlockState();
		level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_ALL);
	}
	// #endregion broadcast-update

	// :::3
	@Override
	protected void saveAdditional(ValueOutput output) {
		output.putInt("clicks", clicks);

		super.saveAdditional(output);
	}
	// :::3

	// :::4
	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);

		clicks = input.getIntOr("clicks", 0);
	}
	// :::4

	// :::5
	public static void tick(Level level, BlockPos blockPos, BlockState blockState, CounterBlockEntity entity) {
		entity.ticksSinceLast++;
	}
	// :::5

	// :::7
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
		return saveWithoutMetadata(registryLookup);
	}
	// :::7

	// #region update-packet
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
	// #endregion update-packet

	// :::1
}
// :::1
