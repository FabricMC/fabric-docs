package com.example.docs.block.custom;

import com.mojang.serialization.MapCodec;
import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import com.example.docs.block.entity.custom.DirtChestBlockEntity;

// #region block
public class DirtChestBlock extends BaseEntityBlock {
	// #endregion block
	public DirtChestBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return simpleCodec(DirtChestBlock::new);
	}

	// #region use
	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		if (!level.isClientSide() && level.getBlockEntity(pos) instanceof DirtChestBlockEntity dirtChest) {
			player.openMenu(dirtChest);
		}

		return InteractionResult.SUCCESS;
	}
	// #endregion use

	// #region block
	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new DirtChestBlockEntity(pos, state);
	}
	// #endregion block

	// #region block
	// ...
}
// #endregion block
