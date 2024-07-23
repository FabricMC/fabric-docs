package com.example.docs.block.entity.custom;

import com.example.docs.sound.DynamicSoundSource;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.example.docs.block.entity.ModBlockEntities;


public class EngineBlockEntity extends BlockEntity implements DynamicSoundSource {
	public static final int MAX_TICK_AMOUNT = 120;	// can represent something like fuel capacity

	private int tick = -1;    // -1 is turned off, it will only tick if its 0 or bigger
	private float normalizedStress = 0;

	public EngineBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.ENGINE_BLOCK_ENTITY, pos, state);
	}

	public int getTick() {
		return tick;
	}

	public void setTick(int tick) {
		this.tick = tick;
	}

	public boolean isTicking() {
		return this.getTick() > -1;
	}

	public void setNormalizedStress(float normalizedStress) {
		this.normalizedStress = Math.clamp(normalizedStress, 0, 1);
	}

	public static void tick(World world, BlockPos pos, BlockState state, EngineBlockEntity blockEntity) {
		if (blockEntity.getTick() < 0) return;
		blockEntity.setTick(blockEntity.getTick() + 1);
		if (blockEntity.getTick() >= MAX_TICK_AMOUNT) blockEntity.setTick(-1);
	}

	@Override
	public int getTicks() {
		return getTick();
	}

	@Override
	public BlockPos getPosition() {
		return this.getPos();
	}

	@Override
	public float getNormalizedStress() {
		return normalizedStress;
	}
}
