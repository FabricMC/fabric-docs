package com.example.docs.block.custom;

import com.mojang.serialization.MapCodec;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import com.example.docs.block.entity.ModBlockEntities;
import com.example.docs.block.entity.custom.CounterBlockEntity;

// #region block
public class CounterBlock extends BaseEntityBlock {
	public CounterBlock(Properties settings) {
		super(settings);
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return simpleCodec(CounterBlock::new);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new CounterBlockEntity(pos, state);
	}
	// #endregion block

	// #region use
	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
		if (!(level.getBlockEntity(pos) instanceof CounterBlockEntity counterBlockEntity)) {
			return super.useWithoutItem(state, level, pos, player, hit);
		}

		counterBlockEntity.incrementClicks();
		player.sendOverlayMessage(Component.literal("You've clicked the block for the " + counterBlockEntity.getClicks() + "th time."));

		return InteractionResult.SUCCESS;
	}
	// #endregion use

	// #region tickers
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, ModBlockEntities.COUNTER_BLOCK_ENTITY, CounterBlockEntity::tick);
	}
	// #endregion tickers

	// #region block
}
// #endregion block
