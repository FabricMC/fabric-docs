package com.example.docs.block.custom;

import com.example.docs.block.entity.custom.DirtChestBlockEntity;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.world.phys.BlockHitResult;

import org.jspecify.annotations.Nullable;
// :::block
public class DirtChestBlock extends BaseEntityBlock {
	// :::block
	public DirtChestBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return simpleCodec(DirtChestBlock::new);
	}

	// :::use
	@Override
	protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
		if (!level.isClientSide() && level.getBlockEntity(blockPos) instanceof DirtChestBlockEntity dirtChest) {
			player.openMenu(dirtChest);
		}
		return InteractionResult.SUCCESS;
	}
	// :::use

	// :::block
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
		return new DirtChestBlockEntity(blockPos, blockState);
	}
	// :::block


	// :::block
	// ...
}
// :::block
