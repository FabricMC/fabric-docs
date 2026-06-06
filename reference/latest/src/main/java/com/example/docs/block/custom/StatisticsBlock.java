package com.example.docs.block.custom;

import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import com.example.docs.stats.ModStats;

// #region statistics_block
public class StatisticsBlock extends Block {
	// #endregion statistics_block
	public StatisticsBlock(Properties properties) {
		super(properties);
	}

	// #region statistics_block
	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity by, ItemStack itemStack) {
		super.setPlacedBy(level, pos, state, by, itemStack);

		if (by instanceof Player player) {
			int neighborCount = 0;

			for (Direction dir : Direction.values()) {
				if (!level.isEmptyBlock(pos.relative(dir))) {
					neighborCount++;
				}
			}

			player.awardStat(ModStats.FRIENDSHIPS_MADE, neighborCount);
		}
	}
}
// #endregion statistics_block
