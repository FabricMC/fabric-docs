package com.example.docs.block.custom;

import com.example.docs.stats.ModStats;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class StatisticsBlock extends Block {
	public StatisticsBlock(Properties properties) {
		super(properties);
	}

	// #region interact
	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		player.awardStat(ModStats.INTERACT_WITH_STATISTICS_BLOCK);

		return InteractionResult.SUCCESS;
	}
	// #endregion interact
}
